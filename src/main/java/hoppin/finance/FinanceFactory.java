package hoppin.finance;

import hoppin.util.factory.CookieFactory;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Implementa {@link hoppin.util.factory.CookieFactory} da cui eredita
 * il metodo {@link hoppin.util.factory.CookieFactory#makeCookieGetter(HttpServletRequest)}
 * usato anche all'interno dello stesso metodo {@link #makeDatabaseConnect(HttpServletRequest)} 
 * che costruisce e restituisce una connessione al database
 */
public class FinanceFactory implements CookieFactory {
	public MySQLFinance makeDatabaseConnect(HttpServletRequest request) {
		int i = this.makeCookieGetter(request).getIdbyCookies();
		MySQLFinance db =  new MySQLFinance(i);
		
		return db;
		
	}
}
