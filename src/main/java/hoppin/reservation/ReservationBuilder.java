package hoppin.reservation;
import jakarta.servlet.http.HttpServletRequest;

public class ReservationBuilder {
	Reservation res = new Reservation();
	
	public ReservationBuilder(){

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
	
	public Reservation getReservation() {
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
		res.setCheckIn(ci);
	}
	
	public void checkOut(String co) {
		res.setCheckOut(co);
	}
	
	public void pckg(String p) {
		String pp = p;
		if (p == null) {
			pp = "Base";
		}
		res.setPckg(pp);
	}
}
