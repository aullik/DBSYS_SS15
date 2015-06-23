package de.dbsys.view.root.searchLayout;

import de.dbsys.view.MVCLoader;


public class SearchLayoutLoader extends MVCLoader {

   public SearchLayoutLoader() {
      super(() -> new SearchLayoutController());
   }
}
