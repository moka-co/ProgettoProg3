package hoppin.finance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hoppin.util.QueryBuilder;

public class FinanceQueryBuilder extends QueryBuilder {
	
	Connection conn;
	String etoSeason;
	String season;
	String startDate;
	String endDate;
	
	String OLDstartDate;
	String OLDendDate;
	
	String HotelName;
	int incr;
	boolean prec = false;
	
	public void connection(Connection conn) { this.conn = conn; }
	
	public FinanceQueryBuilder(String etoSeason, String season, String startDate, String endDate, int incr, String HotelName) {
		super();
		
		this.HotelName = HotelName;
		String dates [] = etoSeason.split("&");
		OLDstartDate = dates[0];
		OLDendDate = dates[1];
		this.season = season;
		this.startDate = startDate;
		this.endDate = endDate;
		this.incr = incr;
		
		
	}

	public String makeQuery() {
		sb.append("UPDATE Season SET");
		
		boolean prec = false;
		
		if ( ! season.equals("")) {
			sb.append("Type = ?");
			prec = true;
		}
		if (! startDate.equals("") ) {
			if ( prec ) {
				sb.append(",");
			}
			sb.append(" Start = ? ");
			prec = true;
		}
		if (! endDate.equals("") ) {
			if ( prec ) {
				sb.append(",");
			}
			sb.append(" End = ? ");
			prec = true;
		}
		if (incr != 0) {
			if ( prec ) {
				sb.append(",");
			}
			sb.append(" PercentIncrease = ?");
			prec = true;
		}
		
		sb.append(" WHERE Hotel = ? and Start = ? and End = ?");
		
		query = sb.toString();
		return this.toString();
	}
	
	public PreparedStatement makeStatement() throws SQLException {
		if ( conn == null) {
			return null;
		}
		
		PreparedStatement ps = conn.prepareStatement( this.makeQuery() );
		
		int i=1;
		if ( ! season.equals("")) {
			ps.setString(1, season);
			i++;
		}
		if ( ! startDate.equals("")) {
			ps.setString(i, startDate);
			i++;
		}
		if ( ! endDate.equals("") ) {
			ps.setString(i, endDate);
			i++;
		}
		if ( incr != 0) {
			ps.setInt(i, incr);
			i++;
		}
		ps.setString(i, HotelName);
		i++;
		ps.setString(i, OLDstartDate);
		i++;
		ps.setString(i, OLDendDate);
	
		return ps;
	}
}
