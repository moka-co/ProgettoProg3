package hoppin.util.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Estensione di {@link MySQLConnect} che implementa alcuni metodi per ottenere gli id degli utenti
 *
 */
public class MySQLCookie extends MySQLConnect {
	
	/**
	 * 
	 * @param id dell'utente autenticato
	 */
	public MySQLCookie(int id) {
		super();
		this.id = id;
	}
	
	public MySQLCookie() {
		super();
	}
	
	/**
	 * 
	 * @param email dell'utente
	 * @return id dell'utente
	 */
	public int getId(String email) {
		int i = 0;
		
		try {
			PreparedStatement pss = conn.prepareStatement("select id from Employee where email = ?");
			pss.setString(1, email);
			ResultSet rs = pss.executeQuery();
			rs.next();
			i = rs.getInt("id");
		}catch (SQLException e) {
			System.out.println(e);
		}
		
		return i;
	}
	
	/**
	 * 
	 * @param i id dell'utente
	 * @return nome completo dell'utente
	 */
	public  String getNamebyId(int i) {
		String completeName = null;
		try {

			PreparedStatement pss = conn.prepareStatement("select completeName from Employee where id = ?");
			pss.setInt(1, i);
			ResultSet rs = pss.executeQuery();
			rs.next();
			completeName = rs.getString("completeName");
				
		}catch (SQLException e) {
			System.out.println(e);
		}
		
		return completeName;
	}

}
