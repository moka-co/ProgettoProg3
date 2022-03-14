package hoppin.util;

import java.util.ArrayList;
import java.util.List;

public class CacheSingleton {
	private static CacheSingleton id = null;
	private int singleValue;
	private List<Integer> values;
	private String RoomTypeId;
	private String EtoSeason;
	private String SelectdPckg;
	
	public CacheSingleton() {
		this.values = new ArrayList<Integer>();
		
	}
	
	public static CacheSingleton getInstance() {
		if ( id == null )
			id = new CacheSingleton();
		
		return id;
	}
	
	public List<Integer> getList(){
		return values;
	}
	
	public int getValue(int x) {
		for (int i=0; i<values.size(); i++) {
			if ( values.get(i) == x) {
				return x;
			}
		}
		return 0;
	}
	
	public void setValue(int x) {
		if ( x == 0) {
			return;
		}
		
		int size = values.size();
		if ( size == 0) {
			values.add(x);
		}else {
			for (int i=0; i<size; i++) {
				if ( values.get(i) == x) {
					return;
				}
			}
			values.add(x);
		}
	}
	
	public void removeValue(int x) {
		
		int size = values.size();
		if ( size != 0 && x != 0 ) {
		
			values.remove((Integer)x);
		}
	}
	
	public void setSingleValue(int x) { this.singleValue = x; }
	public int getSingleValue() {return this.singleValue; }
	
	public void setRoomTypeId(String str) { RoomTypeId=str; }
	public String getRoomTypeId() {return this.RoomTypeId; }
	
	public void setEtoSeason(String eto) { EtoSeason=eto; }
	public String getEtoSeason() {return EtoSeason; }
	
	public void setSelectedPackage(String st) { this.SelectdPckg = st; }
	public String getSelectedPackage() { return this.SelectdPckg; }

}