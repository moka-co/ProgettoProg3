package hoppin.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hoppin.util.QueryBuilder;

public class ReservationQueryBuilder extends QueryBuilder {
	Connection conn;
	Reservation res;

	public ReservationQueryBuilder(Reservation res, Connection connection) {
		super();
		this.res = res;
		this.conn = connection;
	}
	
	public String makeQuery() {
		
		sb.append("UPDATE Reservation SET ");
		boolean p = false;
		if ( res.getRoomNum() != "") {
			sb.append("Number = ? ");
			p = true;
		}
		if ( res.getCheckIn() != "") {
			if ( p == true) {
				sb.append(", ");
			}
			sb.append("Check_in = STR_TO_DATE(? , '%d-%m-%Y')");
			p = true;
		}
		
		if ( res.getCheckOut() != "") {
			if ( p == true ) {
				sb.append(", ");
			}
			sb.append("Check_out = STR_TO_DATE(? , '%d-%m-%Y') ");
			p = true;
		}
		
		if ( res.getPckg() != "") {
			sb.append("Package = ?");
			p = true;
		}
		
		sb.append(" WHERE id = ?");
		
		query = sb.toString();
		return query;
	}
	
	public PreparedStatement makeStatement() throws SQLException {
		if (conn == null) {
			return null;
		}
		
		PreparedStatement ps = conn.prepareStatement( this.makeQuery() );
		
		int p = 1;
		if ( res.getRoomNum() != "") {
			ps.setString(p, res.getRoomNum());
			p++;
		}
		if ( res.getCheckIn() != "") {
			ps.setString(p, res.getCheckIn());
			p++;
		}
		
		if ( res.getCheckOut() != "") {
			ps.setString(p, res.getCheckOut());
			p++;
		}
		if ( res.getPckg() != "") {
			ps.setString(p, res.getPckg());
			p++;
		}
		
		ps.setInt(p, res.getId());
		if ( p == 1)
			return null;
		
		return ps;
	}

}
