package de.dbsys.view.root.Bside.dataAdministration;

import de.dbsys.view.MVCLoader;


public class DataAdministrationLoader extends MVCLoader{
	public DataAdministrationLoader () {
		super(() -> new DataAdministrationController());
	}
}
