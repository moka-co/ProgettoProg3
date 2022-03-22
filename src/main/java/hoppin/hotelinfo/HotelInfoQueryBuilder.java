package hoppin.hotelinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hoppin.util.QueryBuilder;

/**
 * 
 * Costruisce una query SQL utilizzata da {@link hoppin.hotelinfo.MySQLHotelInfo}
 *
 */
public class HotelInfoQueryBuilder extends QueryBuilder {
	Connection conn;
	HotelInfo info;

	/**
	 * 
	 * @param info un istanza di HotelInfo le cui informazioni verrano utilizzate per costruire la query
	 * @param connection connessione al database
	 */
	public HotelInfoQueryBuilder(HotelInfo info, Connection connection) {
		super();
		this.info = info;
		this.conn = connection;
	}
	
	/**
	 * Controlla se i parametri di {@code HotelInfo} passati sono diversi da null, in tal caso li aggiunge come parametri
	 * alla query SQL.
	 * Per esempio se info.getVia(), info.getCity() e info.getPostcode() sono gli unici diversi da null
	 * in altre parole, si sta cercando di modificare la via, la città e il codice postale dell'Hotel, il metodo restituirà 
	 * 
	 * "Update Hotel SET Via = ?, City = ?, Postcode = ? WHERE Name = ?"
	 * 
	 * 
	 * 
	 * @return una query che dovrà essere passata a una classe di tipo {@link java.sql.PreparedStatement}
	 */
	public String makeQuery() {
		
		sb.append("Update Hotel SET ");
		int p = 1;
		if ( info.getVia() != null) {
			sb.append("Via = ?"); 
			p++;
		}
		
		if ( info.getCity() != null ) {
			if ( p > 1) {
				sb.append(", ");
			}
			sb.append("City = ?");
			p++;
		}
		
		if ( info.getPostcode() != null ) {
			if ( p > 1) {
				sb.append(", ");
			}
			sb.append("Postcode = ?");
			p++;
		}
		
		if ( info.getStars() != 0 ) {
			if ( p > 1) {
				sb.append(", ");
			}
			sb.append("Stars = ?"); 
			p++;
		}
		
		if ( info.getDescription() != null) {
			if ( p > 1) {
				sb.append(", ");
			}
			sb.append("Description = ?"); 
			p++;
		}
		
		sb.append(" WHERE Name = ?");
		query = sb.toString();
		
		
		return query;
		
	}
	
	/**
	 * Utilizza il metodo precedente {@link #makeQuery()}, lo imposta a un istanza di PreparedStatement e 
	 * poi imposta i relativi parametri.
	 * 
	 * @return un istanza di PreparedStatement con una query pronta da eseguire
	 * @throws SQLException
	 * @see hoppin.hotelinfo.MySQLHotelInfo
	 * @see java.sql.PreparedStatement
	 */
	public PreparedStatement makeStatement() throws SQLException {
		if ( conn == null) {
			return null;
		}
		
		PreparedStatement ps = conn.prepareStatement( this.makeQuery() );
		
		int p = 1;
		if ( info.getVia() != null) {
			ps.setString(p, info.getVia());
			p++;
		}
		
		if ( info.getCity() != null ) {
			if ( p > 1) {
				sb.append(", ");
			}
			ps.setString(p, info.getCity()); 
			p++;
		}
		
		if ( info.getPostcode() != null ) {
			if ( p > 1) {
				sb.append(", ");
			}
			ps.setString(p, info.getPostcode());
			p++;
		}
		
		if ( info.getStars() != 0 ) {
			if ( p > 1) {
				sb.append(", ");
			}
			ps.setInt(p, info.getStars());
			p++;
		}
		
		if ( info.getDescription() != null) {
			if ( p > 1) {
				sb.append(", ");
			}
			ps.setString(p, info.getDescription()); 
			p++;
		}
		
		ps.setString(p, info.getName());
		
		return ps;
	}

}
