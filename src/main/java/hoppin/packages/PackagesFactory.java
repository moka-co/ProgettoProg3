package hoppin.packages;
import hoppin.util.factory.CookieFactory;
import jakarta.servlet.http.HttpServletRequest;

public class PackagesFactory implements CookieFactory{
	
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

}