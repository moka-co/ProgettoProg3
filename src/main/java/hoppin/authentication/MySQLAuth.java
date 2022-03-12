package hoppin.authentication;
import hoppin.util.sql.MySQLConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLAuth extends MySQLConnect {
	public MySQLAuth(){
		super();
	}
	
	public boolean login(String user, String passw) { 
		try {
			// ps = PreparedStatement
			// rs = ResultSet
			PreparedStatement ps = conn.prepareStatement("select id from  Employee where email = ? and passw_hash = md5(?)");
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
