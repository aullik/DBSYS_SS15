package de.dbsys.view.root.Aside.registerLayout;

import java.net.URL;
import java.util.ResourceBundle;

import de.dbsys.model.SideContainer;
import de.dbsys.view.root.Aside.loggedInLayout.LoggedInLayoutLoader;
import de.dbsys.view.root.Aside.loginLayout.LoginLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class RegisterLayoutController implements Initializable {

   @FXML
   private TextField nameTF;

   @FXML
   private TextField emailRegisterTF;

   @FXML
   private TextField passwortRegisterTF;

   @FXML
   private TextField ibanTF;

   @FXML
   private TextField straszeTF;

   @FXML
   private TextField stadtTF;

   @FXML
   private TextField hausNrTF;

   @FXML
   private ComboBox<?> landTF;

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
      container.setSideALoader(new LoggedInLayoutLoader());
   }

   @FXML
      void handleReturnLogin(final ActionEvent event) {
      container.setSideALoader(new LoginLayoutLoader());
   }
}
