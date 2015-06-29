package de.dbsys.view.root.Bside.resultLayout;

import de.dbsys.view.MVCLoader;

public class ResultLayoutLoader extends MVCLoader{
	public ResultLayoutLoader () {
		super(() -> new ResultLayoutController());
	}
}
