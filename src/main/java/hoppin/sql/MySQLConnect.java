package hoppin.sql;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class MySQLConnect { 
	String pathToConfig = "C:/Users/39347/eclipse-workspace/hoppin/";
	Connection conn = null;
	private String passw;
	private String user;
	private String dburl;

	public MySQLConnect(){
		
		try (InputStream config = new FileInputStream(pathToConfig + "config.properties"); ){
			Properties prop = new Properties();
			prop.load(config);
			
			this.user= prop.getProperty("db.user");
	        this.passw = prop.getProperty("db.passw");
	        this.dburl= "jdbc:mysql://" + prop.getProperty("db.url");
	        if ( this.user.equals("") || this.passw.equals("") || this.dburl.equals("jdbc:mysql://")) {
	        	System.out.println("Configuration file isn't correct");
	        	throw new IOException();
	        }
	            
			this.conn = DriverManager.getConnection(this.dburl, this.user, this.passw); //Connettiti al database
			
		} catch (IOException e) { //Errore se non e' stato possibile aprire o leggere dal file di configurazione
			System.out.println("Error while getting config file, errore: ");
			System.out.println(e);
		} catch (SQLException e) { //Errore se la connessione al database non è corretta
			System.out.println(e);
			System.out.println("Error while connecting to the database");
		}
	}
	
	public void disconnect() {
		
		try {
			this.conn.close();
		} catch (SQLException e) {
			System.out.println("Error when closing DB");
		}
	}
}
