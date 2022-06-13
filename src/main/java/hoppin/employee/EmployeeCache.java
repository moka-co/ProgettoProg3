package hoppin.employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Cache implementata come singleton che mantiene una lista di id di dipendenti dell'Hotel
 *
 */
public class EmployeeCache {
	private static EmployeeCache id = null;
	private List<Integer> values;
	
	private EmployeeCache() {
		if ( values == null ) {
			values = new ArrayList<Integer>();
		}
	}
	
	public static EmployeeCache getInstance() {
		if ( id == null ) {
			id = new EmployeeCache();
		}
		
		return id;
	}

	public List<Integer> getList(){
		return values;
	}
	
	/**
	 * 
	 * @param x intero da restituire, se presente all'interno della lista
	 * @return {@code 0} oppure un intero dalla lista
	 */
	public int getValue(int x) {
		for (int i=0; i<values.size(); i++) {
			if ( values.get(i) == x) {
				return x;
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * Aggiunge un intero passato in input alla lista, se non è già presente
	 * @param x id da aggiungere alla lista
	 */
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
	
	/**
	 * 
	 * @param x id da rimuovere dalla lista
	 */
	public void removeValue(int x) {
		
		int size = values.size();
		if ( size != 0 && x != 0 ) {
		
			values.remove((Integer)x);
		}
	}
}
