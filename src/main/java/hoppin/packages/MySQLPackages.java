package hoppin.packages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import hoppin.util.sql.*;

/**
 * 
 * Effettua operazioni CRUD relative al sottosistema PackagesManagement su database MySQL
 * Si collega al database estendendo la superclasse {@link hoppin.util.sql.MySQLConnect}
 * 
 */
public class MySQLPackages extends MySQLConnect implements MySQLgetHotelNameById{

	/**
	 * 
	 * @param i id dell'utente autenticato.
	 */
	public MySQLPackages(int i) {
		super();
		id = i;
	}

	/**
	 * 
	 * @return un arraylist di pacchetti per l'Hotel dell'utente che ha fatto la richiesta
	 * @see hoppin.packages.Package
	 */
	public ArrayList<Package> getPackageList(){
		ArrayList<Package> PackageList = null;
		try {
			
			String Hname = this.getHotelNameById(conn, id);
			PreparedStatement psi = conn.prepareStatement("select * from Package where Hotel = ? ");
			psi.setString(1, Hname);
			ResultSet rs = psi.executeQuery();
			
			PackagesFactory factory = new PackagesFactory();
			PackageList = factory.makePackageList(rs);
			
		}catch(SQLException e){
			System.out.println(e);
			return null;
		}
		return PackageList;
	}



	/**
	 * Aggiunge un pacchetto alla lista
	 * @param name nome del pacchetto
	 * @param des descrizione del pacchetto
	 * @param price prezzo del pacchetto
	 */
	public void addPackage(String name,String des,int price) {
		try {
			
			String Hname = this.getHotelNameById(conn, id);
			PreparedStatement psi =conn.prepareStatement("insert into Package (name, price, description, hotel) values (? ,? ,? ,?) ");
			psi.setString(1, name);
			psi.setInt(2, price);
			psi.setString(3, des);
			psi.setString(4, Hname);
			
			psi.execute();
			
		}catch(SQLException e){
			System.out.println(e);
		}		
	}
	
	/**
	 * Elimina un pacchetto
	 * @param name nome del pacchetto
	 */
	public void deletePackage(String name) {
		String Hname = this.getHotelNameById(conn, id);
		try {
			PreparedStatement ps = conn.prepareStatement("delete from Package where Hotel = ? and name = ? ");
			ps.setString(1, Hname);
			ps.setString(2, name);
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Modifica un pacchetto già esistente nel database
	 * @param pac istanza Package con contiene i nuovi valori da inserire al posto di quelli presenti nel pacchetto
	 * @param pacName nome del pacchetto da modificare, che può essere diverso da quello contenuto in pac
	 */
	public void editPackage(Package pac, String pacName) {
		
		if ( pac == null) {
			return;
		}
		
		String Hotel = this.getHotelNameById(conn, id);
		
		PackagesQueryBuilder pqb = new PackagesQueryBuilder(Hotel, pac, pacName, conn);
		
		try {
			PreparedStatement ps = pqb.makeStatement();
			if ( ps != null) {
				ps.execute();
			}

			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}


}