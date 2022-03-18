package hoppin.packages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hoppin.util.QueryBuilder;

public class PackagesQueryBuilder extends QueryBuilder {
	Connection conn;
	String Hotel;
	Package pack;
	String oldPackName;

	public PackagesQueryBuilder(String Hotel, Package pack, String oldPackName, Connection connection) {
		super();
		this.Hotel = Hotel;
		this.pack = pack;
		this.conn = connection;
		this.oldPackName = oldPackName;
	}
	
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
