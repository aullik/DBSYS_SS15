package de.dbsys.view.root.Bside.bookingConfirmation;

import de.dbsys.view.MVCLoader;
import de.dbsys.view.root.Bside.resultLayout.ResultLayoutLoader;


public class BookingConfirmationLoader extends MVCLoader {

   public BookingConfirmationLoader(final ResultLayoutLoader rLL) {
      super(() -> new BookingConfirmationController(rLL));
   }
}
