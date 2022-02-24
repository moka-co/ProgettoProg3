

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import hoppin.MySQLConnect;
import hoppin.PriceList;

/**
 * Servlet implementation class FinanceManagement
 */
public class FinanceManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinanceManagement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Cookie [] cookies = request.getCookies(); 

		//Cookie sempre diverso da null, altrimenti AuthFilter se ne accorge e rimanda ad Authentication
		int id = -1;
		for (Cookie aCookie : cookies) {
			String name = aCookie.getName();
			if (name.equals("id")){
				String value = aCookie.getValue();
				id = Integer.valueOf(value);
			}
		}
		MySQLConnect db = new MySQLConnect();
		ArrayList<Integer> params = db.getTotProfit(id); //params = parametri
		ArrayList<PriceList> pricelist = db.getPriceList(id);
		db.disconnect();
		
		HttpSession session=request.getSession();
		session.setAttribute("sumAllPrice", params.get(0));
		session.setAttribute("allReservation", params.get(1));
		session.setAttribute("pricelist", pricelist);
		
		db.disconnect();
		response.sendRedirect("/hoppin/FinanceManagement.jsp");
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String arti= request.getParameter("AddRoomTypeId"); //arti = Add Room Type Id
		if ( arti != null) {
			
		}
		
		//doGet(request, response);
	}

}
