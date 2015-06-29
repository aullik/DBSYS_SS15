package de.dbsys.view.root.Bside.registerLayoutB;

import de.dbsys.view.MVCLoader;

public class RegisterLayoutBLoader extends MVCLoader{
	public RegisterLayoutBLoader () {
		super(() -> new RegisterLayoutBController());
	}
}
