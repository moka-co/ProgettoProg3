package hoppin.util.sql;
import java.sql.*;

import hoppin.util.factory.PropertyFactory;

public class MySQLConnect { 
	protected Connection conn = null;
	protected String passw;
	protected String user;
	protected String dburl;

	public MySQLConnect(){
		
		try {
			
			user = PropertyFactory.getInstance().getPropertyMap().get("user");
			passw = PropertyFactory.getInstance().getPropertyMap().get("password");
			dburl = PropertyFactory.getInstance().getPropertyMap().get("url");
	          
			//Per motivi di troubleshooting
	        /*
	        try {
	        	Class.forName(“com.mysql.jdbc.Driver”);
	        } catch ( Exception e) {
	        	System.out.println(e);
	        } 
	        
	        */
	        
			conn = DriverManager.getConnection(this.dburl, this.user, this.passw); //Connettiti al database
			
		} catch (SQLException e) { //Errore se la connessione al database non è corretta
			System.out.println(e);
			System.out.println("Error while connecting to the database");
		}
	}
	
	public void disconnect() {
		
		try {
			this.conn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
