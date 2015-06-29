package de.dbsys.view.root.Bside.dataAdministration;

import java.net.URL;
import java.util.ResourceBundle;

import de.dbsys.model.SideContainer;
import de.dbsys.view.root.Bside.searchLayout.SearchLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class DataAdministrationController implements Initializable {

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
   private ComboBox<?> landCB;

   @FXML
   private TextField plzTF;

   @FXML
   private ListView<?> ausstattungLV;

   @FXML
   private TextField ausstattungTF;

   @FXML
   private ListView<?> attraktionLV;

   @FXML
   private TextField attraktionTF;

   @FXML
   private TextField ferienwohnungTF;

   @FXML
   private ListView<?> ferienwohnungLV;

   @FXML
   private TextArea ferienwohnungDetailTA;

   private SideContainer container;

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {
      container = SideContainer.get();

   }

   @FXML
      void handleAddApartment(final ActionEvent event) {

   }

   @FXML
      void handleAddAttraction(final ActionEvent event) {

   }

   @FXML
      void handleAddEquipment(final ActionEvent event) {

   }

   @FXML
      void handleDeleteApartement(final ActionEvent event) {

   }

   @FXML
      void handleDeleteAttraction(final ActionEvent event) {

   }

   @FXML
      void handleDeleteEquipment(final ActionEvent event) {

   }

   @FXML
      void handleDeleteUser(final ActionEvent event) {

   }

   @FXML
      void handleSaveUserData(final ActionEvent event) {
      container.setSideBLoader(new SearchLayoutLoader());
   }

}
