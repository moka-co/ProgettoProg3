import hoppin.*;
import hoppin.builder.ReservationBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import hoppin.factory.AbstractFactory;
import hoppin.factory.ReservationFactory;


public class ReservationManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ReservationManagement() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
		
        CacheSingleton cache = CacheSingleton.getInstance();
		cache.setSingleValue(-1);
		
        ArrayList<Reservation> rlist = null;
        
        AbstractFactory factory = new ReservationFactory();
        int id = ((ReservationFactory) factory).makeCookieGetter(request).getIdbyCookies();
		
		//________________
		
		MySQLConnect db = new MySQLConnect();
		
		rlist = db.getReservationList(id);
		
		db.disconnect();
		HttpSession session=request.getSession();
		session.setAttribute("rlist",rlist);
		response.sendRedirect("/hoppin/ReservationManagement.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session=request.getSession();
		CacheSingleton cache = CacheSingleton.getInstance();
		
		if ( request.getParameter("AddValue") != null) { //Si attiva quando si clicca su un elemento della tabella
			int value = Integer.valueOf(request.getParameter("AddValue"));
			cache.setSingleValue(value);
			
		}
		
		if ( request.getParameter("ConfirmAddReservation") != null) { //Si attiva quando si preme il bottone che conferma l'inserimento dati
			ReservationBuilder rb = new ReservationBuilder(request);
			Reservation res = rb.getReservation();
			
			/*
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
			} */
			
			AbstractFactory factory = new ReservationFactory();
		    int id = ((ReservationFactory) factory).makeCookieGetter(request).getIdbyCookies();
			MySQLConnect db = new MySQLConnect();
			boolean result = db.addReservation(id, res);
			db.disconnect();
				
			if ( result == true) {
				session.setAttribute("ResultAddEmployee","ok");
					
			}else {
				session.setAttribute("ResultAddEmployee","no");
			}
			

			//response.sendRedirect("/hoppin/EmployeeManagement.jsp");
			doGet(request, response);
		}
		
		if ( request.getParameter("DeleteReservation") != null) {
			MySQLConnect db = new MySQLConnect();
			db.deleteReservation(cache.getSingleValue());
			cache.setSingleValue(-1);
		}
		
		if ( request.getParameter("ConfirmEditReservation") != null ) {
			MySQLConnect db = new MySQLConnect();
			
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
