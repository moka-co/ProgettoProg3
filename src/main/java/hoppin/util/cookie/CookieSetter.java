package hoppin.util.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.net.CookieManager;



import hoppin.util.sql.MySQLCookie;

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
