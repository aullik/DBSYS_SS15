package de.dbsys.view.root.Bside.resultLayout;

import java.net.URL;
import java.util.ResourceBundle;

import de.dbsys.model.SideContainer;
import de.dbsys.view.root.Bside.searchLayout.SearchLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


public class ResultLayoutController implements Initializable {

   @FXML
   private ListView<?> gefundeneWohnungLV;

   @FXML
   private TextArea wohnungDetailTA;

   private SideContainer container;

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {
      container = SideContainer.get();

   }

   @FXML
      void handleBackToSearch(final ActionEvent event) {
      container.setSideBLoader(new SearchLayoutLoader());
   }

}
