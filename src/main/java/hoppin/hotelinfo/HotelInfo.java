package hoppin.hotelinfo;

/**
 * 
 * Contiene le informazioni di un Hotel: nome, indirizzo, stelle e descrizione
 * e implementa i relativi metodi {@code set} e {@code get} 
 * per manipolare e ottenere queste informazioni.
 *
 */
public class HotelInfo {
	private String name;
	
	private String via;
	private String city;
	private String postcode;
	
	private int stars;
	
	private String description;
	
	public HotelInfo() {
		super();
	}
	
	/**
	 * 
	 * @return il nome dell'Hotel
	 */
	public String getName() { return name; }
	/**
	 * 
	 * @return la via dove si trova l'Hotel
	 */
	public String getVia() { return via; }
	/**
	 * 
	 * @return la città dove si trova l'Hotel
	 */
	public String getCity() { return city; }
	/**
	 * 
	 * @return Il codice postale della città in cui si trova l'Hotel
	 */
	public String getPostcode() { return postcode; }
	/**
	 * 
	 * @return Il numero di stelle dell'Hotel
	 */
	public int getStars() { return stars; }
	/**
	 * 
	 * @return La descrizione dell'Hotel
	 */
	public String getDescription() { return description; }
	
	public void setName(String name) { this.name = name; }
	public void setVia(String via) { this.via = via; }
	public void setCity(String city) { this.city = city; }
	public void setPostcode(String postcode) { this.postcode = postcode; }
	/**
	 * 
	 * @param s un intero che rappresenta il numero di stelle dell'Hotel
	 */
	public void setStars(int s) { stars=s; }
	/**
	 * 
	 * @param des una stringa che rappresenta un testo che descrive l'Hotel
	 */
	public void setDescription(String des) { description = des; }
	
}
