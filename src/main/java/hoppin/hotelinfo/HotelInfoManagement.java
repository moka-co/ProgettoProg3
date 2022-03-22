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

/**
 * 
 * Gestisce le richeiste HTTP, GET e POST, leggendo o impostando i parametri HTTP di conseguenza,
 * implementando le funzionalità del sottosistema HotelInfoManagement 
 *
 * Per l'annotazione {@link jakarta.servlet.annotation.MultipartConfig} 
 * vedi <a>https://github.com/jakartaee/servlet/blob/master/api/src/main/java/jakarta/servlet/annotation/MultipartConfig.java</a>
 * E' necessaria per far capire al servlet che si può aspettare richieste HTTP con parametri di tipo multipart/form-data, come avviene 
 * in {@link #doPost(HttpServletRequest, HttpServletResponse)} nel costrutto switch, nel caso "AddImage".
 * 
 *
 */
@MultipartConfig 
public class HotelInfoManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String path = ""; 

    public HotelInfoManagement() {
        super();
        path = PropertyFactory.getInstance().getPropertyMap().get("path"); //Percorso dove caricare le immagini
    }

    /**
     * Prende alcune informazioni dal database come il nome dell'utente selezionato e le informazioni dell'Hotel,
     * li inserisce come attributi della risposta HTTP e infine rimanda a <mono>HomePage.jsp</mono>
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MySQLHotelInfo db = new MySQLHotelInfo();
		HotelInfoFactory factory = new HotelInfoFactory();
		
		int id = factory.makeCookieGetter(request).getIdbyCookies();
		
		String username = db.getNamebyId(id);
		HotelInfo hotel = db.getHotelInfo(id);
		
		//Gli id delle immagini sono utilizzati per costruire il nome completo delle immagini
		ArrayList<String> idImmagini = db.getHotelImagesId(id);
		
		db.disconnect();
		
		
		HttpSession session=request.getSession();
		
		session.setAttribute("username", username);
		session.setAttribute("hotelInfo", hotel);
		session.setAttribute("fns", idImmagini);
		
		response.sendRedirect("/hoppin/HomePage.jsp");
	}

	/**
	 * @param request 
	 * @param response
	 * 
	 * Riceve degli attributi da @param request e in base a questi
	 * implementa in modo dinamico  {@link hoppin.hotelinfo.HIStrategy#run()} e lo esegue.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MySQLHotelInfo db = new MySQLHotelInfo();
		HotelInfoFactory factory = new HotelInfoFactory();
		HIStrategy strategy = () -> { }; //Inizializzazione della funzione lambda strategy
		
		//lista di keywords valide che corrispondono ad un azione da effettuare:
		List<String> words = Arrays.asList("ConfirmEditInfo", "DeletePhoto", "AddImage" );
		
		String switched = "";
		for (String str : words) {
			if ( request.getParameter(str) != null) {
				switched = str;
			}
		}
		
		 /**
         * Abbiamo due variabili: param e switched.
         * switched è una delle keywords valide che sono passate in input, oppure è una stringa vuota.
         * 
         * switched viene utilizzato nel costrutto 
         * @{code switch(switched){
         * case "ConfirmEditInfo" : { //... };
         * }
         *  // ...
         * } 
         * 
         * param è il parametro della richiesta HTTP associato alla keyword passata.
         * 
         * 
         */
        
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
				
				Part image = request.getPart("image"); //Prendi l'immagine inviata
				String submittedFileName = image.getSubmittedFileName().toString();
				
				try {
					String [] splitd = submittedFileName.split("\\."); //Toglie l'estensione dell'immagine dal nome, esempio se "foto1.jpg", restituisce "foto1" e "jpg"
					ext = splitd[1]; //Se la foto si chiamava "foto1.jpg" , in ext viene salvato "jpg"
				} catch (Exception e) { //nome del file non corretto
					System.out.println(e);
					return;
				}
				
				
				if ( Files.isDirectory(Paths.get(path)) == false  ) { //Se la directory non esiste, creala
					new File(path).mkdir();
				}
				
				
				int imageId = db.getMaxAndCountImageId(id)[0]; //prendi l'id da assegnare all'immagine
				if ( imageId != 0 ) {
					String fileName = db.getHotelNameById(id) + Integer.toString(imageId) + "." + ext  ;
				
				
					try (InputStream input = image.getInputStream()) {
						Files.copy(input, Paths.get(path, fileName)); //Carica l'immagine su file system
					}
				
					db.uploadFileName(id, fileName); //Carica il riferimento all'immagine nel database
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
