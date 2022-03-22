package hoppin.reservation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Costruisce un istanza di Reservation
 * 
 * @see hoppin.reservation.Reservation
 */
public class ReservationBuilder {
	Reservation res = new Reservation();
	
	//HTML ha come formato yyyy-mm-dd, si vuole il format dd-MM-yyyy
	private static final DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	/**
	 * 
	 * @param rs è un oggetto ResultSet che contiene i parametri da inserire nell'oggetto Reservation che verrà creato
	 */
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
	
	/**
	 * 
	 * @param request è un oggetto di tipo HttpServletRequest che contiene i parametri da inserire nell'oggetto Reservation che verrà creato
	 */
	public ReservationBuilder(HttpServletRequest request) {
		String name = request.getParameter("name");
		String room = request.getParameter("room");
		String checkin = request.getParameter("checkin");
		String checkout = request.getParameter("checkout");
		String pckg = request.getParameter("package");
		
		if ( name == null || room == null || checkin == null || checkout == null) {
			//nessuno di questi può essere null
			return;
		}
		
		this.name(name);
		this.room(room);
		this.checkIn( checkin);
		this.checkOut(checkout);
		this.pckg(pckg);
	}
	
	/**
	 * 
	 * @return l'oggetto di tipo Reservation che è stato costruito
	 */
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
	
	/**
	 * 
	 * @param ci Check In, una stringa nel formato "yyyy-MM-dd"
	 */
	public void checkIn(String ci) {
		res.setCheckIn( format(ci) );
	}
	
	/**
	 * 
	 * @param co Check Out, una stringa nel formato "yyyy-MM-dd"
	 */
	public void checkOut(String co) {
		res.setCheckOut( format(co) );
	}
	
	/**
	 * 
	 * @param p package, un pacchetto a cui associare la prenotazione. Se null o stringa vuota, verrà inserito un pacchetto base
	 */
	public void pckg(String p) {

		if (p == null || p.equals("")) {
			res.setPckg("Base");
			
		}else {
			res.setPckg(p);
		}
	}
	
	/**
	 * 
	 * @param date una stringa nel formato "yyyy-MM-dd"
	 * @return la data di input viene convertita in formato "dd-MM-yyyy"
	 */
	public String format(String date) {
		if ( date == null || date.equals("")) {
			return "";
		}
		
		LocalDate formatDate = LocalDate.parse(date,oldFormat);
		
		return formatDate.format(newFormat).toString();
	}
}
