package hoppin.reservation;

public class ReservationCache {
	private static ReservationCache id = null;
	private int val;
	
	
	public static ReservationCache getInstance() {
		if ( id == null) {
			id = new ReservationCache();
		}
		
		return id;
	}
	
	public void setSingleValue(int x) { val = x; }
	public int getSingleValue() {return val; }

}
