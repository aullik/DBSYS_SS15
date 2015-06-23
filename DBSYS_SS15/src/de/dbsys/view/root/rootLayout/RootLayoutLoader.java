package de.dbsys.view.root.rootLayout;

import de.dbsys.view.MVCLoader;


public class RootLayoutLoader extends MVCLoader {

   public RootLayoutLoader() {
      super(() -> new RootLayoutController());
   }
}
