package hoppin.util.sql;
import java.sql.*;

import hoppin.util.factory.PropertyFactory;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * 
 * Effettua una connessione ad un database MYSQL utilizzando i dati presi da 
 * {@link hoppin.util.factory.PropertyFactory}.
 * 
 *
 */
public abstract class MySQLConnect { 
	protected int id = 0;
	protected MysqlDataSource mysqlDS = null;
	
	protected Connection conn;
	protected String passw;
	protected String user;
	protected String dburl;

	/**
	 * Nel costruttore viene effettuata la connessione al database, impostando tutti i parametri necessari come user, passw e dburl
	 */
	public MySQLConnect(){
		
		try {
			
			user = PropertyFactory.getInstance().getPropertyMap().get("user");
			passw = PropertyFactory.getInstance().getPropertyMap().get("password");
			dburl = PropertyFactory.getInstance().getPropertyMap().get("url");
	        

			mysqlDS = new MysqlDataSource();
			mysqlDS.setURL(dburl);
			mysqlDS.setUser(user);
			mysqlDS.setPassword(passw);
			
			conn = mysqlDS.getConnection();
			
			
		} catch (SQLException e) { //Errore se la connessione al database non avviene in modo corretto
			System.out.println(e);
			System.out.println("Error while connecting to the database");
	
		}
	}
	
	public void disconnect() {
		
		try {
			if ( conn.isClosed() == false ) { //controlla se non è già chiusa
				conn.close();
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
