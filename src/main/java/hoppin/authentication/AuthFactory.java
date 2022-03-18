package hoppin.authentication;

import hoppin.util.factory.CookieFactory;
import hoppin.util.sql.MySQLConnect;
import jakarta.servlet.http.HttpServletRequest;


public class AuthFactory implements CookieFactory {

	@Override
	public MySQLConnect makeDatabaseConnect(HttpServletRequest request) {
		int i = this.makeCookieGetter(request).getIdbyCookies();
		MySQLConnect db = (MySQLConnect) new MySQLAuth(i);
		
		return db;
		
	}
}
