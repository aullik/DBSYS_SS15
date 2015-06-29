package de.dbsys.view.root.Aside.registerLayout;

import java.net.URL;
import java.util.ResourceBundle;

import de.dbsys.backend.Backend;
import de.dbsys.backend.Backend.LoginType;
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
import javafx.scene.control.TextField;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


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
   }

   @FXML
      void handleRegisterNewFinish(final ActionEvent event) {

      String vorname = nameTF.getText().trim();
      String nachname = nachnameTF.getText().trim();
      String email = emailTF.getText().trim();
      String pw = emailTF.getText().trim();
      String iban = ibanTF.getText().trim();
      String strasze = straszeTF.getText().trim();
      String plz = plzTF.getText().trim();
      Land land = landCB.getValue();

      if (vorname.isEmpty() || nachname.isEmpty() || email.isEmpty() || pw.isEmpty()
            || iban.isEmpty() || strasze.isEmpty() || plz.isEmpty() || land == null) {
         Alert warn = new Alert(AlertType.WARNING);
         warn.setContentText("Alle Felder m�ssen gef�llt sein");
         return;
      }

      LoginType login = Backend.get().login(email, pw);
      if (LoginType.User.equals(login))
         container.setSideALoader(new LoggedInLayoutLoader());
      else if (LoginType.Manager.equals(login))
         throw new NotImplementedException();
      // TODO
      else {
         Alert warn = new Alert(AlertType.WARNING);
         warn.setContentText("Login Fehlgeschlagen");
         warn.show();
      }

      container.setSideALoader(new LoggedInLayoutLoader());
   }

   @FXML
      void handleReturnLogin(final ActionEvent event) {
      container.setSideALoader(new LoginLayoutLoader());
   }
}
