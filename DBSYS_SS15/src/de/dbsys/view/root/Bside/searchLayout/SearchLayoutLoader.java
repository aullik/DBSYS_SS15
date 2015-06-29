package de.dbsys.view.root.Bside.searchLayout;

import de.dbsys.view.MVCLoader;


public class SearchLayoutLoader extends MVCLoader {

   public SearchLayoutLoader() {
      super(() -> new SearchLayoutController());
   }
}
