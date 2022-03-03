package hoppin.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

interface MySQLgetAccTypeById {
	public default String getAccTypeById(Connection conn, int id) { 
		String AccType="";
		
		try {
		PreparedStatement ps = conn.prepareStatement("select accType from User where id = ?");
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

interface MySQLgetHotelNameById extends MySQLgetAccTypeById {
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
				PreparedStatement ps = conn.prepareStatement("select Name from Hotel where OwnerId = (select sid from User where id = ?) ");
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
