package hoppin.util.factory;

import hoppin.util.cookie.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieFactory extends AbstractFactory {
	
	public default CookieGetter makeCookieGetter(HttpServletRequest request) {
		return new CookieGetter(request);
	}
	
	public default void makeCookieSetter(HttpServletResponse response, String user) {
		new CookieSetter(response,user);
	}
}
