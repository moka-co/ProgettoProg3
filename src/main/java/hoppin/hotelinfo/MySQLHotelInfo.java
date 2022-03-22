package hoppin.hotelinfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import hoppin.util.sql.*;

/**
 * 
 * Effettua operazioni CRUD relative al sottosistema HotelInfoManagement su database MySQL
 * Si collega al database estendendo la superclasse {@link hoppin.util.sql.MySQLConnect}
 * 
 */
public class MySQLHotelInfo extends MySQLCookie implements MySQLgetHotelNameById {
	
	public MySQLHotelInfo() {
		super();
	}
	
	/**
	 * 
	 * @param i id dell'utente autenticato.
	 */
	public MySQLHotelInfo(int i) {
		super();
		id = i;
	}

	/**
	 * 
	 * @param id
	 * @return un istanza di HotelInfo contenente le informazioni dell'Hotel
	 * @see hoppin.hotelinfo.HotelInfo
	 */
	public HotelInfo getHotelInfo(int id) {
		HotelInfo hotel = null;
		
		String hotelName = this.getHotelNameById(conn, id);
		String query = "Select * from Hotel where Name = ?";
		
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, hotelName);
			
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				HotelInfoBuilder hib = new HotelInfoBuilder(rs);
				hotel = hib.toHotelInfo();
			}
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return hotel;
	}
	
	/**
	 * 
	 * @param id
	 * @return un array di due elemento, il primo è l'id più grande delle immagini salvate,
	 *  il secondo è il numero di immagini salvate, per un determinato Hotel.
	 */
	public Integer [] getMaxAndCountImageId(int id) {
		
		Integer [] res = new Integer[2];
		res[0] = 1;
		res[1] = 0;
		
		String Hname = this.getHotelNameById(conn, id);
		
		String queryMax = "select max(imageId) as max, count(imageId) as count from HotelImages where Hotel = ? ;";
		try {
			PreparedStatement ps = conn.prepareStatement(queryMax);
			ps.setString(1, Hname);
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				res[0] = Integer.valueOf( rs.getInt("max") ) + 1;
				res[1] = Integer.valueOf( rs.getInt("count") );
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return res;
	}
	
	/**
	 * 
	 * @param id id dell'utente autenticato
	 * @param fn filename, nome del file
	 */
	public void uploadFileName(int id, String fn) {
		final int MaxNumImages = 6; //Numero massimo di immagini caricabili
		
		Integer[] res = this.getMaxAndCountImageId(id);
		int max = res[0];
		int count = res[1];
		String Hname = this.getHotelNameById(conn, id);
		
		String query = "insert into HotelImages (Hotel, imageId, filename) VALUES ( ?, ?, ?); ";
		
		try {
			if ( count < MaxNumImages) { 
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, Hname);
				ps.setInt(2, max);
				ps.setString(3, fn);
				
				ps.execute();
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		
	}
	

	/**
	 * 
	 * @param id id dell'utente autenticato.
	 * @return una stringa che co0ntiene il nome dell'Hotel
	 */
	public String getHotelNameById(int id) {
		
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
	
	/**
	 * 
	 * @param id dell'utente autenticato
	 * @return un ArrayList di stringhe contenenti gli id delle immagini 
	 */
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
	
	/**
	 * Modifica le informazioni dell'Hotel
	 * @param info istanza HotelInfo contenente i parametri da modificare
	 */
	public void editHotelInfo(HotelInfo info) {
		
		HotelInfoQueryBuilder hiqb = new HotelInfoQueryBuilder(info, conn);

		try {
			PreparedStatement ps = hiqb.makeStatement();
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * Elimina il riferimento all'immagine dal database
	 * @param id dell'utente autenticato
	 * @param imgId id dell'immagine 
	 * 
	 * @see #getHotelImagesId(int)
	 * @see hoppin.hotelinfo.HotelInfoManagement
	 * 
	 * 
	 */
	public void deleteImg(int id, String imgId) {
		
		if ( imgId.equals("") || imgId == null) {
			return;
		}
		
		String query = "Delete from HotelImages where FileName = ? ";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, imgId);
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	
}
