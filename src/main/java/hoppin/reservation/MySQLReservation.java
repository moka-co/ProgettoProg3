package hoppin.reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hoppin.util.sql.*;

public class MySQLReservation extends MySQLConnect implements MySQLgetHotelNameById {
	public MySQLReservation(int i) {
		super();
		id = i;
	}
	
	public ArrayList<Reservation> getReservationList(){
		
		
		try {			
			String HotelName = this.getHotelNameById(conn, id);
			
			
			PreparedStatement pss = conn.prepareStatement("select id,Name,Number, Check_In, Check_Out, Package from Reservation where Hotel = ?");
			pss.setString(1, HotelName);
			ResultSet rs = pss.executeQuery();
			
			ReservationFactory factory = new ReservationFactory();
			ArrayList<Reservation> al = factory.makeReservationList(rs);
			
			rs.close();
			pss.close();	
			
			return al;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}

	}
	
	public boolean addReservation(Reservation res) {
		try {
			PreparedStatement ps = conn.prepareStatement("select max(id) as id from Reservation");
			ResultSet rs = ps.executeQuery();
			int ReservationId = -1;
			if ( rs != null) {
				rs.next();
				ReservationId = rs.getInt("id") + 1;
			}
			rs.close();
			ps.close();
			
			//get Hotel Name
			ps = conn.prepareStatement("select Hotel from Room where Number= ?;");
			ps.setString(1, res.getRoomNum());
			rs = ps.executeQuery();
			rs.next();
			String HotelName = rs.getString("Hotel");
			
			ps = conn.prepareStatement("INSERT INTO Reservation (Name, id, Hotel, Number, Check_In, Check_Out, Package) "
					+ " VALUES (?, ?, ?, ?, STR_TO_DATE( ?,  '%d-%m-%Y'), STR_TO_DATE( ?, '%d-%m-%Y' ) , ? )");
			
			
			ps.setString(1, res.getCustomerName());
			ps.setInt(2, ReservationId);
			ps.setString(3, HotelName);
			ps.setString(4, res.getRoomNum());
			ps.setString(5, res.getCheckIn());
			ps.setString(6, res.getCheckOut());
			ps.setString(7, res.getPckg());
			ps.execute();
			
			ps.close();
			rs.close();
			return true;
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	
	public boolean editReservation(Reservation res) {
		
		ReservationQueryBuilder rqb = new ReservationQueryBuilder(res, conn);
		
		try {
			/*
			StringBuilder sb = new StringBuilder();
	
			sb.append("UPDATE Reservation SET ");
			int p = 0;
			if ( res.getRoomNum() != "") {
				sb.append("Number = ? ");
				p++;
			}
			if ( res.getCheckIn() != "") {
				if ( p != 0) {
					sb.append(", ");
				}
				sb.append("Check_in = STR_TO_DATE(? , '%d-%m-%Y')");
				p++;
			}
			
			if ( res.getCheckOut() != "") {
				if ( p != 0) {
					sb.append(", ");
				}
				sb.append("Check_out = STR_TO_DATE(? , '%d-%m-%Y') ");
				p++;
			}
			
			if ( res.getPckg() != "") {
				sb.append("Package = ?");
				p++; 
			}
			
			sb.append(" WHERE id = ?");
			
			String query = sb.toString();
			
			PreparedStatement pss = conn.prepareStatement(query);
			
			*/
			
			PreparedStatement pss = rqb.makeStatement();
			
			/*
			int pstack = 1;
			if ( res.getRoomNum() != "") {
				pss.setString(pstack, res.getRoomNum());
				pstack++;
			}
			if ( res.getCheckIn() != "") {
				pss.setString(pstack, res.getCheckIn());
				pstack++;
			}
			
			if ( res.getCheckOut() != "") {
				pss.setString(pstack, res.getCheckOut());
				pstack++;
			}
			if ( res.getPckg() != "") {
				pss.setString(pstack, res.getPckg());
				pstack++;
			}
			
			pss.setInt(pstack, res.getId());
			if ( pstack == 1)
				return false;
			*/
			
			if ( pss != null) {
				pss.executeUpdate();
				pss.close();
			}
			
		} catch ( SQLException e) {
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	
	public boolean deleteReservation(int resId) {
		try {
			
			PreparedStatement pss = conn.prepareStatement("delete from Reservation where id = ? ");
			pss.setInt(1, resId);
			pss.execute();
		
			pss.close();
		
		} catch ( SQLException e) {
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	
}
