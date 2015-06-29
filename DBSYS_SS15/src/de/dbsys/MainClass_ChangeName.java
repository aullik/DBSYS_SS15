package de.dbsys;

import de.dbsys.view.root.rootLayout.RootLayoutLoader;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainClass_ChangeName extends Application {

   public static void main(final String[] args) {
      launch(MainClass_ChangeName.class, args);
   }

   @Override
   public void start(final Stage primaryStage) throws Exception {
      primaryStage.setTitle("DBSYS Buchungssoftware");
      new RootLayoutLoader().showIn(primaryStage);
      primaryStage.show();

   }
}
