package hoppin.packages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import hoppin.util.sql.*;

public class MySQLPackages extends MySQLConnect implements MySQLgetHotelNameById{



	public ArrayList<Package> GetPackageList(int i){
		ArrayList<Package> PackageList = new ArrayList<Package>();
		try {
			
			String Hname = this.getHotelNameById(conn, i);
			PreparedStatement psi = conn.prepareStatement("select * from Package where Hotel = ? ");
			psi.setString(1, Hname);
			ResultSet Rs = psi.executeQuery();
			while(Rs.next()) {
				String PackName = Rs.getString("name");
				String PackDes = Rs.getString("description");
				String PackHotel = Rs.getString("Hotel");
				int PackPrice = Rs.getInt("price");
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
			String Hname = this.getHotelNameById(conn, id);
			PreparedStatement psi =conn.prepareStatement("insert into Package (name, price, description, hotel) values (? ,? ,? ,?) ");
			psi.setString(1, NPack);
			psi.setInt(2, PPack);
			psi.setString(3, DPack);
			psi.setString(4, Hname);
			psi.execute();
		}catch(SQLException e){
			System.out.println(e);
		}		
	}
	
	public void deletePackage(int id, String pckg) {
		String Hname = this.getHotelNameById(conn, id);
		try {
			PreparedStatement ps = conn.prepareStatement("delete from Package where Hotel = ? and name = ? ");
			ps.setString(1, Hname);
			ps.setString(2, pckg);
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public void editPackage(int id, Package pac, String pacName) {
		
		if ( pac == null) {
			return;
		}
		
		String name = pac.getName();
		String description = pac.getDescription();
		int price = pac.getPrice();
		
		String Hotel = this.getHotelNameById(conn, id);
		
		String query = "";
		StringBuilder sb = new StringBuilder();
		int pnt = 1;
		sb.append("UPDATE Package SET ");

		if ( ! name.equals("") ) {
			if (pnt != 1)
				sb.append(", ");
			sb.append("name = ? ");
			pnt++;
		}
		
		if ( ! description.equals("") ) {
			if (pnt != 1)
				sb.append(", ");
			sb.append("description = ? ");
			pnt++;
		}
		
		if ( price != 0) {
			if (pnt != 1)
				sb.append(", ");
			sb.append("price = ?" );
			pnt++;
		}
		
		sb.append("WHERE Hotel = ? and name = ?;");
		query = sb.toString();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			pnt = 1;
			if ( ! name.equals("") ) {
				ps.setString(pnt, name);
				pnt++;
			}
			
			if ( ! description.equals("") ) {
				ps.setString(pnt, description);
				pnt++;
			}
			
			if ( price != 0) {
				ps.setInt(pnt, price);
				pnt++;
			}
			
			if ( pnt != 1) {
				ps.setString(pnt, Hotel);
				pnt++;
				ps.setString(pnt, pacName);
				ps.execute();
			}

			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}


}