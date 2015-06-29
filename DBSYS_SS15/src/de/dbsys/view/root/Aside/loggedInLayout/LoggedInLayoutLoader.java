package de.dbsys.view.root.Aside.loggedInLayout;

import de.dbsys.view.MVCLoader;

public class LoggedInLayoutLoader extends MVCLoader{
	public LoggedInLayoutLoader() {
		super(() -> new LoggedInLayoutController());
	}
}
