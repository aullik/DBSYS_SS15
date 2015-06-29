package de.dbsys.view.root.rootLayout;

import java.net.URL;
import java.util.ResourceBundle;

import de.dbsys.model.SideContainer;
import de.dbsys.view.root.Aside.loginLayout.LoginLayoutLoader;
import de.dbsys.view.root.Bside.searchLayout.SearchLayoutLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.layout.BorderPane;


public class RootLayoutController implements Initializable {

   public RootLayoutController() {}

   @FXML
   private SplitPane root;

   @FXML
   private BorderPane parentA;

   @FXML
   private BorderPane parentB;

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {
      for (Divider div : root.getDividers()) {
         double pos = div.getPosition();
         div.positionProperty().addListener(obs -> div.positionProperty().set(pos));
      }
      SideContainer container = SideContainer.get();
      container.SideALoaderProperty()
            .addListener(ign -> parentA.setCenter(container.getSideALoader().getView()));
      container.SideBLoaderProperty()
            .addListener(ign -> parentB.setCenter(container.getSideBLoader().getView()));

      container.setSideALoader(new LoginLayoutLoader());
      container.setSideBLoader(new SearchLayoutLoader());

      // Backend.get(); // FIXME REMOVE

   }
}
