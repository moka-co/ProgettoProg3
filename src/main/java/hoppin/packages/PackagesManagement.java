package hoppin.packages;



import hoppin.util.CacheSingleton;

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

		CacheSingleton cache = CacheSingleton.getInstance();
		cache.setSelectedPackage("");
		
		MySQLPackages db = new MySQLPackages();
		PackagesFactory Factory = new PackagesFactory();
		int i=Factory.makeCookieGetter(request).getIdbyCookies();
	
		HttpSession session = request.getSession();
		
		ArrayList<Package> PackageList = db.GetPackageList(i);
		
		session.setAttribute("PackageList", PackageList);
		
		db.disconnect();
		
		response.sendRedirect("/hoppin/PackagesManagement.jsp");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MySQLPackages db = new MySQLPackages();
		CacheSingleton cache = CacheSingleton.getInstance();
		PackagesFactory factory = new PackagesFactory();
		int id=factory.makeCookieGetter(request).getIdbyCookies();
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
				
				db.AddPackage(id, Npack, DPack, PPack);
				
			};
			
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
					db.deletePackage(id, cache.getSelectedPackage());
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
					db.editPackage(id, newPac , cache.getSelectedPackage() );
				}
			};
			
			break;
		}
		
		default: {
			doGet(request, response);
			break;
		}
		}
		
		db.disconnect();
		strategy.run();
		

		/*
		String cap = request.getParameter("ConfirmAddPackage");
		if (cap != null) {
			
			PackagesStrategy strategy = () -> {
				String Npack = request.getParameter("NPack");
				String DPack = request.getParameter("DPack");
				int PPack = Integer.valueOf(request.getParameter("PPack"));

				int id=Factory.makeCookieGetter(request).getIdbyCookies();
				
				MySQLPackages db = new MySQLPackages();
				db.AddPackage(id, Npack, DPack, PPack);
				
				db.disconnect();
			};
			
			strategy.run();
			
			doGet(request, response);
		}
		

		//Si attiva quando si clicca su un elemento della tabella
		String sval = request.getParameter("SetValue");
		if ( sval != null) { 
			
			PackagesStrategy strategy = () -> {
				cache.setSelectedPackage(sval);
			};
			
			strategy.run();
			
		}
		
		String delpac = request.getParameter("DeletePackage");
		if ( delpac != null) {
			
			PackagesStrategy strategy = () -> {
				
				//Se c'è un pacchetto nella cache
				if ( cache.getSelectedPackage().equals("") == false ) {
					int id=Factory.makeCookieGetter(request).getIdbyCookies();
					
					MySQLPackages db = new MySQLPackages();
					db.deletePackage(id, cache.getSelectedPackage());
					db.disconnect();
				}

				cache.setSelectedPackage("");
			};
			
			strategy.run();
			
		}
		
		if ( request.getParameter("ConfirmEditPackage") != null ) {
			
			PackagesStrategy strategy = () -> {
				String selpac = cache.getSelectedPackage();
				if ( selpac.equals("") == false ) {
					MySQLPackages db = new MySQLPackages();
					
					int id=Factory.makeCookieGetter(request).getIdbyCookies();
					
					String Npack = request.getParameter("NPack");
					String DPack = request.getParameter("DPack");
					String temp = request.getParameter("PPack");
					int PPack = 0;
					if ( temp.equals("") == false ) {
						PPack = Integer.valueOf(temp);
					}
					
					Package newPac= new Package(Npack, DPack, PPack);
					db.editPackage(id, newPac, cache.getSelectedPackage() );
					db.disconnect();
				}
			};
			
			strategy.run();
			
			doGet(request,response);
		}
		
		*/
	}
	

}