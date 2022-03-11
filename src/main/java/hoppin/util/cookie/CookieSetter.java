package hoppin.util.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import hoppin.util.sql.MySQLCookie;

public class CookieSetter {
	public CookieSetter(HttpServletResponse response, String user){
		MySQLCookie db = new MySQLCookie();
		int id = db.getId(user);
		db.disconnect();
		
		Cookie dataCookie = new Cookie("id",Integer.toString(id));
		
		dataCookie.setMaxAge(600);
		dataCookie.setDomain("localhost");
		response.addCookie(dataCookie);
	}
}
