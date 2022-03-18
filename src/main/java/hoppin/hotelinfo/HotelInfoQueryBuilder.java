package hoppin.hotelinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hoppin.util.QueryBuilder;

public class HotelInfoQueryBuilder extends QueryBuilder {
	Connection conn;
	HotelInfo info;

	public HotelInfoQueryBuilder(HotelInfo info, Connection connection) {
		super();
		this.info = info;
		this.conn = connection;
	}
	
	public String makeQuery() {
		
		int p = 1;
		if ( info.getVia() != null) {
			sb.append("Via = ?"); // +  '"' + info.getVia() + '"' );
			p++;
		}
		
		if ( info.getCity() != null ) {
			if ( p > 1) {
				sb.append(", ");
			}
			sb.append("City = ?"); // + '"' +  info.getCity() + '"' );
			p++;
		}
		
		if ( info.getPostcode() != null ) {
			if ( p > 1) {
				sb.append(", ");
			}
			sb.append("Postcode = ?");//; + '"' + info.getPostcode() + '"' );
			p++;
		}
		
		if ( info.getStars() != 0 ) {
			if ( p > 1) {
				sb.append(", ");
			}
			sb.append("Stars = ?"); // + '"' + info.getStars() + '"'  );
			p++;
		}
		
		if ( info.getDescription() != null) {
			if ( p > 1) {
				sb.append(", ");
			}
			sb.append("Description = ?"); // + '"' + info.getDescription() + '"' ) ;
			p++;
		}
		
		sb.append(" WHERE Name = ?"); //+ '"' + info.getName() + '"' );
		query = sb.toString();
		
		
		return query;
		
	}
	
	public PreparedStatement makeStatement() throws SQLException {
		if ( conn == null) {
			return null;
		}
		
		PreparedStatement ps = conn.prepareStatement( this.makeQuery() );
		
		int p = 1;
		if ( info.getVia() != null) {
			ps.setString(p, info.getVia()); // +  '"' + info.getVia() + '"' );
			p++;
		}
		
		if ( info.getCity() != null ) {
			if ( p > 1) {
				sb.append(", ");
			}
			ps.setString(p, info.getCity()); // + '"' +  info.getCity() + '"' );
			p++;
		}
		
		if ( info.getPostcode() != null ) {
			if ( p > 1) {
				sb.append(", ");
			}
			ps.setString(p, info.getPostcode());//; + '"' + info.getPostcode() + '"' );
			p++;
		}
		
		if ( info.getStars() != 0 ) {
			if ( p > 1) {
				sb.append(", ");
			}
			ps.setInt(p, info.getStars()); // + '"' + info.getStars() + '"'  );
			p++;
		}
		
		if ( info.getDescription() != null) {
			if ( p > 1) {
				sb.append(", ");
			}
			ps.setString(p, info.getDescription()); // + '"' + info.getDescription() + '"' ) ;
			p++;
		}
		
		ps.setString(p, info.getName());
		
		return ps;
	}

}
