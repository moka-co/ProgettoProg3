package hoppin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class ReservationManagement
 */

public class ReservationManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationManagement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
		
        CacheSingleton cache = CacheSingleton.getInstance();
		cache.setSingleValue(-1);
		
        ArrayList<Reservation> rlist = null;
        
        Cookie [] cookies = request.getCookies(); 
        int id = 0;
		for (Cookie aCookie : cookies) {
			String name = aCookie.getName();
			if (name.equals("id")){
				String value = aCookie.getValue();
				id = Integer.valueOf(value);
			}
		}
		
		//________________
		
		MySQLConnect db = new MySQLConnect();
		
		rlist = db.getReservationList(id);
		
		db.disconnect();
		HttpSession session=request.getSession();
		session.setAttribute("rlist",rlist);
		response.sendRedirect("/hoppin/ReservationManagement.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session=request.getSession();
		
		if ( request.getParameter("AddValue") != null) { //Si attiva quando si clicca su un elemento della tabella
			int value = Integer.valueOf(request.getParameter("AddValue"));
			CacheSingleton cache = CacheSingleton.getInstance();
			cache.setSingleValue(value);
			
		}
		
		if ( request.getParameter("ConfirmAddReservation") != null) { //Si attiva quando si preme il bottone che conferma l'inserimento dati
			String name = request.getParameter("name");
			String room = request.getParameter("room");
			String checkin = request.getParameter("checkin");
			String checkout = request.getParameter("checkout");
			String pckg = request.getParameter("package");
			if (pckg == null) {
				pckg = "Base";
			}
			
			if ( name == null || room == null || checkin == null || checkout == null) {
				System.out.println("Error");
				doGet(request,response);
				return;
			}
			

			MySQLConnect db = new MySQLConnect();
			boolean res = db.addReservation(name, room, checkin, checkout, pckg);
				
			if ( res == true) {
				session.setAttribute("ResultAddEmployee","ok");
					
			}else {
				session.setAttribute("ResultAddEmployee","no");
			}
			
			db.disconnect();
			//response.sendRedirect("/hoppin/EmployeeManagement.jsp");
			doGet(request, response);
		}
		
		if ( request.getParameter("DeleteReservation") != null) {
			MySQLConnect db = new MySQLConnect();
			CacheSingleton cache = CacheSingleton.getInstance();
			db.deleteReservation(cache.getSingleValue());
			cache.setSingleValue(-1);
		}
		
		if ( request.getParameter("ConfirmEditReservation") != null ) {
			MySQLConnect db = new MySQLConnect();
			CacheSingleton cache = CacheSingleton.getInstance();
			String name = request.getParameter("name");
			String room = request.getParameter("room");
			String checkin = request.getParameter("checkin");
			String checkout = request.getParameter("checkout");
			String pckg = request.getParameter("package");
			Reservation newReservation = new Reservation(name, cache.getSingleValue(), room, checkin, checkout, pckg);
			db.editReservation(newReservation);
			
			doGet(request,response);
		}
	}

}
