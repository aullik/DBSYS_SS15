package de.dbsys.view.root.rootLayout;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.layout.BorderPane;
import de.dbsys.backend.Backend;
import de.dbsys.view.MVCLoader;
import de.dbsys.view.root.loginLayout.LoginLayoutLoader;
import de.dbsys.view.root.searchLayout.SearchLayoutLoader;


public class RootLayoutController implements Initializable {

   private final ObjectProperty<MVCLoader> childA;
   private final ObjectProperty<MVCLoader> childB;

   public RootLayoutController() {
      this.childA = new SimpleObjectProperty<>();
      this.childB = new SimpleObjectProperty<>();
   }

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

      childA.addListener(ign -> parentA.setCenter(childA.get().getView()));
      childB.addListener(ign -> parentB.setCenter(childB.get().getView()));

      childA.set(new LoginLayoutLoader());
      childB.set(new SearchLayoutLoader());

      Backend.get(); // FIXME REMOVE

   }
}
