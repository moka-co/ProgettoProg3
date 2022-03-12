package hoppin.util.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLCookie extends MySQLConnect {
	
	public MySQLCookie() {
		super();
	}
	
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
