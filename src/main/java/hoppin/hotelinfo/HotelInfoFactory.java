package hoppin.hotelinfo;
import hoppin.util.factory.CookieFactory;
import hoppin.util.sql.MySQLConnect;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * Nasconde la costruzione di alcuni oggetti a {@link HotelInfoManagement} e li restituisce. <br>
 * Implementa {@link hoppin.util.factory.CookieFactory} del quale utilizza
 * {@link #makeCookieGetter(HttpServletRequest)} per ottenere l'id dell'utente autenticato.
 *
 */
public class HotelInfoFactory implements CookieFactory {
	public HotelInfoFactory() {
		super();
	}
	
	/**
	 * @param request cioè una richiesta http che contiene un cookie da cui si ricava l'id dell'utente autenticato.
	 * @return Restituisce una connessione al database
	 */
	public MySQLConnect makeDatabaseConnect(HttpServletRequest request) {
		int i = this.makeCookieGetter(request).getIdbyCookies();
		MySQLConnect db = (MySQLConnect) new MySQLHotelInfo(i);
		
		return db;
		
	}

	/**
	 * @see {@link hoppin.hotelinfo.HotelInfoBuilder}
	 * @param request  Una richiesta http che contiene un cookie da cui si ricava l'id dell'utente autenticato.
	 * @return HotelInfo
	 */
	public HotelInfo makeHotelInfo(HttpServletRequest request) {
		HotelInfoBuilder hib = new HotelInfoBuilder(request);
		int id = makeCookieGetter(request).getIdbyCookies();
		MySQLHotelInfo db = new MySQLHotelInfo();
		hib.name( db.getHotelNameById(id)  );
		db.disconnect();
		
		return hib.toHotelInfo();
	}
}
