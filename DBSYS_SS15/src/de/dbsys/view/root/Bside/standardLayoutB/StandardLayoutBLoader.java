package de.dbsys.view.root.Bside.standardLayoutB;

import de.dbsys.view.MVCLoader;

public class StandardLayoutBLoader extends MVCLoader{
	public StandardLayoutBLoader () {
		super(() -> new StandardLayoutBController());
	}
}
