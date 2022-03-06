package hoppin.sql;
import hoppin.HotelInfo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hoppin.builder.HotelInfoBuilder;

public class MySQLHotelInfo extends MySQLCookie implements MySQLgetHotelNameById {
	
	public MySQLHotelInfo() {
		super();
	}

	public HotelInfo getHotelInfo(int id) {
		HotelInfo hotel = null;
		
		String hotelName = this.getHotelNameById(conn, id);
		String query = "Select * from Hotel where Name = ?";
		
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, hotelName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			HotelInfoBuilder hib = new HotelInfoBuilder(rs);
			hotel = hib.toHotelInfo();
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return hotel;
	}
}
