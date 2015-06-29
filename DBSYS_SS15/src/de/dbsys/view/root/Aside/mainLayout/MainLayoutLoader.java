package de.dbsys.view.root.Aside.mainLayout;

import de.dbsys.view.MVCLoader;

public class MainLayoutLoader extends MVCLoader{
	public MainLayoutLoader() {
		super(() -> new MainLayoutController());
	}
}
