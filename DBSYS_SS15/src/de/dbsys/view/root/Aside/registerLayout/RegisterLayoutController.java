package de.dbsys.view.root.Aside.registerLayout;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import de.dbsys.backend.Backend;
import de.dbsys.model.Adresse;
import de.dbsys.model.Kunde;
import de.dbsys.model.Land;
import de.dbsys.model.SideContainer;
import de.dbsys.view.root.Aside.loggedInLayout.LoggedInLayoutLoader;
import de.dbsys.view.root.Aside.loginLayout.LoginLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;


public class RegisterLayoutController implements Initializable {

   @FXML
   private TextField nameTF;

   @FXML
   private TextField emailTF;

   @FXML
   private TextField passwortTF;

   @FXML
   private TextField ibanTF;

   @FXML
   private TextField bicTF;

   @FXML
   private TextField straszeTF;

   @FXML
   private TextField stadtTF;

   @FXML
   private TextField hausNrTF;

   @FXML
   private ComboBox<Land> landCB;

   @FXML
   private TextField plzTF;

   @FXML
   private TextField nachnameTF;

   private SideContainer container;

   @Override
   public void initialize(final URL arg0, final ResourceBundle arg1) {
      container = SideContainer.get();

      List<Land> lands = Backend.get().getAllLands();
      landCB.setCellFactory(ign -> new LandesCell());
      landCB.setButtonCell(new LandesCell());
      landCB.getItems().addAll(lands);
   }

   private String trim(final String s) {
      if (s == null)
         return "";
      else
         return s.trim();
   }

   @FXML
      void handleRegisterNewFinish(final ActionEvent event) {

      String vorname = trim(nameTF.getText());
      String nachname = trim(nachnameTF.getText());
      String email = trim(emailTF.getText());
      String pw = trim(passwortTF.getText());
      String iban = trim(ibanTF.getText());
      String bic = trim(bicTF.getText());
      String strasze = trim(straszeTF.getText());
      String ort = trim(stadtTF.getText());
      String hausnr = trim(hausNrTF.getText());
      String plz = trim(plzTF.getText());
      Land land = landCB.getValue();

      if (vorname.isEmpty() || nachname.isEmpty() || email.isEmpty() || pw.isEmpty()
            || iban.isEmpty() || bic.isEmpty() || strasze.isEmpty() || hausnr.isEmpty()
            || plz.isEmpty() || ort.isEmpty() || land == null) {
         Alert warn = new Alert(AlertType.WARNING);
         warn.setContentText("Alle Felder müssen gefüllt sein");
         warn.show();
         return;
      }

      Adresse kundenAdresse = new Adresse(strasze, hausnr, ort, plz, land);
      Kunde newKunde = new Kunde(vorname, nachname, email, pw, iban, bic, kundenAdresse);

      Optional<Kunde> kunde = Backend.get().createNewUser(newKunde);
      if (kunde.isPresent())
         container.setSideALoader(new LoggedInLayoutLoader(kunde.get()));
      else {
         Alert warn = new Alert(AlertType.WARNING);
         warn.setContentText("Regestrierung Fehlgeschlagen");
         warn.show();
      }
   }

   @FXML
      void handleReturnLogin(final ActionEvent event) {
      container.setSideALoader(new LoginLayoutLoader());
   }

   private static class LandesCell extends ListCell<Land> {

      @Override
      protected void updateItem(final Land item, final boolean empty) {
         super.updateItem(item, empty);
         if (empty)
            setText("");
         else
            setText(item.getLandesname());
      }
   }
}
