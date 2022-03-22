package hoppin.packages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hoppin.util.QueryBuilder;

/**
 * 
 * Costruisce una query SQL utilizzata da {@link hoppin.packages.PackagesQueryBuilder}
 *
 */
public class PackagesQueryBuilder extends QueryBuilder {
	Connection conn;
	String Hotel;
	Package pack;
	String oldPackName;

	/**
	 * 
	 * @param Hotel nome dell'Hotel
	 * @param pack pacchetto che contiene i parametri da mettere nella query sql
	 * @param oldPackName nome del pacchetto da modificare, che può differire quello restituito da {@code pack.getName()}
	 * @param connection connessione al database
	 */
	public PackagesQueryBuilder(String Hotel, Package pack, String oldPackName, Connection connection) {
		super();
		this.Hotel = Hotel;
		this.pack = pack;
		this.conn = connection;
		this.oldPackName = oldPackName;
	}
	
	/**
	 * 
	 * @return restituisce una stringa usata per la query sql
	 * ad esempio, se pack.getDescription() è l'unico attributo non null, 
	 * allora restituisce UPDATE Package SET description = ? WHERE Hotel = ? and name = ?;
	 */
	public String makeQuery() {
		String name = pack.getName();
		String description = pack.getDescription();
		int price = pack.getPrice();
		
		boolean p = false;
		sb.append("UPDATE Package SET ");

		if ( ! name.equals("") ) {
			sb.append("name = ? ");
			p = true;
		}
		
		if ( ! description.equals("") ) {
			if (p == true)
				sb.append(", ");
			sb.append("description = ? ");
			p = true;
		}
		
		if ( price != 0) {
			if (p == true)
				sb.append(", ");
			sb.append("price = ?" );
			p = true;
		}
		
		sb.append("WHERE Hotel = ? and name = ?;");
		query = sb.toString();
		
		return query;
		
	}
	
	/**
	 * Utilizza il metodo precedente {@link #makeQuery()}, lo imposta a un istanza di PreparedStatement e 
	 * poi imposta i relativi parametri.
	 * 
	 * @return un istanza di PreparedStatement con una query pronta da eseguire
	 * @throws SQLException
	 */
	public PreparedStatement makeStatement() throws SQLException {
		String name = pack.getName();
		String description = pack.getDescription();
		int price = pack.getPrice();
		
		if ( conn == null) {
			return null;
		}
		
		PreparedStatement ps = conn.prepareStatement( this.makeQuery() );
		
		int p = 1;
		if ( ! name.equals("") ) {
			ps.setString(p, name);
			p++;
		}
		
		if ( ! description.equals("") ) {
			ps.setString(p, description);
			p++;
		}
		
		if ( price != 0) {
			ps.setInt(p, price);
			p++;
		}
		
		if ( p == 1) {
			return null;
		}else {
			
		}
		
		if ( p != 1) {
			ps.setString(p, Hotel);
			p++;
			ps.setString(p, oldPackName);
		}
	
		
		return ps;
	}

}
