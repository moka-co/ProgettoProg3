package hoppin.reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import hoppin.util.sql.*;

public class MySQLReservation extends MySQLConnect implements MySQLgetHotelNameById {
	public MySQLReservation() {
		super();
	}
	
	public ArrayList<Reservation> getReservationList(int OwnerId){
		//ArrayList<String>[] al = null;
		
		ArrayList<Reservation> al = null;
		
		try {
			PreparedStatement pss = conn.prepareStatement("select Name from Hotel where OwnerId = ?");
			pss.setInt(1, OwnerId);
			ResultSet rs = pss.executeQuery();
			rs.next();
			String HotelName = rs.getString("Name");
			al = new ArrayList<Reservation>();
			
			//^--Questo dovrebbe diventare un altro metodo
			
			
			pss = conn.prepareStatement("select id,Name,Number, Check_In, Check_Out, Package from Reservation where Hotel = ?");
			pss.setString(1, HotelName);
			rs = pss.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String CustomerName = rs.getString("Name");
				String RoomNum = rs.getString("Number");
				String CheckIn = rs.getString("Check_In");
				String CheckOut = rs.getString("Check_Out");
				String pckg = rs.getString("Package");
				Reservation pnt = new Reservation(CustomerName, id, RoomNum, CheckIn, CheckOut, pckg );
				al.add(pnt);
			}
			
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		return al;
	}
	
	public boolean addReservation(int id, Reservation res) {
		try {
			PreparedStatement pss = conn.prepareStatement("select max(id) as id from Reservation");
			ResultSet rs = pss.executeQuery();
			int ReservationId = -1;
			if ( rs != null) {
				rs.next();
				ReservationId = rs.getInt("id") + 1;
			}
			rs.close();
			
			//get Hotel Name
			pss = conn.prepareStatement("select Hotel from Room where Number= ?;");
			pss.setString(1, res.getRoomNum());
			rs = pss.executeQuery();
			rs.next();
			String HotelName = rs.getString("Hotel");
			
			String checkin = res.getCheckIn();
			String checkout = res.getCheckOut();
			
			
			//Conversione del formato HTML:
			//HTML ha come formato yyyy-mm-dd, bisogna convertirlo prima di inserire
			//i dati nella query in dd-mm-yyyy
			System.out.print("Checkin: " + checkin + " | Checkout: " + checkout + "\n");
			System.out.println("");
			
			DateTimeFormatter oformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter nformat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate Datecheckin = LocalDate.parse(checkin,oformat);
			LocalDate Datecheckout = LocalDate.parse(checkout,oformat);

			
			checkin = Datecheckin.format(nformat).toString();
			checkout = Datecheckout.format(nformat).toString();
			System.out.print("Checkin: " + checkin + " | Checkout: " + checkout + "\n");
			
			PreparedStatement psi = conn.prepareStatement("INSERT INTO Reservation (Name, id, Hotel, Number, Check_In, Check_Out, Package) "
					+ " VALUES (?, ?, ?, ?, STR_TO_DATE( ?,  '%d-%m-%Y'), STR_TO_DATE( ?, '%d-%m-%Y' ) , ? )");
			
			
			psi.setString(1, res.getCustomerName());
			psi.setInt(2, ReservationId);
			psi.setString(3, HotelName);
			psi.setString(4, res.getRoomNum());
			psi.setString(5, checkin);
			psi.setString(6, checkout);
			psi.setString(7, res.getPckg());
			psi.execute();
			return true;
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	
	public boolean editReservation(Reservation res) { //fare refactoring di questa
		DateTimeFormatter oformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter nformat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate Datecheckin = null;
		LocalDate Datecheckout = null;
		
		try {
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
				Datecheckin = LocalDate.parse(res.getCheckIn(),oformat);
				p++;
			}
			
			if ( res.getCheckOut() != "") {
				if ( p != 0) {
					sb.append(", ");
				}
				sb.append("Check_out = STR_TO_DATE(? , '%d-%m-%Y') ");
				Datecheckout = LocalDate.parse(res.getCheckOut(),oformat);
				p++;
			}
			
			if ( res.getPckg() != "") {
				sb.append("Package = ?");
				p++; 
			}
			
			sb.append(" WHERE id = ?");
			
			String query = sb.toString();
			
			PreparedStatement pss = conn.prepareStatement(query);
			
			int pstack = 1;
			if ( res.getRoomNum() != "") {
				pss.setString(pstack, res.getRoomNum());
				pstack++;
			}
			if ( res.getCheckIn() != "") {
				String checkin = Datecheckin.format(nformat).toString();
				pss.setString(pstack, checkin);
				pstack++;
			}
			
			if ( res.getCheckOut() != "") {
				String checkout = Datecheckout.format(nformat).toString();
				pss.setString(pstack, checkout);
				pstack++;
			}
			if ( res.getPckg() != "") {
				pss.setString(pstack, res.getPckg());
				pstack++;
			}
			
			pss.setInt(pstack, res.getId());
			if ( pstack == 1)
				return false;
			
			pss.executeUpdate();
			
			return true;
		} catch ( SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	public boolean deleteReservation(int id) {
		try {
		PreparedStatement pss = conn.prepareStatement("delete from Reservation where id = ? ");
		pss.setInt(1, id);
		pss.execute();
		return true;
		} catch ( SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
}
