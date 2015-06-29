package de.dbsys.view.root.Aside.loggedInLayout;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import de.dbsys.backend.Backend;
import de.dbsys.model.Buchung;
import de.dbsys.model.Kunde;
import de.dbsys.model.SideContainer;
import de.dbsys.model.Wohnung;
import de.dbsys.view.MVCLoader;
import de.dbsys.view.root.Aside.loginLayout.LoginLayoutLoader;
import de.dbsys.view.root.Bside.bookingConfirmation.BookingConfirmationLoader;
import de.dbsys.view.root.Bside.dataAdministration.DataAdministrationLoader;
import de.dbsys.view.root.Bside.resultLayout.ResultLayoutController;
import de.dbsys.view.root.Bside.resultLayout.ResultLayoutLoader;
import de.dbsys.view.root.Bside.searchLayout.SearchLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class LoggedInLayoutController implements Initializable {

   @FXML
   private TextField bewertungTF;

   @FXML
   private Button bewertungBTN;

   @FXML
   private Label welcomeMessage;

   private SideContainer container;

   private final Kunde kunde;
   Optional<Buchung> booking;

   public LoggedInLayoutController(final Kunde kunde) {
      this.kunde = kunde;
   }

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {
      welcomeMessage.setText(
            String.format(welcomeMessage.getText(), kunde.getVorname(), kunde.getNachname()));
      container = SideContainer.get();

      setToNumbersOnly(bewertungTF);

      this.booking = Backend.get().getLastCompletedBooking(kunde);
      if (!booking.isPresent()) {
         bewertungBTN.setDisable(true);
         bewertungTF.setDisable(true);
      }

   }

   private void setToNumbersOnly(final TextField tf) {
      tf.addEventFilter(KeyEvent.KEY_TYPED, event -> {
         if (!event.getCharacter().matches("\\d") || tf.getLength() >= 9)
            event.consume();
      });
   }

   @FXML
      void handleBooking(final ActionEvent event) {

      Alert info = new Alert(AlertType.INFORMATION);
      info.setContentText("keine Wohnung ausgewählt!");

      if (!(container.getSideBLoader() instanceof ResultLayoutLoader)) {
         info.show();
         return;
      }

      ResultLayoutLoader loader = (ResultLayoutLoader) container.getSideBLoader();
      ResultLayoutController controller = loader.getController();
      Optional<Wohnung> wohnung = controller.getSelectedWohnung();
      if (!wohnung.isPresent()) {
         info.show();
         return;
      }

      Optional<Buchung> buchung = Backend.get().bookWohnung(wohnung.get());
      if (!buchung.isPresent()) {
         Alert warn = new Alert(AlertType.WARNING);
         warn.setContentText("Buchung nicht erfolgreich");
         warn.show();
         return;
      }

      container.setSideBLoader(new BookingConfirmationLoader(loader, buchung.get()));
   }

   @FXML
      void handleDataAdmin(final ActionEvent event) {
      container.setSideBLoader(new DataAdministrationLoader());
   }

   @FXML
      void handleEvaluation(final ActionEvent event) {

      Alert warn = new Alert(AlertType.WARNING);
      warn.setContentText("Bewertung muss zwischen 10 und 60 sein");

      if (!booking.isPresent())
         return;
      if (bewertungTF.getText().isEmpty()) {
         warn.show();
         return;
      }
      int bewertung = Integer.parseInt(bewertungTF.getText());
      if (bewertung > 60 || bewertung < 10) {
         warn.show();
         return;
      }

      Backend.get().evaluateBooking(booking.get(), bewertung);

   }

   @FXML
      void handleLogout(final ActionEvent event) {
      container.setSideALoader(new LoginLayoutLoader());
      MVCLoader sideB = container.getSideBLoader();
      if (sideB instanceof DataAdministrationLoader || sideB instanceof BookingConfirmationLoader)
         container.setSideBLoader(new SearchLayoutLoader());
   }

}
