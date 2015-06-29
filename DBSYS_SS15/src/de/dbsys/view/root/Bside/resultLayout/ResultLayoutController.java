package de.dbsys.view.root.Bside.resultLayout;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.dbsys.model.SideContainer;
import de.dbsys.model.Wohnung;
import de.dbsys.view.root.Bside.searchLayout.SearchLayoutLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


public class ResultLayoutController implements Initializable {

   @FXML
   private ListView<Wohnung> gefundeneWohnungLV;

   @FXML
   private TextArea wohnungDetailTA;

   private final SideContainer container;

   private final SearchLayoutLoader searchLoader;

   private final List<Wohnung> list;

   public ResultLayoutController(final SearchLayoutLoader searchLoader, final List<Wohnung> list) {
      container = SideContainer.get();
      this.searchLoader = searchLoader;
      this.list = list;
   }

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {
      gefundeneWohnungLV.getItems().addAll(list);
   }

   @FXML
      void handleBackToSearch(final ActionEvent event) {
      container.setSideBLoader(searchLoader);
   }

}
