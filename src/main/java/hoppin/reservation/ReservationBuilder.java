package hoppin.reservation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServletRequest;

public class ReservationBuilder {
	Reservation res = new Reservation();
	
	//HTML ha come formato yyyy-mm-dd, è desiderabile il format dd-MM-yyyy
	private static final DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public ReservationBuilder(ResultSet rs){
		try {
			this.id( rs.getInt("id") );
			this.name( rs.getString("Name" ) );
			this.room( rs.getString("Number" ) );
			this.checkIn( rs.getString("Check_In") );
			this.checkOut( rs.getString("Check_Out") );
			this.pckg( rs.getString("Package") );
			
			
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public ReservationBuilder(HttpServletRequest request) {
		String name = request.getParameter("name");
		String room = request.getParameter("room");
		String checkin = request.getParameter("checkin");
		String checkout = request.getParameter("checkout");
		String pckg = request.getParameter("package");
		
		if ( name == null || room == null || checkin == null || checkout == null) {
			System.out.println("Error while creating Reservation");
			return;
		}
		
		this.name(name);
		this.room(room);
		this.checkIn( checkin);
		this.checkOut(checkout);
		this.pckg(pckg);
	}
	
	public Reservation toReservation() {
		return res;
	}
	
	public void name(String name) {
		res.setCustomerName(name);
	}
	
	public void id(int id) {
		res.setReservationId(id);
	}
	
	public void room(String room) {
		res.setRoomNumber(room);
	}
	
	public void checkIn(String ci) {
		res.setCheckIn( format(ci) );
	}
	
	public void checkOut(String co) {
		res.setCheckOut( format(co) );
	}
	
	public void pckg(String p) {
		String pp = p;
		if (p == null) {
			pp = "Base";
		}
		res.setPckg(pp);
	}
	
	public String format(String p) {
		if ( p == null || p.equals("")) {
			return "";
		}
		
		LocalDate date = LocalDate.parse(p,oldFormat);
		
		return date.format(newFormat).toString();
	}
}
