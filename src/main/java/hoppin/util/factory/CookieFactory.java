package hoppin.util.factory;

import java.util.List;

import hoppin.util.cookie.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * Interfaccia che estende CookieFactory e viene implementata 
 * da quasi tutti i metodi Factory nei sottosistemi di hoppin
 *
 * @see hoppin.reservation.ReservationFactory
 * @see hoppin.employee.EmployeeFactory
 */
public interface CookieFactory extends AbstractFactory {
	
	/**
	 * @see hoppin.util.CookieGetter
	 */
	public default CookieGetter makeCookieGetter(HttpServletRequest request) {
		return new CookieGetter(request);
	}
	
	/**
	 * @see hoppin.util.CookieSetter
	 */
	public default void makeCookieSetter(HttpServletResponse response, String user) {
		new CookieSetter(response,user);
	}
	
	/**
	 * Riceve una lista di keywords, la scorre tutte finché non trova una keywords che è un parametro della richiesta HTTP, 
	 * poi ritorna questa keyword
	 * @param keywords un insieme di keyword 
	 * @param request viene usato per verificare se uno dei parametri p
	 * @return la keyword trovata oppure una stringa vuota
	 */
	public default String makeSwitched(List<String> keywords, HttpServletRequest request) {
		String switched = "";
		for (String str : keywords) { 
			if ( request.getParameter(str) != null) {
				switched = str;
				break;
			} 
		} //end for
		
		return switched;
		
	} //end method

}//end class
