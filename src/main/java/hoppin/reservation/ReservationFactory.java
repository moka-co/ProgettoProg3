package hoppin.reservation;

import hoppin.util.factory.CookieFactory;
import jakarta.servlet.http.HttpServletRequest;


public class ReservationFactory implements CookieFactory {
	public Reservation makeReservation( HttpServletRequest request ) {
		ReservationBuilder rb = new ReservationBuilder(request);
		
		return rb.toReservation();
	}
	
	public Reservation makeReservation(HttpServletRequest request, int resId ) {
		ReservationBuilder rb = new ReservationBuilder(request);
		rb.id(resId);
		return rb.toReservation();
	}
}
