package hoppin.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import hoppin.Package;

public class MySQLPackages extends MySQLConnect implements MySQLgetHotelNameById{



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
}
