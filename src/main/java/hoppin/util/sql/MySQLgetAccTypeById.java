package hoppin.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface MySQLgetAccTypeById {
	public default String getAccTypeById(Connection conn, int id) { 
		String AccType="";
		
		try {
		PreparedStatement ps = conn.prepareStatement("select accType from Employee where id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		if (rs.getString("accType") != null) {
			AccType = rs.getString("accType");
		}
		
		} catch  ( SQLException e) {
			System.out.println(e);
		}
		
		return AccType;
	}
}