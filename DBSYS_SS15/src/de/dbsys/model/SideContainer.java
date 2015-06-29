package de.dbsys.model;

import de.dbsys.view.MVCLoader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


public class SideContainer {

   private final static SideContainer INSTANCE = new SideContainer();

   private final ObjectProperty<MVCLoader> sideA;
   private final ObjectProperty<MVCLoader> sideB;

   private SideContainer() {
      this.sideA = new SimpleObjectProperty<>();
      this.sideB = new SimpleObjectProperty<>();
   }

   public static SideContainer get() {
      return INSTANCE;
   }

   public MVCLoader getSideALoader() {
      return sideA.get();
   }

   public void setSideALoader(final MVCLoader sideALoader) {
      this.sideA.set(sideALoader);
   }

   public ObjectProperty<MVCLoader> SideALoaderProperty() {
      return sideA;
   }

   public ObjectProperty<MVCLoader> SideBLoaderProperty() {
      return sideB;
   }

   public MVCLoader getSideBLoader() {
      return sideB.get();
   }

   public void setSideBLoader(final MVCLoader sideBLoader) {
      this.sideB.set(sideBLoader);
   }

}
