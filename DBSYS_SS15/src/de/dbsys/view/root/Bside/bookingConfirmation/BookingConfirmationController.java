package de.dbsys.view.root.Bside.bookingConfirmation;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;

import de.dbsys.backend.Backend;
import de.dbsys.model.Buchung;
import de.dbsys.model.SideContainer;
import de.dbsys.view.root.Bside.resultLayout.ResultLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


public class BookingConfirmationController implements Initializable {

   private final ResultLayoutLoader rLL;

   private final Buchung buchung;

   public BookingConfirmationController(final ResultLayoutLoader rLL, final Buchung buchung) {
      this.rLL = rLL;
      this.buchung = buchung;

   }

   @FXML
   private Button confirmBookingBTN;

   @FXML
   private TextArea wohnungDetailTA;

   @FXML
   private TextArea buchungDetailTA;

   private SideContainer container;

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {
      container = SideContainer.get();
      wohnungDetailTA.setText(buchung.getWohnung().getWohnungsdetails());
      handleShowBookingdetails(buchung);

   }

   private void handleShowBookingdetails(final Buchung buchung) {
      char newLine = '\n';
      StringBuilder sb = new StringBuilder();
      sb.append("Kontoinhaber: ").append(buchung.getKunde().getVorname()).append(" ")
            .append(buchung.getKunde().getNachname()).append(newLine);
      sb.append("BIC: ").append(buchung.getKunde().getBIC()).append(newLine);
      sb.append("IBAN: ").append(buchung.getKunde().getIBAN()).append(newLine);
      DateTimeFormatter DTF = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
      sb.append("Reisezeitraum:").append(newLine).append(buchung.getAnreiseDatum().format(DTF))
            .append(" bis ").append(buchung.getAbreiseDatum().format(DTF)).append(newLine);
      sb.append(newLine);
      int buchungsnummer = buchung.getBuchungsnummer();
      if (buchungsnummer == 0)
         sb.append("Bitte best�tigen Sie Ihre Buchung");
      else
         sb.append("Buchungsnummer: ").append(buchungsnummer);

      buchungDetailTA.setText(sb.toString());
   }

   @FXML
      void handleConfirmBooking(final ActionEvent event) {

      Optional<Buchung> buchung = Backend.get().bookWohnung(this.buchung);
      if (!buchung.isPresent()) {
         Alert warn = new Alert(AlertType.WARNING);
         warn.setContentText("Buchung nicht erfolgreich");
         warn.show();
         return;
      }

      confirmBookingBTN.setDisable(true);
      handleShowBookingdetails(buchung.get());
   }

   @FXML
      void handleReturnSelection(final ActionEvent event) {
      container.setSideBLoader(rLL);
   }

}
