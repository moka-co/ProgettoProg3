package hoppin.employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeCache {
	private static EmployeeCache id = null;
	private List<Integer> values;
	
	public EmployeeCache() {
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
}
