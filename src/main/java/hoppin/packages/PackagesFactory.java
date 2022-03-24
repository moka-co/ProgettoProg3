package hoppin.packages;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hoppin.hotelinfo.HotelInfoManagement;
import hoppin.util.factory.CookieFactory;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * Nasconde la costruzione di alcuni oggetti a {@link HotelInfoManagement} e li restituisce. <br>
 * Implementa {@link hoppin.util.factory.CookieFactory} del quale utilizza
 * {@link #makeCookieGetter(HttpServletRequest)} per ottenere l'id dell'utente autenticato.
 *
 *	Implementa metodi per costruire {@link hoppin.packages.PackagesFactory} 
 */
public class PackagesFactory implements CookieFactory{
	
	/**
	 * Crea una connessione al database
	 * @param request richiesta che contiene un cookie con valore l'id dell'utente
	 * @return restituisce una connessione del tipo {@link hoppin.util.sql.MySQLConnect}
	 * @see hoppin.util.factory.CookieFactory
	 * @see hoppin.util.sql.MySQLConnect
	 */
	public MySQLPackages makeDatabaseConnect(HttpServletRequest request) {
		int i = this.makeCookieGetter(request).getIdbyCookies();
		MySQLPackages db = new MySQLPackages(i);
		
		return db;
		
	}
	
	/**
	 * Costruire un istanza della classe Package
	 * @param request che contiene i parametri i cui valori servono a costruire package
	 * @return un istanza di Package
	 * @see hoppin.packages.Package
	 */
	public Package makePackage(HttpServletRequest request) {
		String name = request.getParameter("NPack");
		String description = request.getParameter("DPack");
		String temp = request.getParameter("PPack");
		int price = 0;
		if ( temp.equals("") == false ) {
			price = Integer.valueOf(temp);
		}
		
		Package newPac= new Package(name, description, price);
		
		return newPac;
	} 
	
	/**
	 *
	 * @param rs ResultSet che contiene elementi per costruire i pacchetti
	 * @return un ArrayList contenente tutti i pacchetti dell'Hotel in cui lavora l'utente che fa la richiesta
	 * @throws SQLException
	 */
	public ArrayList<Package> makePackageList(ResultSet rs) throws SQLException {
		ArrayList<Package> packageList = new ArrayList<Package>();
		
		while(rs.next()) {
			String packName = rs.getString("name");
			String packDes = rs.getString("description");
			String packHotel = rs.getString("Hotel");
			int packPrice = rs.getInt("price");
			Package pack = new Package(packName,packDes,packHotel,packPrice);
			packageList.add(pack);
		}
		
		return packageList;
	} //end method

}