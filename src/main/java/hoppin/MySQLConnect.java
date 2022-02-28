package hoppin;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnect { //Forse questo dovrebbe diventare Singleton
	String pathToConfig = "";
	Connection conn = null;
	private String passw;
	private String user;
	private String dburl;

	public MySQLConnect(){
		
		try (InputStream config = new FileInputStream(pathToConfig + "config.properties"); ){
			Properties prop = new Properties();
			prop.load(config);
			
			this.user= prop.getProperty("db.user");
	        this.passw = prop.getProperty("db.pass");
	        this.dburl= "jdbc:mysql://" + prop.getProperty("db.url");
	            
			this.conn = DriverManager.getConnection(this.dburl, this.user, this.passw); //Establishing connection
		} catch (IOException e) {
			System.out.println("Error while getting config file");
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("Error while connecting to the database");
		}
	}
	
	public void disconnect() {
		
		try {
			this.conn.close();
		} catch (SQLException e) {
			System.out.println("Error when closing DB");
		}
	}
	
	public boolean login(String user, String passw) { 
		try {
			// ps = PreparedStatement
			// rs = ResultSet
			PreparedStatement ps = conn.prepareStatement(" select id from  Employee where email = ? and passw_hash = md5(?)  UNION select id from  Customer where email = ? and passw_hash = md5(?)");
			
			ps.setString(1, user);
			ps.setString(2, passw);
			ps.setString(3, user);
			ps.setString(4, passw);
			ResultSet rs = ps.executeQuery();
		
			if ( rs.next() == false) {
				return false;
			}
			
		} catch (SQLException e) { //e = Exception
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public boolean register(String name, String email, String passw) {
		
		try {
			PreparedStatement pss = conn.prepareStatement("select max(id) as id from User");
			ResultSet rs = pss.executeQuery();
			int i=2;
			if ( rs != null) {
				rs.next();
				i = rs.getInt("id") + 1;
			}
			rs.close();
			PreparedStatement psi = conn.prepareStatement("insert into User (id, completeName, email, passw_hash, accType ) values (?,?,?,?,?)");
			psi.setInt(1, i);
			psi.setString(2, name);
			psi.setString(3, email);
			psi.setString(4, passw);
			psi.setString(5, "Customer");
			boolean res = psi.execute();
			System.out.println("res: " + res);
			return true;
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	public int getId(String email) {
		int i = 0;
		
		try {
			PreparedStatement pss = conn.prepareStatement("select id from User where email = ?");
			pss.setString(1, email);
			ResultSet rs = pss.executeQuery();
			rs.next();
			i = rs.getInt("id");
		}catch (SQLException e) {
			System.out.println(e);
		}
		
		return i;
	}
	
	public String getNamebyId(int i) {
		String completeName = null;
		try {
			PreparedStatement pss = conn.prepareStatement("select completeName from User where id = ?");
			pss.setInt(1, i);
			ResultSet rs = pss.executeQuery();
			rs.next();
			completeName = rs.getString("completeName");
			
		}catch (SQLException e) {
			System.out.println(e);
		}
		return completeName;
	}
	
	public String getAccTypeById(int id) { //Questo metodo è richiamato da più metodi di questa classe
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
	
	public String getHotelNameById(int id) { //Questo metodo è richiamato da più metodi di questa classe
        String HotelName = "";
        try {
            String AccType = this.getAccTypeById(id);
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
	
	
	public List<String> getNEmployeeList(int i){ //Funzione da cancellare, utile solo come riferimento
		List<String> list = new ArrayList<String>();
		try {
			PreparedStatement pss = conn.prepareStatement("select completeName from User where sid = ?");
			pss.setInt(1, i);
			ResultSet rs = pss.executeQuery();
			
			while ( rs.next()) {
				list.add(rs.getString("completeName"));
			}
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		return list;
	}
	
	public boolean addEmployee(String name, String email, String passw, int id) {
		try {
			PreparedStatement pss = conn.prepareStatement("select max(id) as id from User");
			ResultSet rs = pss.executeQuery();
			int i=2;
			if ( rs != null) {
				rs.next();
				i = rs.getInt("id") + 1;
			}
			rs.close();
			PreparedStatement psi = conn.prepareStatement("insert into User (id, completeName, email, passw_hash, accType, sid ) values (?,?,?,?,?,?)");
			psi.setInt(1, i);
			psi.setString(2, name);
			psi.setString(3, email);
			psi.setString(4, passw);
			psi.setString(5, "Customer");
			psi.setInt(6, id);
			psi.execute();
			return true;
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	public ArrayList<Employee> getEmployeeList(int i){
		//ArrayList<String>[] al = null;
		
		ArrayList<Employee> al = null;
		
		try {
			PreparedStatement pss = conn.prepareStatement("select count(id) as max from User where sid = ?");
			pss.setInt(1, i);
			ResultSet rs = pss.executeQuery();
			rs.next();
			int max = rs.getInt("max");
			al = new ArrayList<Employee>();
			al.ensureCapacity(max);
			
			
			pss = conn.prepareStatement("select id,completeName,email from User where sid = ?");
			pss.setInt(1, i);
			rs = pss.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String completeName = rs.getString("completeName");
				Employee pnt = new Employee(id,email,completeName);
				al.add(pnt);
			}
			
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		return al;
	}
	
	
	public boolean deleteEmployee(List<Integer> ids) {
		try {
			int size = ids.size();
			System.out.println("Size: " + size);
			if ( size < 1) {
				System.out.println("Lista vuota");
				return false;
			}
			
			
			StringBuilder sb = new StringBuilder();
			String qistart = "( "; //Query Incognites Start
			String qiend = " )"; //QUery Incognites End
			String strep =" ?"; //String To Repeat 
			sb.append(qistart);
			for (int i=0; i<size; i++) {
				sb.append(strep);
				if ( i+1 < size)
					sb.append(", ");
			}
			sb.append(qiend);
			
			String idsToDelete = sb.toString();
			System.out.println("Stringa costruita: " + idsToDelete);
			PreparedStatement ps = conn.prepareStatement("delete from User where id IN " + idsToDelete);
			
			for (int i=0; i<size;i++) {
				ps.setInt(i+1, ids.get(i));
			}
			boolean res = ps.execute(); //ps return true or false
			System.out.println("Query ritorna: " + res);
			return res;
			
		} catch( SQLException e) {
			System.out.println(e);
		}
		
		return false;
		
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
			
			
			pss = conn.prepareStatement("select id,CustomerName,RoomNum, CheckIn, CheckOut, Package from Reservation where HotelName = ?");
			pss.setString(1, HotelName);
			rs = pss.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String CustomerName = rs.getString("CustomerName");
				String RoomNum = rs.getString("RoomNum");
				String CheckIn = rs.getString("CheckIn");
				String CheckOut = rs.getString("CheckOut");
				String pckg = rs.getString("Package");
				Reservation pnt = new Reservation(CustomerName, id, RoomNum, CheckIn, CheckOut, pckg );
				al.add(pnt);
			}
			
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		return al;
	}
	
	public boolean addReservation(String name, String room, String checkin, String checkout, String pckg) {
		try {
			PreparedStatement pss = conn.prepareStatement("select max(id) as id from Reservation");
			ResultSet rs = pss.executeQuery();
			int id = -1;
			if ( rs != null) {
				rs.next();
				id = rs.getInt("id") + 1;
			}
			rs.close();
			
			//get Hotel Name
			pss = conn.prepareStatement("select HotelName from Room where Num= ?;");
			pss.setString(1, room);
			rs = pss.executeQuery();
			rs.next();
			String HotelName = rs.getString("HotelName");
			
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
			
			PreparedStatement psi = conn.prepareStatement("INSERT INTO Reservation (CustomerName, id, HotelName, RoomNum, CheckIn, CheckOut, Package) "
					+ " VALUES (?, ?, ?, ?, STR_TO_DATE( ?,  '%d-%m-%Y'), STR_TO_DATE( ?, '%d-%m-%Y' ) , ? )");
			psi.setString(1, name);
			psi.setInt(2, id);
			psi.setString(3, HotelName);
			psi.setString(4, room);
			psi.setString(5, checkin);
			psi.setString(6, checkout);
			psi.setString(7, pckg);
			psi.execute();
			return true;
			
		} catch (SQLException e) {
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
	
	public boolean editReservation(Reservation res) {
		DateTimeFormatter oformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter nformat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate Datecheckin = null;
		LocalDate Datecheckout = null;
		
		try {
			StringBuilder sb = new StringBuilder();
	
			sb.append("UPDATE Reservation SET ");
			if ( res.getRoomNum() != "") {
				System.out.println("passato res.getRoomNum(): " + res.getRoomNum());
				sb.append("roomNum = ?, ");
			}
			if ( res.getCheckIn() != "") {
				sb.append("checkIn = STR_TO_DATE(? , '%d-%m-%Y'), ");
				Datecheckin = LocalDate.parse(res.getCheckIn(),oformat);
			}
			
			if ( res.getCheckOut() != "") {
				sb.append("checkOut = STR_TO_DATE(? , '%d-%m-%Y')");
				Datecheckout = LocalDate.parse(res.getCheckOut(),oformat);
			}
			
			if ( res.getPckg() != "") {
				sb.append("Package = ?");
			}
			sb.append(" WHERE id = ?");
			String psQuery = sb.toString(); //ps = PreparedStatement
			System.out.println("Stringa costruita: " + psQuery);
			
			PreparedStatement pss = conn.prepareStatement(psQuery);
			
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
	
	public ArrayList<Integer> getTotProfit(int id) {
		PreparedStatement ps;
		//ResultSet rs;
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		try {
			//Check Account Type
			String AccType = "";
			String HotelName ="";
			ResultSet rs;
			
			HotelName = this.getHotelNameById(id);			
			
			//Query per prendere la somma del prezzo totale di ogni prenotazione e il numero di prenotazioni
			ps = conn.prepareStatement(" select sum(PriceList.Price) as sumPrice, count(Reservation.id) as totCount"
					+ "	from Reservation"
					+ "		INNER JOIN Room ON Room.Num = Reservation.RoomNum"
					+ "		INNER JOIN PriceList ON Room.Type=PriceList.RoomType"
					+ "		WHERE Reservation.HotelName= ? ;"
					);
			ps.setString(1, HotelName);
			rs = ps.executeQuery();
			rs.next();
			int sumPrice = rs.getInt("sumPrice");
			int totCount = rs.getInt("totCount");
			
			//Aggiungi gli elementi trovati dalla Query all'array
			res.add(sumPrice);
			res.add(totCount);
			
			
		}catch ( SQLException e) {
			System.out.println(e);
			res.add(-1); res.add(-1);
		}
		
		return res;
	}
	
	public ArrayList<Package> GetPackageList(int i){
		ArrayList<Package> PackageList = new ArrayList<Package>();
	
		try {
			
			PreparedStatement psi = conn.prepareStatement("select sid from User where id = ? ");
			int idprop;
			String Hname;
			psi.setInt(1,i);
			
			ResultSet Rs = psi.executeQuery();
			Rs.next();
			
			idprop = Rs.getInt("sid");
			psi = conn.prepareStatement("select Name from Hotel where OwnerId = ?");
			psi.setInt(1, i);
			
			Rs = psi.executeQuery();
			Rs.next();
			Hname = Rs.getString("Name");
			
			psi = conn.prepareStatement("select * from Package where Hotel = ? ");
			psi.setString(1, Hname);
			Rs = psi.executeQuery();
			
			while(Rs.next()) {
				
				String 	PackName = Rs.getString("name");			
				String 	PackDes = Rs.getString("description");
				String 	PackHotel = Rs.getString("Hotel");
				int	PackPrice = Rs.getInt("price");
				
				Package pack = new Package(PackName,PackDes,PackHotel,PackPrice);
				
				PackageList.add(pack);
			}
			
			
		}catch(SQLException e){
			System.out.println(e);
			return null;
		}
		
		return PackageList;
	}
	
	public void AddPackage(int id,String NPack,String DPack,int PPack) {
	
	try {
		String Hname = this.getHotelNameById(id);
		PreparedStatement psi =conn.prepareStatement("insert into package (name, price, description, hotel) values (? ,? ,? ,?) ");
		psi.setString(1, NPack);
		psi.setInt(2, PPack);
		psi.setString(3, DPack);
		psi.setString(4, Hname);
	
		psi.execute();
		
	}catch(SQLException e){
		System.out.println(e);
	}
	
}
	
	public ArrayList<PriceList> getPriceList(int id){ //PriceList = prezziario
        ArrayList<PriceList> al = new ArrayList<PriceList>(); //al = array list
       
        try {
            String HotelName = this.getHotelNameById(id);
            PreparedStatement ps = conn.prepareStatement("select RoomType, Price from PriceList where Hotel = ?");
            ps.setString(1, HotelName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String RoomType = rs.getString("RoomType");
                int price = rs.getInt("Price");
                PriceList pl = new PriceList(RoomType,price);
                al.add(pl);
            }
           
        } catch ( SQLException e) {
            System.out.println(e);
           
        }
       
        return al;
    }


	public ArrayList<Integer> getTotProfit(int id) {
		PreparedStatement ps;
		//ResultSet rs;
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		try {
			//Check Account Type
			//String AccType = "";
			String HotelName ="";
			ResultSet rs;
			
			
			// Get Hotel Name da rimuovere e testare
			ps = conn.prepareStatement("select Name from Hotel where OwnerId = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rs.next();
			HotelName = rs.getString("Name");
			//____________________________________________________________________________________-
			
			//la riga 420 va bene
			HotelName = this.getHotelNameById(id);
			
			
			//Query per prendere la somma del prezzo totale di ogni prenotazione e il numero di prenotazioni
			ps = conn.prepareStatement(" select sum(PriceList.Price) as sumPrice, count(Reservation.id) as totCount"
					+ "	from Reservation"
					+ "		INNER JOIN Room ON Room.Num = Reservation.RoomNum"
					+ "		INNER JOIN PriceList ON Room.Type=PriceList.RoomType"
					+ "		WHERE Reservation.HotelName= ? ;"
					);
			ps.setString(1, HotelName);
			rs = ps.executeQuery();
			rs.next();
			int sumPrice = rs.getInt("sumPrice");
			int totCount = rs.getInt("totCount");
			
			//Aggiungi gli elementi trovati dalla Query all'array
			res.add(sumPrice);
			res.add(totCount);
			
			
		}catch ( SQLException e) {
			System.out.println(e);
			res.add(-1); res.add(-1);
		}
		
		return res;
	}
	
	public String getAccTypeById(int id) { //Questo metodo è richiamato da più metodi di questa classe
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
	
	
	public String getHotelNameById(int id) { //Questo metodo è richiamato da più metodi di questa classe
		String HotelName = "";
		try {
			String AccType = this.getAccTypeById(id);
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
	
	
	public ArrayList<PriceList> getPriceList(int id){ //PriceList = prezziario
		ArrayList<PriceList> al = new ArrayList<PriceList>(); //al = array list
		
		try {
			String HotelName = this.getHotelNameById(id);
			PreparedStatement ps = conn.prepareStatement("select RoomType, Price from PriceList where Hotel = ?");
			ps.setString(1, HotelName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String RoomType = rs.getString("RoomType");
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
			String Hotel = this.getHotelNameById(id);
			PreparedStatement ps = conn.prepareStatement("insert into PriceList (Price, RoomType, Hotel ) values ( ?, ?, ? )");
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
			String Hotel = this.getHotelNameById(id);
			PreparedStatement ps = conn.prepareStatement("update PriceList set Price = ? where RoomType = ? and Hotel = ?");
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
			String Hotel = this.getHotelNameById(id);
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
			String HotelName = this.getHotelNameById(id);
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
		String HotelName = this.getHotelNameById(id);
		
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
		String HotelName = this.getHotelNameById(id);
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
		String HotelName = this.getHotelNameById(id);
		String dates [] = etoSeason.split("&");
		String OLDstartDate = dates[0];
		String OLDendDate = dates[1];
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE Season SET");
			//bitmap in java che controlla se il precedente è 1 allora metti la virgola
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
			
			//un idea per migliorare la leggibilità del codice potrebbe essere
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


