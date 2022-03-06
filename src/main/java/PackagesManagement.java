

import hoppin.sql.MySQLPackages;

import hoppin.CacheSingleton;
import hoppin.Package;

import hoppin.factory.PackagesFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

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
		CacheSingleton cache = CacheSingleton.getInstance();
		PackagesFactory Factory = new PackagesFactory();

		String cap = request.getParameter("ConfirmAddPackage");
		if (cap != null) {
			String Npack = request.getParameter("NPack");
			String DPack = request.getParameter("DPack");
			int PPack = Integer.valueOf(request.getParameter("PPack"));

			int i=Factory.makeCookieGetter(request).getIdbyCookies();
			
			MySQLPackages db = new MySQLPackages();
			db.AddPackage(i, Npack, DPack, PPack);
			
			db.disconnect();
			
			doGet(request, response);
			}
		

		if ( request.getParameter("SetValue") != null) { //Si attiva quando si clicca su un elemento della tabella

			String value = request.getParameter("SetValue");
			cache.setSelectedPackage(value);
			System.out.println("Settato pacchetto" + cache.getSelectedPackage());
			
		}
		

		if ( request.getParameter("DeletePackage") != null) {

			int i=Factory.makeCookieGetter(request).getIdbyCookies();
			
			MySQLPackages db = new MySQLPackages();
			db.deletePackage(i, cache.getSelectedPackage());
			db.disconnect();
			cache.setSelectedPackage("");
		}
		
		if ( request.getParameter("ConfirmEditPackage") != null ) {
			MySQLPackages db = new MySQLPackages();
			
			int i=Factory.makeCookieGetter(request).getIdbyCookies();
			
			String Npack = request.getParameter("NPack");
			String DPack = request.getParameter("DPack");
			String temp = request.getParameter("PPack");
			int PPack = 0;
			if ( ! temp.equals("") ) {
				PPack = Integer.valueOf(temp);
			}

			Package newPac= new Package(Npack, DPack, null, PPack); //Sto null è pericoloso e cozza secondo me
			//Andrebbe semplicemente fatto tramite un costruttore es PackageBuilder
			
			db.editPackage(i, newPac, cache.getSelectedPackage() );
			db.disconnect();
			
			doGet(request,response);
		}
	}
	

}