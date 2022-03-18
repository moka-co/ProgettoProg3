package hoppin.reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hoppin.util.factory.CookieFactory;
import hoppin.util.sql.MySQLConnect;
import jakarta.servlet.http.HttpServletRequest;


public class ReservationFactory implements CookieFactory {
	
	public MySQLConnect makeDatabaseConnect(HttpServletRequest request) {
		int i = this.makeCookieGetter(request).getIdbyCookies();
		MySQLConnect db = (MySQLConnect) new MySQLReservation(i);
		
		return db;
		
	}
	
	public Reservation makeReservation( HttpServletRequest request ) {
		ReservationBuilder rb = new ReservationBuilder(request);
		
		return rb.toReservation();
	}
	
	public Reservation makeReservation(HttpServletRequest request, int resId ) {
		ReservationBuilder rb = new ReservationBuilder(request);
		rb.id(resId);
		return rb.toReservation();
	}
	
	public ArrayList<Reservation> makeReservationList(ResultSet rs) throws SQLException {
		ArrayList<Reservation> al = new ArrayList<Reservation>();
		
		while (rs.next()) {
			ReservationBuilder rb = new ReservationBuilder(rs);
			al.add( rb.toReservation() );
		}
		
		rs.close();
		
		return al;
	}
}
