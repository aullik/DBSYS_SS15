package de.dbsys.view.root.Aside.loginLayout;

import java.net.URL;
import java.util.ResourceBundle;

import de.dbsys.model.SideContainer;
import de.dbsys.view.root.Aside.loggedInLayout.LoggedInLayoutLoader;
import de.dbsys.view.root.Aside.registerLayout.RegisterLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
      container.setSideALoader(new LoggedInLayoutLoader());
   }

   @FXML
      void handleRegisterNew(final ActionEvent event) {
      container.setSideALoader(new RegisterLayoutLoader());

   }

}
