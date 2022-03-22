package hoppin.reservation;

/**
 * 
 * Mantiene l'id della prenotazione selezionata dal front-end
 * e verrà utilizzato in caso di eliminazione o modifica di questa.
 * 
 * @see hoppin.reservation.ReservationManagement
 *
 */
public class ReservationCache {
	private static ReservationCache id = null;
	private int val;
	
	
	public static ReservationCache getInstance() {
		if ( id == null) {
			id = new ReservationCache();
		}
		
		return id;
	}
	
	/**
	 * 
	 * @param x cioè l'id della prenotazione selezionata
	 */
	public void setSingleValue(int x) { val = x; }
	/**
	 * 
	 * @return l'id della prenotazione selezionata
	 */
	public int getSingleValue() {return val; }

}
