package hoppin.finance;

/**
 * Cache implementata come singleton che mantiene una lista di: <br>
 * - RoomTypeId cioè il tipo di stanza, es Matrimoniale <br>
 * - EtoSeason cioè una stringa che rappresenta un periodo stagionale dell'Hotel <br>
 * E implementa i relativi metodi get e set
 *
 */
public class FinanceCache {
	protected static FinanceCache id = null;
	private String RoomTypeId;
	private String EtoSeason; 
	
	public static FinanceCache getInstance() {
		if ( id == null )
			id = new FinanceCache();
		
		return id;
	}
	
	
	public void setRoomTypeId(String str) { RoomTypeId=str; }
	/**
	 * 
	 * @return una stringa RoomTypeId cioè il tipo di stanza
	 */
	public String getRoomTypeId() {return this.RoomTypeId; }
	
	public void setEtoSeason(String eto) { EtoSeason=eto; }
	
	/**
	 * 
	 * @return una stringa EtoSeason che rappresenta un periodo stagionale dell'Hotel
	 */
	public String getEtoSeason() {return EtoSeason; }
	
}
