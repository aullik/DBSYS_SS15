package de.dbsys.view.root.Bside.bookingConfirmation;

import de.dbsys.view.MVCLoader;

public class BookingConfirmationLoader extends MVCLoader{
	public BookingConfirmationLoader () {
		super(() -> new BookingConfirmationController());
	}
}
