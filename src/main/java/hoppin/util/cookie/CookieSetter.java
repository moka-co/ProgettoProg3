package hoppin.util.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.net.CookieManager;



import hoppin.util.sql.MySQLCookie;

/**
 * Imposta l'id di un utente come valore di un cookie chiamato "id"
 * @see CookieGetter
 *
 */
public class CookieSetter {
	public static CookieManager cookieManager = null;
	
	public CookieSetter(HttpServletResponse response, String user){
		
		MySQLCookie db = new MySQLCookie();
		int id = db.getId(user);
		db.disconnect();
		
		Cookie dataCookie = new Cookie("id",Integer.toString(id));
		
		dataCookie.setMaxAge(650);
		response.addCookie(dataCookie);
	}
	

}
