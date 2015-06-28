package de.dbsys.view.root.Aside.loginLayout;

import de.dbsys.view.MVCLoader;


public class LoginLayoutLoader extends MVCLoader {

   public LoginLayoutLoader() {
      super(() -> new LoginLayoutController());
   }

}
