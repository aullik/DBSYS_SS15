package de.dbsys.view.root.Aside.loginLayout;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import de.dbsys.backend.Backend;
import de.dbsys.model.Kunde;
import de.dbsys.model.SideContainer;
import de.dbsys.view.root.Aside.loggedInLayout.LoggedInLayoutLoader;
import de.dbsys.view.root.Aside.registerLayout.RegisterLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginLayoutController implements Initializable {

   @FXML
   private TextField emailTF;

   @FXML
   private PasswordField passwortTF;

   private SideContainer container;

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {
      container = SideContainer.get();
   }

   @FXML
      void handleLogin(final ActionEvent event) {

      String email = emailTF.getText().trim();
      String pw = passwortTF.getText().trim();
      if (email.isEmpty() || pw.isEmpty()) {
         Alert warn = new Alert(AlertType.WARNING);
         warn.setContentText("Weder Email noch Password dürfen leer sein!");
         warn.show();
         return;
      }

      Optional<Kunde> kunde = Backend.get().login(email, pw);
      if (kunde.isPresent())
         container.setSideALoader(new LoggedInLayoutLoader(kunde.get()));
      else {
         Alert warn = new Alert(AlertType.WARNING);
         warn.setContentText("Login Fehlgeschlagen");
         warn.show();
      }

   }

   @FXML
      void handleRegisterNew(final ActionEvent event) {
      container.setSideALoader(new RegisterLayoutLoader());

   }

}
