package hoppin.util.cookie;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


//Classe che scorre la lista di Cookies e trova il valore di un cookie desiderato
//Si restituisce -1 se non si è trovato il cookie desiderato
public class CookieGetter {
	private Cookie [] cookies;
	public CookieGetter(HttpServletRequest request){
		cookies = request.getCookies();
		
		
	}
	
	public int getIdbyCookies() {
		int i = -1;
		if ( cookies == null) {
			return i;
		}
		for (Cookie aCookie : cookies) {
			String name = aCookie.getName();
			if (name.equals("id")){
				String value = aCookie.getValue();
				i = Integer.valueOf(value);
			}
		}
		
		return i;
	}
	
	
}
