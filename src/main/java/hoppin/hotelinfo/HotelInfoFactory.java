package hoppin.hotelinfo;
import java.io.IOException;

import hoppin.util.factory.CookieFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

/**
 * 
 * Nasconde la costruzione di alcuni oggetti a {@link HotelInfoManagement} e li restituisce. <br>
 * Implementa {@link hoppin.util.factory.CookieFactory} del quale utilizza
 * {@link #makeCookieGetter(HttpServletRequest)} per ottenere l'id dell'utente autenticato.
 *
 */
public class HotelInfoFactory implements CookieFactory {
	public HotelInfoFactory() {
		super();
	}
	
	/**
	 * @param request cioè una richiesta http che contiene un cookie da cui si ricava l'id dell'utente autenticato.
	 * @return Restituisce una connessione al database
	 */
	public MySQLHotelInfo makeDatabaseConnect(HttpServletRequest request) {
		int i = this.makeCookieGetter(request).getIdbyCookies();
		MySQLHotelInfo db =  new MySQLHotelInfo(i);
		
		return db;
		
	}

	/**
	 * @see {@link hoppin.hotelinfo.HotelInfoBuilder}
	 * @param request  Una richiesta http che contiene un cookie da cui si ricava l'id dell'utente autenticato.
	 * @return HotelInfo
	 */
	public HotelInfo makeHotelInfo(HttpServletRequest request) {
		HotelInfoBuilder hib = new HotelInfoBuilder(request);
		int id = makeCookieGetter(request).getIdbyCookies();
		MySQLHotelInfo db = new MySQLHotelInfo(id);
		hib.name( db.getHotelNameById()  );
		db.disconnect();
		
		return hib.toHotelInfo();
	}
	
	/**
	 * 
	 * @return restituisce il nome che l'immagine dovrà avere
	 * @throws ServletException se non riesce a prendere l'immagine dalla richiesta
	 * @throws IOException se non riesce a prendere l'immagine dalla richiesta
	 */
	public String makeFilename(HttpServletRequest request, Part image) throws IOException, ServletException {
		MySQLHotelInfo db = this.makeDatabaseConnect(request);
		
		String fn = "";
		
		String ext = "";
		
		if ( image == null) {
			return fn;
		}
		
		String submittedFileName = image.getSubmittedFileName().toString();
		
		try {
			String [] splitd = submittedFileName.split("\\."); //Toglie l'estensione dell'immagine dal nome, esempio se "foto1.jpg", restituisce "foto1" e "jpg"
			ext = splitd[1]; 
		} catch (Exception e) { //nome del file non corretto
			System.out.println(e);
			db.disconnect();
			return fn;
		}
		
		int imageId = db.getMaxAndCountImageId()[0];
		
		if ( imageId != 0 ) {
			fn = db.getHotelNameById() + Integer.toString(imageId) + "." + ext  ;
		}
		
		db.disconnect();
		return fn;
	}
}
