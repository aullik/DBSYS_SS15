package de.dbsys.view.root.Bside.resultLayout;

import java.util.List;

import de.dbsys.model.Wohnung;
import de.dbsys.view.MVCLoader;
import de.dbsys.view.root.Bside.searchLayout.SearchLayoutLoader;


public class ResultLayoutLoader extends MVCLoader {

   public ResultLayoutLoader(final SearchLayoutLoader searchLoader, final List<Wohnung> list) {
      super(() -> new ResultLayoutController(searchLoader, list));
   }
}
