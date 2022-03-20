package hoppin.finance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hoppin.util.QueryBuilder;

/**
 * 
 * Costruisce una stringa di id di impiegati da eliminare
 * utilizzata dal metodo {@link hoppin.employee.MySQLEmployee#deleteEmployee(java.util.List)}
 */
public class FinanceQueryBuilder extends QueryBuilder {
	
	Connection conn;
	String etoSeason; //Identificativo della tabella delle stagioni (alta, media, bassa stagione)
	String season; //Stagione (alta, media, bassa)
	String startDate; //nuova data di inizio periodo da inserire
	String endDate; //nuova data di fine periodo da inserire
	
	String OLDstartDate;
	String OLDendDate;
	
	String HotelName;
	int incr; //incremento percentuale
	boolean prec = false;
	
	public void connection(Connection conn) { this.conn = conn; }
	
	/**
	 * 
	 * @param etoSeason identificativo della tabella delle stagioni costituito da periodo_inizio + "\&" + periodo_fine"
	 * @param season alta, media, bassa stagione
	 * @param startDate nuova data di inizio periodo da inserire
	 * @param endDate nuova data di fine periodo da inserire
	 * @param incr incremento percentuale da inserire
	 * @param HotelName nome dell'Hotel
	 * @param conn connessione al database
	 */
	public FinanceQueryBuilder(String etoSeason, String season, String startDate, String endDate, int incr, String HotelName, Connection conn) {
		super();
		
		this.HotelName = HotelName;
		String dates [] = etoSeason.split("&");
		OLDstartDate = dates[0];
		OLDendDate = dates[1];
		this.season = season;
		this.startDate = startDate;
		this.endDate = endDate;
		this.incr = incr;
		this.conn = conn;
		
		
	}
	
	/**
	 * Costruisce una query SQL usata per aggiornare la tabella Season
	 * 
	 * @return ritorna una stringa del tipo UPDATE SEASON SET nome_colonna = ? ... WHERE Hotel = ? ...
	 * in base ai parametri che sono ricevuti alla costruzione della classe
	 */
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
	
	/**
	 * 
	 * @return restituisce un istanza di PreparedStatement con la query e i dati inseriti, pronta per l'esecuzione.
	 * @throws SQLException quando c'è un problema con la connessione al database
	 */
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
