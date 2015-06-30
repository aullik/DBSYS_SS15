package de.dbsys;

import de.dbsys.view.root.rootLayout.RootLayoutLoader;
import javafx.application.Application;
import javafx.stage.Stage;


public class BookingApplication extends Application {

   public static void main(final String[] args) {
      launch(BookingApplication.class, args);
   }

   @Override
   public void start(final Stage primaryStage) throws Exception {
      primaryStage.setTitle("DBSYS Buchungssoftware");
      new RootLayoutLoader().showIn(primaryStage);

   }
}
