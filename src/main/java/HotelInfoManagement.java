

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

import hoppin.factory.HotelInfoFactory;
import hoppin.sql.MySQLHotelInfo;
import hoppin.HotelInfo;
import hoppin.builder.HotelInfoBuilder;

@MultipartConfig
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
		ArrayList<String> fns = db.getHotelImagesId(id);
		db.disconnect();
		
		HttpSession session=request.getSession();
		
		
		
		session.setAttribute("username", username);
		session.setAttribute("hotelInfo", hotel);
		session.setAttribute("fns", fns);
		
		response.sendRedirect("/hoppin/HomePage.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if ( request.getParameter("ConfirmEditInfo") != null) {
			MySQLHotelInfo db = new MySQLHotelInfo();
			HotelInfoFactory factory = new HotelInfoFactory();
			int id = factory.makeCookieGetter(request).getIdbyCookies();
			
			HotelInfoBuilder hib = new HotelInfoBuilder(request);
			String Hname = db.getHotelNameById(id);
			hib.name(Hname);
			
			HotelInfo hi = hib.toHotelInfo();
			
			db.editHotelInfo(hi);
		}

		if ( request.getParameter("AddImage") != null ) {
			MySQLHotelInfo db = new MySQLHotelInfo();
			
			String path = "";
			String ext = "";
			HotelInfoFactory factory = new HotelInfoFactory();
			int id = factory.makeCookieGetter(request).getIdbyCookies();
			
			/*
			String via = request.getParameter("addressVia");
			String city = request.getParameter("addressCity");
			String postcode = request.getParameter("addressPostcode");
			String description = request.getParameter("description");
			*/
			Part image = request.getPart("image");
			String submittedFileName = image.getSubmittedFileName().toString();
			
			try {
				String [] splitd = submittedFileName.split("\\.");
				ext = splitd[1];
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Bad file");
				return;
			}
			
			String pathToConfig = "/home/kurush/prog/eclipse-workspace/hoppin/";
			
			try (InputStream config = new FileInputStream(pathToConfig + "config.properties"); ){
				Properties prop = new Properties();
				prop.load(config);
				
				path = prop.getProperty("uploads.path");
		        
		        if ( path == null ) {
		        	System.out.println("Configuration file isn't correct");
		        	throw new IOException();
		        }
		        
				
			} catch (IOException e) { //Errore se non e' stato possibile aprire o leggere dal file di configurazione
				System.out.println("Error while getting config file, errore: ");
				System.out.println(e);
			}
			
			
			if ( Files.isDirectory(Paths.get(path)) == false  ) { //Se la directory non esiste, creala
				new File(path).mkdir();
			}
			
			
			int imageId = db.getMaxImageId(id);
			if ( imageId != 0 ) {
				String fileName = db.getHotelNameById(id) + Integer.toString(imageId) + "." + ext  ;
			
			
				try (InputStream input = image.getInputStream()) {
					Files.copy(input, Paths.get(path, fileName));
				}
			
				db.uploadFileName(id, fileName);
				db.disconnect();
			}
		}

		response.sendRedirect("/hoppin/HotelInfoManagement");

	}
}
