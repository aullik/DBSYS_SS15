package de.dbsys.view.root.Bside.searchLayout;

import java.net.URL;
import java.util.ResourceBundle;

import de.dbsys.model.SideContainer;
import de.dbsys.view.root.Bside.resultLayout.ResultLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


public class SearchLayoutController implements Initializable {

   @FXML
   private DatePicker anreiseDP;

   @FXML
   private DatePicker abreiseDP;

   @FXML
   private ComboBox<?> landCB;

   @FXML
   private TextField preisMinTF;

   @FXML
   private TextField preisMaxTF;

   @FXML
   private ComboBox<?> ausstattungCB;

   private SideContainer container;

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {
      container = SideContainer.get();

   }

   @FXML
      void handleApartementSearch(final ActionEvent event) {
      container.setSideBLoader(new ResultLayoutLoader());
   }

}
