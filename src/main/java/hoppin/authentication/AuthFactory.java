package hoppin.authentication;

import hoppin.util.factory.CookieFactory;
import hoppin.util.sql.MySQLConnect;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * Implementa {@link hoppin.util.factory.CookieFactory}
 * Nasconde la costruzione di {@link MySQLConnect} e la inizializza
 *
 */

public class AuthFactory implements CookieFactory {

	@Override
	public MySQLConnect makeDatabaseConnect(HttpServletRequest request) {
		int i = this.makeCookieGetter(request).getIdbyCookies();
		MySQLConnect db = (MySQLConnect) new MySQLAuth(i);
		
		return db;
		
	}
}
