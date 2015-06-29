package de.dbsys.view.root.Aside.loggedInLayout;

import de.dbsys.model.Kunde;
import de.dbsys.view.MVCLoader;


public class LoggedInLayoutLoader extends MVCLoader {

   public LoggedInLayoutLoader(final Kunde kunde) {
      super(() -> new LoggedInLayoutController(kunde));
   }
}
