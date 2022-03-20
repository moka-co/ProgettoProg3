package hoppin.authentication;
import hoppin.util.sql.MySQLConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * Estende l'interfaccia {@link hoppin.util.sql.MySQLConnect}
 * implementa il metodo {@link #login(String, String)} 
 * che verifica se i dati di login sono corretti
 */

public class MySQLAuth extends MySQLConnect {
	
	/**
	 * 
	 * @param i ovvero l'id dell'utente autenticato nella piattaforma
	 */
	public MySQLAuth(int i) {
		super();
		id = i;
	}

	/**
	 * 
	 * @param user stringa inserita nel form di input
	 * @param passw password inserita 
	 * @return {@code true} se esiste una corrispondenza nel database, altrimenti {@code false}
	 * @throws java.sql.SQLException
	 */

	public boolean login(String user, String passw) { 
		try {

			String query = "Select id from  Employee where email = ? and passw_hash = md5(?)";
			PreparedStatement ps = conn.prepareStatement(query); 
			ps.setString(1, user);
			ps.setString(2, passw);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id = Integer.valueOf( rs.getString("id") );
			
			return (id != 0);
			
		} catch (SQLException e) { //e = Exception
			System.out.println(e);
			return false;
		}
	}
	

}
