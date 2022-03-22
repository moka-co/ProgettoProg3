package hoppin.reservation;

/**
 * Rappresenta una prenotazione di un Hotel
 *
 */
public class Reservation {
	int employeeId;
	String customerName;
	int id;
	String hotelName;
	String roomNum;
	String checkIn;
	String checkOut;
	String pckg;
	
	public Reservation() {
		super();
	}
	
	public int getEmployeeId() { return employeeId; }
	public String getCustomerName() { return customerName; }
	/**
	 * 
	 * @return l'id della prenotazione
	 */
	public int getId() { return id; }
	public String getHotelName() { return hotelName; }
	public String getRoomNum() { return roomNum; }
	public String getCheckIn() { return checkIn.toString(); }
	public String getCheckOut() { return checkOut.toString(); }
	public String getPckg() { return pckg; }
	
	public void setEmployeeId(int id) { employeeId=id; }
	public void setCustomerName(String name) { customerName=name; }
	public void setReservationId(int id) { this.id=id; }
	/**
	 * 
	 * @param rn room number
	 */
	public void setRoomNumber(String rn) { roomNum=rn; }
	public void setCheckIn(String checkIn) { this.checkIn=checkIn; }
	public void setCheckOut(String checkOut) { this.checkOut=checkOut; }
	/**
	 * 
	 * @param pac package name
	 */
	public void setPckg(String pac) { pckg=pac; }
	

}
