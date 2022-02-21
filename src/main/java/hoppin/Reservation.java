package hoppin;
import java.time.LocalDate;

public class Reservation {
	int customerId;
	String customerName;
	int id;
	String hotelName;
	String roomNum;
	String checkIn;
	String checkOut;
	String pckg;
	

	//Probabilmente c'è bisogno di una classe Builder per Reservation perché è un po' ostico effettivamente l'inserimento
	public Reservation( String cn, int id,String rn, String ci, String co, String p) {
		//customerId=cid;
		customerName=cn;
		this.id=id;
		//hotelName=hn; Tolto perché non viene utilizzato
		roomNum=rn;
		checkIn=ci;
		checkOut=co;
		pckg=p;
		
	}
	
	public int getCustomerId() { return customerId; }
	public String getCustomerName() { return customerName; }
	public int getId() { return id; }
	public String getHotelName() { return hotelName; }
	public String getRoomNum() { return roomNum; }
	public String getCheckIn() { return checkIn.toString(); }
	public String getCheckOut() { return checkOut.toString(); }
	public String getPckg() { return pckg; }
	public int CountNulls() {
		int x = 0;
		if (customerName == null) x++;
		if (roomNum == null) x++;
		if (checkIn == null) x++;
		if (checkOut == null) x++;
		
		return x;
	}
	

}
