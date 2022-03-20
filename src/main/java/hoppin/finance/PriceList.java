package hoppin.finance;

/**
 * Rappresenta il prezziario delle stanze dell'Hotel, composto da due attributi: <br>
 * roomType è il tipo di stanza, ad esempio Matrimoniale <br>
 * price è prezzo del tipo di stanza, ad esempio 40 euro <br>
 * Sono implementati anche i relativi metodi {@link #getPrice()} e {@link #getRoomType()} per 
 * ottenere gli attributi memorizzati e {@link #PriceList(String, int)} il costruttore per assegnarli.
 *
 */
public class PriceList {
	public String roomType;
	public int price;

	/**
	 * Istanzia un nuovo PriceList e setta i due attributi
	 * @param type tipo di stanza
	 * @param price prezzo della stanza
	 */
	public PriceList(String type, int price) {
		super();
		this.roomType=type;
		this.price=price;
		
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getRoomType() {
		return roomType;
	}

}
