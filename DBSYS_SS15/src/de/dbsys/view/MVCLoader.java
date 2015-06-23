package de.dbsys.view;

import java.util.function.Supplier;

import javafx.fxml.Initializable;


public abstract class MVCLoader extends AbstractMVCLoader {

   private final Supplier<Initializable> controllerSupplier;

   public MVCLoader(final Supplier<Initializable> controllerSupplier) {
      this.controllerSupplier = controllerSupplier;
   }

   @Override
   protected final Initializable controllerInstance() {
      return controllerSupplier.get();
   }
}
