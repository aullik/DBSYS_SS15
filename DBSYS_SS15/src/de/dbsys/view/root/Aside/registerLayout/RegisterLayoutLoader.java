package de.dbsys.view.root.Aside.registerLayout;

import de.dbsys.view.MVCLoader;

public class RegisterLayoutLoader extends MVCLoader {
	public RegisterLayoutLoader () {
		super(() -> new RegisterLayoutController());
	}
	 
}
