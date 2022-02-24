
import hoppin.MySQLConnect;
import hoppin.Package;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
		MySQLConnect db = new MySQLConnect();
		int i=0;
		Cookie [] cookies = request.getCookies(); 

		//Cookie sempre diverso da null, altrimenti AuthFilter se ne accorge e rimanda ad Authentication

		for (Cookie aCookie : cookies) {
			String name = aCookie.getName();
			if (name.equals("id")){
				String value = aCookie.getValue();
				i = Integer.valueOf(value);
			}
		}
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
			
			int i=0;
			Cookie [] cookies = request.getCookies(); 

			//Cookie sempre diverso da null, altrimenti AuthFilter se ne accorge e rimanda ad Authentication

			for (Cookie aCookie : cookies) {
				String name = aCookie.getName();
				if (name.equals("id")){
					String value = aCookie.getValue();
					i = Integer.valueOf(value);
				}
			}
			
			MySQLConnect db = new MySQLConnect();
			db.AddPackage(i, Npack, DPack, PPack);
			
			doGet(request, response);
			}
	}
	
	
	

}
