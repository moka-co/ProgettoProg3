package hoppin.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hoppin.util.QueryBuilder;

/**
 * 
 * Costruisce la query e lo statement pronto da eseguire 
 * per il metodo {@link hoppin.reservation.MySQLReservation#editReservation(Reservation)}
 *
 */
public class ReservationQueryBuilder extends QueryBuilder {
	Connection conn;
	Reservation res;

	/**
	 * 
	 * @param res istanza di Reservation con i dati da modificare della prenotazione
	 * @param connection to database
	 */
	public ReservationQueryBuilder(Reservation res, Connection connection) {
		super();
		this.res = res;
		this.conn = connection;
	}
	
	/**
	 * 
	 * @return restituisce una query sql con i parametri da inserire
	 * Ad esempio se solo res.getCheckIn() è diverso dalla stringa vuota, quindi significa che si vuole
	 * modificare la data di check-in, verrà restituita la stringa: <br>
	 * "UPDATE Reservation SET Check_in = STR_TO_DATE(?, '%d-%m-%Y') WHERE id = ?"
	 * @see java.sql.PreparedStatement
	 */
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
			if (p == true) {
				sb.append(", ");
			}
			sb.append("Package = ?");
			p = true;
		}
		
		sb.append(" WHERE id = ?;");
		
		query = sb.toString();
		return query;
	}
	
	/**
	 * Utilizza {@link #makeQuery()}
	 * @return un PreparedStatement con i parametri inseriti nella query pronto da essere eseguito
	 * @throws SQLException
	 */
	public PreparedStatement makeStatement() throws SQLException {
		if (conn == null) {
			return null;
		}
		
		String q = this.makeQuery();
		System.out.println(q);
		PreparedStatement ps = conn.prepareStatement( q );
		
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
