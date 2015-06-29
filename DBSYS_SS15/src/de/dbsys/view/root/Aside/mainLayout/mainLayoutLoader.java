package de.dbsys.view.root.Aside.mainLayout;

import de.dbsys.view.MVCLoader;

public class mainLayoutLoader extends MVCLoader{
	public mainLayoutLoader() {
		super(() -> new mainLayoutController());
	}
}
