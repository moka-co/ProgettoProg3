package hoppin.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hoppin.util.sql.MySQLgetHotelNameById;

/**
 * 
 * Interfaccia che estende {@link MySQLgetAccTypeById} e la usa per ottenere il nome dell'Hotel dall'id.
 * Se l'utente è di tipo Owner, allora basta fare una query banale per ottenere il nome dell'Hotel a partire dall'Id.
 * Se l'utente è di tipo Employee, allora si fa una query innestata per ottenere l'id del proprietario e poi si effettua la query di sopra.
 *
 */
public interface MySQLgetHotelNameById extends MySQLgetAccTypeById {
	public default String getHotelNameById(Connection conn, int id) { //Questo metodo e' richiamato da piu' metodi di questa classe
		
		String HotelName = "";
		try {
			String AccType = this.getAccTypeById(conn, id);
			if ( AccType.equals("Owner")) {
				PreparedStatement ps = conn.prepareStatement("select Name from Hotel where OwnerId = ?");
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				rs.next();
				HotelName = rs.getString("Name");
				
			}else if (AccType.equals("Employee")) {
				PreparedStatement ps = conn.prepareStatement("select Name from Hotel where OwnerId = (select sid from Employee where id = ?) ");
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				rs.next();
				HotelName = rs.getString("Name");
			}
			
		}catch (SQLException e) {
			System.out.println(e);
		}
		
		return HotelName;
	}
}