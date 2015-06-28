package de.dbsys.view.root.Bside.rootLayout;

import de.dbsys.view.MVCLoader;


public class RootLayoutLoader extends MVCLoader {

   public RootLayoutLoader() {
      super(() -> new RootLayoutController());
   }
}
