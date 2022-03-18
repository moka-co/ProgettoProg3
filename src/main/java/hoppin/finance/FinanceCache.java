package hoppin.finance;

public class FinanceCache {
	protected static FinanceCache id = null;
	private String RoomTypeId;
	private String EtoSeason;
	
	public FinanceCache() {
		
	}
	
	
	public static FinanceCache getInstance() {
		if ( id == null )
			id = new FinanceCache();
		
		return id;
	}
	
	public void setRoomTypeId(String str) { RoomTypeId=str; }
	public String getRoomTypeId() {return this.RoomTypeId; }
	
	public void setEtoSeason(String eto) { EtoSeason=eto; }
	public String getEtoSeason() {return EtoSeason; }
}
