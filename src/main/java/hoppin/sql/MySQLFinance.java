package hoppin.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hoppin.PriceList;
import hoppin.Season;

public class MySQLFinance extends MySQLConnect implements MySQLgetHotelNameById {
	
	public MySQLFinance() {
		super();
	}
	
	public ArrayList<Integer> getTotProfit(int id) {
		//ResultSet rs;
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		try {
			//Check Account Type
			//String AccType = "";
			String HotelName ="";
			ResultSet rs;
			PreparedStatement ps;
			
			int sumPrice = 0;
			int totCount = 0;
			
			HotelName = this.getHotelNameById(conn, id);
			
			
			//Query per prendere la somma del prezzo totale di ogni prenotazione e il numero di prenotazioni
			ps = conn.prepareStatement(" select sum(PriceList.Price) as sumPrice, count(Reservation.id) as totCount"
					+ "	from Reservation"
					+ "		INNER JOIN Room ON Room.Number = Reservation.Number"
					+ "		INNER JOIN PriceList ON Room.Type=PriceList.type_room"
					+ "		WHERE Reservation.Hotel = ? and Room.Hotel = ? and PriceList.Hotel = ?;"
					);
			for (int i=1; i<4;i++)
				ps.setString(i, HotelName);
			
			rs = ps.executeQuery();
			if ( rs.next() ) {
			sumPrice = rs.getInt("sumPrice");
			totCount = rs.getInt("totCount");
			}
			//Aggiungi gli elementi trovati dalla Query all'array
			res.add(sumPrice);
			res.add(totCount);
			
			
		}catch ( SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			res.add(-1); res.add(-1);
		}
		
		return res;
	}
	
	
	public ArrayList<PriceList> getPriceList(int id){ //PriceList = prezziario
		ArrayList<PriceList> al = new ArrayList<PriceList>(); //al = array list
		
		try {
			String HotelName = this.getHotelNameById(conn, id);
			PreparedStatement ps = conn.prepareStatement("select Type_Room, Price from PriceList where Hotel = ?");
			ps.setString(1, HotelName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String RoomType = rs.getString("Type_Room");
				int price = rs.getInt("Price");
				PriceList pl = new PriceList(RoomType,price);
				al.add(pl);
			}
			
		} catch ( SQLException e) {
			System.out.println(e);
			
		}
		
		return al;
	}
	
	
	public boolean addElementToPriceList(String RoomType, int price, int id) {
		try {
			String Hotel = this.getHotelNameById(conn, id);
			PreparedStatement ps = conn.prepareStatement("insert into PriceList (Price, Type_Room, Hotel ) values ( ?, ?, ? )");
			ps.setInt(1, price);
			ps.setString(2, RoomType);
			ps.setString(3, Hotel);
			ps.execute();
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public boolean editPriceList(String RoomType, int price, int id) {
		
		try {
			String Hotel = this.getHotelNameById(conn, id);
			PreparedStatement ps = conn.prepareStatement("update PriceList set Price = ? where Type_Room = ? and Hotel = ?");
			ps.setInt(1, price);
			ps.setString(2, RoomType);
			ps.setString(3, Hotel);
			ps.execute();
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;
		
	}
	
	public boolean deleteElementToPriceList(String RoomType, int id) {
		boolean res = true;
		try {
			String Hotel = this.getHotelNameById(conn, id);
			PreparedStatement ps = conn.prepareStatement("delete from PriceList where Hotel = ? and RoomType = ?");
			ps.setString(1, Hotel);
			ps.setString(2, RoomType);
			ps.execute();
			
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return res;
	}
	
	public ArrayList<Season> getSeason(int id){ //PriceList = prezziario
		ArrayList<Season> al = new ArrayList<Season>(); //al = array list
		
		try {
			String HotelName = this.getHotelNameById(conn, id);
			PreparedStatement ps = conn.prepareStatement("select Type, Start, End, PercentIncrease from Season where Hotel = ?");
			ps.setString(1, HotelName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String Type = rs.getString("Type");
				String Start = rs.getString("Start");
				String End = rs.getString("End");
				int price = rs.getInt("PercentIncrease");
				Season pl = new Season(HotelName, Type, Start, End, price);
				al.add(pl);
			}
			
		} catch ( SQLException e) {
			System.out.println(e);
			
		}
		
		return al;
	}
	
	
	public boolean addEtoSeason(int id, String season, String startDate, String endDate, int price) {
		String HotelName = this.getHotelNameById(conn, id);
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(" INSERT INTO Season (Type, Start, End, PercentIncrease, Hotel)"
					+ " VALUES ( ?,  ? ,  ? ,  ? , ? ) ");
			ps.setString(1, season);
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			ps.setInt(4, price);
			ps.setString(5, HotelName);
			ps.execute();
			
		} catch ( SQLException e) {
			System.out.println(e);
			return false;
		}
		
		return true;
		
	}
	
	public boolean removeEtoSeason(int id, String etoSeason) {
		String HotelName = this.getHotelNameById(conn, id);
		String dates [] = etoSeason.split("&");
		String startDate = dates[0];
		String endDate = dates[1];
		
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE from Season where Hotel = ? and Start = ? and End = ?");
			ps.setString(1, HotelName);
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			ps.execute();
			
		} catch( SQLException e) {
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	
	public boolean editEtoSeason(int id, String etoSeason, String season, String startDate, String endDate, int incr) {
		String HotelName = this.getHotelNameById(conn, id);
		String dates [] = etoSeason.split("&");
		String OLDstartDate = dates[0];
		String OLDendDate = dates[1];
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE Season SET");
			//bitmap in java che controlla se il precedente e' 1 allora metti la virgola
			//Altrimenti non la mettere
			boolean prec = false;
			if ( ! season.equals("")) {
				sb.append("Type = ?");
				prec = true;
			}
			if (! startDate.equals("") ) {
				if ( prec ) {
					sb.append(",");
				}
				sb.append(" Start = ? ");
				prec = true;
			}
			if (! endDate.equals("") ) {
				if ( prec ) {
					sb.append(",");
				}
				sb.append(" End = ? ");
				prec = true;
			}
			if (incr != 0) {
				if ( prec ) {
					sb.append(",");
				}
				sb.append(" PercentIncrease = ?");
				prec = true;
			}
			
			sb.append(" WHERE Hotel = ? and Start = ? and End = ?");
			String query = sb.toString();
			PreparedStatement ps = conn.prepareStatement(query);
			int i=1;
			if ( ! season.equals("")) {
				ps.setString(1, season);
				i++;
			}
			if ( ! startDate.equals("")) {
				ps.setString(i, startDate);
				i++;
			}
			if ( ! endDate.equals("") ) {
				ps.setString(i, endDate);
				i++;
			}
			if ( incr != 0) {
				ps.setInt(i, incr);
				i++;
			}
			ps.setString(i, HotelName);
			i++;
			ps.setString(i, OLDstartDate);
			i++;
			ps.setString(i, OLDendDate);
			
			//un idea per migliorare la leggibilita' del codice potrebbe essere
			//Visto che sto usando degli if per controllare quanti elementi ho
			//Allora quando vado ad inserire ad esempio Start = ?, sostituisco 
			//? direttamente con OLDstartDate e vedo se funziona
			ps.execute();
			
		} catch( SQLException e) {
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	

}
