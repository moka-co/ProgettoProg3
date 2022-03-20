package hoppin.finance;
import hoppin.util.sql.MySQLConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import hoppin.util.sql.MySQLgetHotelNameById;

/**
 * 
 * Effettua delle operazioni CRUD (Create, Read, Update, Delete) su database MYSQL
 * legate al sottosistema Finance di hoppin.
 * Si collega al database estendendo la superclasse {@link hoppin.util.sql.MySQLConnect}
 *
 */
public class MySQLFinance extends MySQLConnect implements MySQLgetHotelNameById {
	int id = 0;
	
	/**
	 * 
	 * @param id dell'utente autenticato
	 */
	public MySQLFinance(int id) {
		this.id = id;
	}
	public MySQLFinance() {
		super();
	}
	
	/**
	 * 
	 * @return un arrylist di due interi, il primo elemento è il profitto totale, 
	 * il secondo è il numero di prenotazoni totali
	 * @throws java.sql.SQLException
	 */
	public ArrayList<Integer> getTotProfit() {
		int sumPrice = 0;
		int totCount = 0;
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		//Query per prendere la somma del prezzo totale di ogni prenotazione e il numero di prenotazioni
		String query = " select sum(PriceList.Price) as sumPrice, count(Reservation.id) as totCount"
				+ "	from Reservation"
				+ "		INNER JOIN Room ON Room.Number = Reservation.Number"
				+ "		INNER JOIN PriceList ON Room.Type=PriceList.type_room"
				+ "		WHERE Reservation.Hotel = ? and Room.Hotel = ? and PriceList.Hotel = ?;";
		
		
		try {
			String HotelName = this.getHotelNameById(conn, id);
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			for (int i=1; i<4;i++)
				ps.setString(i, HotelName);
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				sumPrice = rs.getInt("sumPrice");
				totCount = rs.getInt("totCount");
				
				//Aggiungi gli elementi trovati dalla Query all'array
				res.add(sumPrice);
				res.add(totCount);
			}
			
			
		}catch ( SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			res.add(-1); res.add(-1);
		}
		
		return res;
	}
	
	/**
	 * 
	 * @return un arraylist di prezziari
	 * @throws java.sql.SQLException
	 */
	public ArrayList<PriceList> getPriceList(){ //PriceList = prezziario
		ArrayList<PriceList> al = new ArrayList<PriceList>(); //al = array list
		String query = "select Type_Room, Price from PriceList where Hotel = ?";
		
		try {
			String HotelName = this.getHotelNameById(conn, id);
			
			PreparedStatement ps = conn.prepareStatement(query);
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
	
	/**
	 * 
	 * @param RoomType è il tipo di stanza da aggiungere al prezziario, esempio Matrimoniale, Tripla
	 * @param price è il prezzo associato al tipo di stanza, esempio 40, 60
	 * @return {@code true} se l'aggiunta è effettuata con successo, altrimenti {@code false} 
	 * @throws java.sql.SQLException
	 */
	public boolean addElementToPriceList(String RoomType, int price) {
		String query = "insert into PriceList (Price, Type_Room, Hotel ) values ( ?, ?, ? )";
		
		try {
			String Hotel = this.getHotelNameById(conn, id);
			
			PreparedStatement ps = conn.prepareStatement(query);
			
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
	
	/**
	 * Si può modificare solo il prezzo, non il nome del tipo di stanza.
	 * @param RoomType è il tipo di stanza da modificare, esempio Matrimoniale, Tripla
	 * @param price è il nuovo prezzo da associare alla stanza, esempio 50, 70
	 * @return {@code true} se la modifica è effettuata con successo, altrimenti {@code false} 
	 * @throws java.sql.SQLException
	 */
	public boolean editPriceList(String RoomType, int price) {
		String query = "update PriceList set Price = ? where Type_Room = ? and Hotel = ?";
		
		try {
			String Hotel = this.getHotelNameById(conn, id);
			
			PreparedStatement ps = conn.prepareStatement(query);
			
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
	
	/**
	 * 
	 * @param RoomType il tipo di stanza da eliminare
	 * @return {@code true} se l'eliminazione è fatta con successo, altrimenti {@code false}
	 * @throws java.sql.SQLException
	 */
	public boolean deleteElementToPriceList(String RoomType) {
		boolean res = true;
		String query = "delete from PriceList where Hotel = ? and Type_Room = ?";
		
		try {
			String Hotel = this.getHotelNameById(conn, id);
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, Hotel);
			ps.setString(2, RoomType);
			ps.execute();
			
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return res;
	}
	
	/**
	 * 
	 * @return un array list di periodi stagionali dell'Hotel
	 * @throws java.sql.SQLException
	 */
	public ArrayList<Season> getSeason(){ //PriceList = prezziario
		ArrayList<Season> al = new ArrayList<Season>(); //al = array list
		
		String query = "select Type, Start, End, PercentIncrease from Season where Hotel = ?";
		
		try {
			String HotelName = this.getHotelNameById(conn, id);
			PreparedStatement ps = conn.prepareStatement(query);
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
	
	
	/**
	 * Aggiunge un periodo stagionale al database
	 * @param season può essere alta, media, bassa stagione
	 * @param startDate data di inizio periodo stagionale
	 * @param endDate data di fine periodo stagionale
	 * @param incr incremento percentuale
	 * @return
	 */
	public boolean addEtoSeason(String season, String startDate, String endDate, int incr) {
		String HotelName = this.getHotelNameById(conn, id);
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(" INSERT INTO Season (Type, Start, End, PercentIncrease, Hotel)"
					+ " VALUES ( ?,  ? ,  ? ,  ? , ? ) ");
			ps.setString(1, season);
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			ps.setInt(4, incr);
			ps.setString(5, HotelName);
			ps.execute();
			
		} catch ( SQLException e) {
			System.out.println(e);
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * Elimina un periodo stagionale dall'Hotel
	 * @param etoSeason una stringa che contiene periodo di inizio e di fine del periodo stagionale, seperati dal carattere {@code '&'}
	 * @return {@code true} se l'eliminazione è effettuata con successo, altrimenti {@code false}
	 */
	public boolean removeEtoSeason(String etoSeason) {
		if (etoSeason == null) {
			return false;
		}
		
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
	
	/**
	 * Modifica un periodo stagionale. utilizza {@link hoppin.finance.FinanceQueryBuilder} per costruire la query SQL da effettuare.
	 * @param etoSeason una stringa che contiene periodo di inizio e di fine del periodo stagionale, seperati dal carattere {@code '&'}
	 * serve ad identificare la stagione da modificare
	 * @param season nuovo tipo di stagione da modificare
	 * @param startDate, nuova data di inizio periodo, può essere null in tal caso NON verrà modificata la data d'inizio
	 * @param endDate nuova data di fine periodo, può essere null vedi sopra
	 * @param incr nuovo incremento percentuale, può essere null vedi sopra
	 * @return {@code true} se la modifica è effettuata con successo, altrimenti {@code false}
	 */
	public boolean editEtoSeason(String etoSeason, String season, String startDate, String endDate, int incr) {
		
		if ( etoSeason == null) {
			return false;
		}
		
		try {
			String HotelName = this.getHotelNameById(conn, id);
			
			FinanceQueryBuilder fqb = new FinanceQueryBuilder(etoSeason, season, startDate, endDate, incr, HotelName, conn);
			
			PreparedStatement ps = fqb.makeStatement();
			
			if ( ps != null) {
				ps.execute();
			}
			
			
		} catch( SQLException e) {
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	

}
