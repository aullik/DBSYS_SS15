package de.dbsys.view.root.Aside.standardLayout;

import de.dbsys.view.MVCLoader;

public class StandardLayoutLoader extends MVCLoader {
	public StandardLayoutLoader() {
		super(() -> new StandardLayoutController());
	}
}
