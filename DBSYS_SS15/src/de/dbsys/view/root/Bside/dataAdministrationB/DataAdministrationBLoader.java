package de.dbsys.view.root.Bside.dataAdministrationB;

import de.dbsys.view.MVCLoader;


public class DataAdministrationBLoader extends MVCLoader{
	public DataAdministrationBLoader () {
		super(() -> new DataAdministrationBController());
	}
}
