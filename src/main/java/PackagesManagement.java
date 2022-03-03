
import hoppin.sql.MySQLPackages;
import hoppin.Package;
import hoppin.factory.PackagesFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class PackagesManagement
 */
public class PackagesManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PackagesManagement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MySQLPackages db = new MySQLPackages();
		PackagesFactory Factory = new PackagesFactory();
		int i=Factory.makeCookieGetter(request).getIdbyCookies();
	
		HttpSession session = request.getSession();
		
		ArrayList<Package> PackageList = db.GetPackageList(i);
		
		session.setAttribute("PackageList", PackageList);
		
		db.disconnect();
		
		response.sendRedirect("/hoppin/PackagesManagement.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String cap = request.getParameter("ConfirmAddPackage");
		if (cap != null) {
			String Npack = request.getParameter("NPack");
			String DPack = request.getParameter("DPack");
			int PPack = Integer.valueOf(request.getParameter("PPack"));
			PackagesFactory Factory = new PackagesFactory();
			int i=Factory.makeCookieGetter(request).getIdbyCookies();
			
			MySQLPackages db = new MySQLPackages();
			db.AddPackage(i, Npack, DPack, PPack);
			
			db.disconnect();
			
			doGet(request, response);
			}
	}
	
	
	

}
