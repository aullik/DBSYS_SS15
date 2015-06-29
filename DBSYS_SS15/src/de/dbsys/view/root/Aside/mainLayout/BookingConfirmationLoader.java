package de.dbsys.view.root.Aside.mainLayout;

import de.dbsys.view.MVCLoader;

public class BookingConfirmationLoader extends MVCLoader{
	public BookingConfirmationLoader() {
		super(() -> new BookingConfirmationController());
	}
}
