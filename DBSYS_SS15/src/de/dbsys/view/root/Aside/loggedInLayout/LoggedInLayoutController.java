package de.dbsys.view.root.Aside.loggedInLayout;

import java.net.URL;
import java.util.ResourceBundle;

import de.dbsys.model.Kunde;
import de.dbsys.model.SideContainer;
import de.dbsys.view.MVCLoader;
import de.dbsys.view.root.Aside.loginLayout.LoginLayoutLoader;
import de.dbsys.view.root.Bside.bookingConfirmation.BookingConfirmationLoader;
import de.dbsys.view.root.Bside.dataAdministration.DataAdministrationLoader;
import de.dbsys.view.root.Bside.resultLayout.ResultLayoutLoader;
import de.dbsys.view.root.Bside.searchLayout.SearchLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


public class LoggedInLayoutController implements Initializable {

   @FXML
   private TextField bewertungTF;

   private SideContainer container;

   private final Kunde kunde;

   public LoggedInLayoutController(final Kunde kunde) {
      this.kunde = kunde;
   }

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {
      container = SideContainer.get();

   }

   @FXML
      void handleBooking(final ActionEvent event) {
      container.setSideBLoader(
            new BookingConfirmationLoader((ResultLayoutLoader) container.getSideBLoader()));
   }

   @FXML
      void handleDataAdmin(final ActionEvent event) {
      container.setSideBLoader(new DataAdministrationLoader());
   }

   @FXML
      void handleEvaluation(final ActionEvent event) {

   }

   @FXML
      void handleLogout(final ActionEvent event) {
      container.setSideALoader(new LoginLayoutLoader());
      // FIXME: only when on certain page
      MVCLoader sideB = container.getSideBLoader();
      if (sideB instanceof DataAdministrationLoader || sideB instanceof BookingConfirmationLoader)
         container.setSideBLoader(new SearchLayoutLoader());
   }

}
