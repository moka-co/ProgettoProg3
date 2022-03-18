package hoppin.hotelinfo;

import hoppin.util.factory.PropertyFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface HIStrategy {
	void run() throws IOException, ServletException;
}


@MultipartConfig
public class HotelInfoManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static String pathToConfig = "/home/kurush/prog/eclipse-workspace/hoppin/";
	private String path = "";

    public HotelInfoManagement() {
        super();
        path = PropertyFactory.getInstance().getPropertyMap().get("path");
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
		MySQLHotelInfo db = new MySQLHotelInfo();
		HotelInfoFactory factory = new HotelInfoFactory();
		
		List<String> words = Arrays.asList("ConfirmEditInfo", "DeletePhoto", "AddImage" );
		
		String switched = "";
		for (String str : words) {
			if ( request.getParameter(str) != null) {
				switched = str;
			}
		}
		
		HIStrategy strategy = () -> { };
        
        switch ( switched ) {
        case "ConfirmEditInfo" : {
        	HotelInfo hi = factory.makeHotelInfo(request);
			
			db.editHotelInfo(hi);
			break;
        }
        
        case "DeletePhoto" : {
        	strategy = () -> {
				int id = factory.makeCookieGetter(request).getIdbyCookies();
				String ImgId = request.getParameter("DeletePhoto");
				db.deleteImg(id, ImgId);
				
				Files.deleteIfExists( Paths.get(path, ImgId)  );

			};
        	break;
        }
        
        case "AddImage" : {
        	strategy = () -> {
				
				String ext = "";
				int id = factory.makeCookieGetter(request).getIdbyCookies();
				
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
				
				
				if ( Files.isDirectory(Paths.get(path)) == false  ) { //Se la directory non esiste, creala
					new File(path).mkdir();
				}
				
				
				int imageId = db.getMaxAndCountImageId(id)[0];
				if ( imageId != 0 ) {
					String fileName = db.getHotelNameById(id) + Integer.toString(imageId) + "." + ext  ;
				
				
					try (InputStream input = image.getInputStream()) {
						Files.copy(input, Paths.get(path, fileName));
					}
				
					db.uploadFileName(id, fileName);
				}
        	
        };
        	break;
        }
        
        default:
        	doGet(request, response);
        	break;
        	
        }
        
        strategy.run();
        db.disconnect();  
        doGet(request, response);
        
       

	}
}
