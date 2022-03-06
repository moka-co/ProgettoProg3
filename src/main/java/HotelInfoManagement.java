

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import hoppin.factory.HotelInfoFactory;
import hoppin.sql.MySQLHotelInfo;
import hoppin.HotelInfo;


public class HotelInfoManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HotelInfoManagement() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MySQLHotelInfo db = new MySQLHotelInfo();
		HotelInfoFactory factory = new HotelInfoFactory();
		int id = factory.makeCookieGetter(request).getIdbyCookies();
		String username = db.getNamebyId(id);
		HotelInfo hotel = db.getHotelInfo(id);


		db.disconnect();
		
		HttpSession session=request.getSession();
		
		session.setAttribute("username", username);
		session.setAttribute("hotelInfo", hotel);
		
		response.sendRedirect("/hoppin/HomePage.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
