package hoppin.packages;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hoppin.util.factory.CookieFactory;
import hoppin.util.sql.MySQLConnect;
import jakarta.servlet.http.HttpServletRequest;

public class PackagesFactory implements CookieFactory{
	
	public MySQLConnect makeDatabaseConnect(HttpServletRequest request) {
		int i = this.makeCookieGetter(request).getIdbyCookies();
		MySQLConnect db = (MySQLConnect) new MySQLPackages(i);
		
		return db;
		
	}
	
	public Package makePackage(HttpServletRequest request) {
		String Npack = request.getParameter("NPack");
		String DPack = request.getParameter("DPack");
		String temp = request.getParameter("PPack");
		int PPack = 0;
		if ( temp.equals("") == false ) {
			PPack = Integer.valueOf(temp);
		}
		
		Package newPac= new Package(Npack, DPack, PPack);
		
		return newPac;
	}
	
	public ArrayList<Package> makePackageList(ResultSet rs) throws SQLException {
		ArrayList<Package> PackageList = new ArrayList<Package>();
		
		while(rs.next()) {
			String PackName = rs.getString("name");
			String PackDes = rs.getString("description");
			String PackHotel = rs.getString("Hotel");
			int PackPrice = rs.getInt("price");
			Package pack = new Package(PackName,PackDes,PackHotel,PackPrice);
			PackageList.add(pack);
		}
		
		return PackageList;
	}

}