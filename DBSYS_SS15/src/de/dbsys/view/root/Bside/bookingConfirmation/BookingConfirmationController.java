package de.dbsys.view.root.Bside.bookingConfirmation;

import java.net.URL;
import java.util.ResourceBundle;

import de.dbsys.model.Buchung;
import de.dbsys.model.SideContainer;
import de.dbsys.view.root.Bside.resultLayout.ResultLayoutLoader;
import de.dbsys.view.root.Bside.searchLayout.SearchLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;


public class BookingConfirmationController implements Initializable {

   private final ResultLayoutLoader rLL;

   private final Buchung buchung;

   public BookingConfirmationController(final ResultLayoutLoader rLL, final Buchung buchung) {
      this.rLL = rLL;
      this.buchung = buchung;

   }

   @FXML
   private TextArea wohnungDetailTA;

   @FXML
   private TextArea buchungDetailTA;

   private SideContainer container;

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {
      container = SideContainer.get();

   }

   @FXML
      void handleConfirmBooking(final ActionEvent event) {
      container.setSideBLoader(new SearchLayoutLoader());
   }

   @FXML
      void handleReturnSelection(final ActionEvent event) {
      container.setSideBLoader(rLL);
   }

}
