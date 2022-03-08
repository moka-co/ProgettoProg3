package hoppin.sql;
import hoppin.HotelInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public int getMaxImageId(int id) {
		int max = 1;
		String Hname = this.getHotelNameById(conn, id);
		
		String queryMax = "select max(imageId) as max, count(imageId) as count from HotelImages where Hotel = ? ;";
		try {
			PreparedStatement ps = conn.prepareStatement(queryMax);
			ps.setString(1, Hname);
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				max = Integer.valueOf( rs.getInt("count") ) + 1;
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return max;
	}
	
	public void uploadFileName(int id, String fn) {
		int max = 1;
		int count = 0;
		String Hname = this.getHotelNameById(conn, id);
		
		String queryMax = "select max(imageId) as max, count(imageId) as count from HotelImages where Hotel = ?;";
		
		
		try {
			PreparedStatement ps = conn.prepareStatement(queryMax);
			ps.setString(1, Hname);
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				max = Integer.valueOf( rs.getInt("max") ) + 1;
				count = Integer.valueOf(  rs.getInt("count"));
			
			
				if ( count < 6) {
					String queryIns = "insert into HotelImages (Hotel, imageId, filename) VALUES ( ?, ?, ?); ";
					ps = conn.prepareStatement(queryIns);
					ps.setString(1, Hname);
					ps.setInt(2, max);
					ps.setString(3, fn);
					ps.execute();
				}
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		
	}
	

	public  String getHotelNameById(int id) {
		
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
				PreparedStatement ps = conn.prepareStatement("select Name from Hotel where OwnerId = (select sid from Employee where id = ?) ");
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
	
	public ArrayList<String> getHotelImagesId(int id){
		ArrayList<String> ids = new ArrayList<String>();
		String HotelName = this.getHotelNameById(id);
		String query = "select FileName from HotelImages where Hotel = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, HotelName);
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				ids.add( rs.getString("FileName") );
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		
		return ids;
	}
	
}
