package hoppin.packages;

/**
 * Rappresenta un pacchetto di servizi aggiuntivi per un Hotel <br>
 * Ha come attributi nome, descrizione, nome dell'Hotel e prezzo, 
 * implementa i relativi metodi per manipolare e ottenere questi attributi.
 *
 */
public class Package {

	public String name;
	public String description;
	public String Hotel;
	public int price;
	
	/**
	 * La differenza tra i due costruttori sta nei parametri, in questo non c'è Hotel in input
	 * 
	 */
	public Package(String name, String description, int price) {
		super();
		this.name=name;
		this.description=description;
		this.price=price;
	}
	
	public Package(String name, String description, String Hotel, int price) {
		super();
		this.name=name;
		this.description=description;
		this.Hotel=Hotel;
		this.price=price;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getHotel() {
		return Hotel;
	}
	
	public int getPrice() {
		return price;
	}
	
}