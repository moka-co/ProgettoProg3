package hoppin;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieSetter {
	public CookieSetter(HttpServletResponse response, String user){
		MySQLConnect db = new MySQLConnect();
		int id = db.getId(user);
		db.disconnect();
		
		Cookie dataCookie = new Cookie("id",Integer.toString(id));
		
		dataCookie.setMaxAge(600);
		dataCookie.setDomain("localhost");
		response.addCookie(dataCookie);
	}
}
