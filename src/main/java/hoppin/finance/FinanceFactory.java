package hoppin.finance;

import hoppin.util.factory.CookieFactory;
import hoppin.util.sql.MySQLConnect;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Implementa {@link hoppin.util.factory.CookieFactory} da cui eredita
 * il metodo {@link hoppin.util.factory.CookieFactory#makeCookieGetter(HttpServletRequest)}
 * usato anche all'interno dello stesso metodo {@link #makeDatabaseConnect(HttpServletRequest)} 
 * che costruisce e restituisce una connessione al database
 */
public class FinanceFactory implements CookieFactory {
	public MySQLConnect makeDatabaseConnect(HttpServletRequest request) {
		int i = this.makeCookieGetter(request).getIdbyCookies();
		MySQLConnect db = (MySQLConnect) new MySQLFinance(i);
		
		return db;
		
	}
}
