package de.dbsys.view.root.Bside.bookingConfirmationB;

import de.dbsys.view.MVCLoader;

public class BookingConfirmationBLoader extends MVCLoader{
	public BookingConfirmationBLoader () {
		super(() -> new BookingConfirmationBController());
	}
}
