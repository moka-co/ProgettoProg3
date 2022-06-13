package hoppin.util.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * 
 * Usa l'absolute path definito da {@code pathToConfig} cioè {@value pathToConfig} 
 * per caricare i dati inseriti nel file di configurazione <code>config.properties</code>
 * E' implementato come un Singleton per evitare la creazione di altre istanze superflue
 * 
 * @see {@link hoppin.sql.MySQLConnect}
 * @see {@link hoppin.hotelinfo.HotelInfoManagement}
 */

public class PropertyFactory {
	private static PropertyFactory id = null;
	private static final String pathToConfig = "/opt";
	private HashMap<String, String> map = null;
	
	/**
	 * Il costruttore usa un HashMap per associare ogni proprietà 
	 * presente nel file di configurazione ad una stringa, in modo che sia facilmente
	 * raggiungibile da qualunque classe nel programma.
	 */
	
	public PropertyFactory() {
		try (InputStream config = new FileInputStream(pathToConfig + "config.properties"); ){
			Properties prop = new Properties();
			prop.load(config);
			map = new HashMap<String, String>();
			
			String path = prop.getProperty("uploads.path");
			String url = prop.getProperty("db.url");
			String user = prop.getProperty("db.user");
			String passw = prop.getProperty("db.passw");
			
			
			boolean stringNull = path == null || url == null || user == null || passw == null;
			boolean stringEmpty = path.equals("") || url.equals("") || user.equals("") || passw.equals("");
			
			if ( stringNull || stringEmpty ) {
				throw new IOException();
			}
			
			map.put("url", url );
			map.put("user", user);
			map.put("password", passw );
			map.put("path", path);
	        
			
		} catch (IOException e) { //Errore se non e' stato possibile aprire o leggere dal file di configurazione
			System.out.println("Error while getting config file, errore: ");
			System.out.println(e);
		}
	}
	
	
	public static PropertyFactory getInstance() {
		if ( id == null) {
			id = new PropertyFactory();
		}
		
		return id;
	}
	
	/**
	 * 
	 * @return restituisce {@code HashMap<String, String>} contenente gli elementi del file di configurazione
	 */
	public HashMap<String, String> getPropertyMap() {
		return map;
	}
	
}
