package de.dbsys.view.root.Bside.dataAdministration;

import de.dbsys.view.MVCLoader;


public class DataAdministrationBLoader extends MVCLoader{
	public DataAdministrationBLoader () {
		super(() -> new DataAdministrationBController());
	}
}
