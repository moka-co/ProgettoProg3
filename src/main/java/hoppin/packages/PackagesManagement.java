package hoppin.packages;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface PackagesStrategy {
	void run()  throws ServletException, IOException;
}

public class PackagesManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PackagesManagement() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PackagesCache cache = PackagesCache.getInstance();
		cache.setSelectedPackage("");
		
		//MySQLPackages db = new MySQLPackages();
		PackagesFactory factory = new PackagesFactory();
		int i=factory.makeCookieGetter(request).getIdbyCookies();
		MySQLPackages db = (MySQLPackages) factory.makeDatabaseConnect(request);
	
		HttpSession session = request.getSession();
		
		ArrayList<Package> PackageList = db.GetPackageList(i);
		
		session.setAttribute("PackageList", PackageList);
		
		db.disconnect();
		
		response.sendRedirect("/hoppin/PackagesManagement.jsp");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PackagesCache cache = PackagesCache.getInstance();
		PackagesFactory factory = new PackagesFactory();
		
		MySQLPackages db = (MySQLPackages) factory.makeDatabaseConnect(request);
		
		//int id=factory.makeCookieGetter(request).getIdbyCookies();
		PackagesStrategy strategy = () -> { };
		
		List<String> words = Arrays.asList("ConfirmAddPackage", "SetValue", "DeletePackage", "ConfirmEditPackage");
		
		String switched = "";
		for (String str : words) {
			if ( request.getParameter(str) != null) {
				switched = str;
			}
		}
		
		String param = request.getParameter(switched);
		
		switch (switched) {
		
		case "ConfirmAddPackage" : {
			
			strategy = () -> {
				String Npack = request.getParameter("NPack");
				String DPack = request.getParameter("DPack");
				int PPack = Integer.valueOf(request.getParameter("PPack"));
				
				db.addPackage(Npack, DPack, PPack);
				
			};
			response.sendRedirect("/hoppin/PackagesManagement");
			break;
		}
		
		//Si attiva quando si clicca su un elemento della tabella
		case "SetValue" : {
			strategy = () -> cache.setSelectedPackage(param);
			break;
		}
		
		case "DeletePackage" : {
			strategy = () -> {	
				//Se c'è un pacchetto nella cache
				if ( cache.getSelectedPackage().equals("") == false ) {
					db.deletePackage(cache.getSelectedPackage());
				}
				cache.setSelectedPackage("");
				
			};
			
			break;
		}
		
		case "ConfirmEditPackage" : {
			strategy = () -> {
				String selpac = cache.getSelectedPackage();
				if ( selpac.equals("") == false ) {
					
					Package newPac= factory.makePackage(request);
					db.editPackage(newPac , cache.getSelectedPackage() );
				}
			};
			
			response.sendRedirect("/hoppin/PackagesManagement");
			break;
		}
		
		default: {
			doGet(request, response);
			break;
		}
		
		} //end switch
		
		strategy.run();
		db.disconnect();
		
	}	

}