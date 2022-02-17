package hoppin;

//Save a value, used by Authentication.java to serve the correct GUI

public class ServeSingleton {
	private static ServeSingleton id = null;
	private int value;
	
	public ServeSingleton() {
		
	}
	
	public static ServeSingleton getInstance() {
		if ( id == null )
			id = new ServeSingleton();
		
		return id;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int x) {
		value = x;
	}

}
