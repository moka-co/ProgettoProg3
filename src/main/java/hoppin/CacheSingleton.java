package hoppin;

import java.util.ArrayList;
import java.util.List;

public class CacheSingleton {
	private static CacheSingleton id = null;
	private int singleValue;
	private List<Integer> values;
	
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
		if ( size != 0) {
		
			values.remove((Integer)x);
		}
	}
	
	public void setSingleValue(int x) { this.singleValue = x; }
	public int getSingleValue() {return this.singleValue; }

}
