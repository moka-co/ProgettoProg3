package hoppin.finance;

/**
 * 
 * Rappresenta un periodo stagionale, costituito da Hotel, periodo di inizio, periodo di fine, incremento percentuale e 
 * tipo di stagione, ad esempio bassa, media, alta stagione.
 * Sono implementati i relativi metodi per ottenere questi attributi e il costruttore per impostarli
 *
 */
public class Season {
	private String hotel;
	private String type;
	private String start;
	private String end;
	private int percIncrease; //Percentual Increase

	/**
	 * 
	 * @param Hotel nome dell'Hotel
	 * @param Type tipo di stagione esempio {@code "Alta"} , {@code "Media"} etc 
	 * @param Start periodo di inizio stagione, deve essere una stringa del formato dd-mm-yyyy o yyyy-mm-dd
	 * @param End periodo di fine stagione, deve essere una stringa del formato dd-mm-yyyy o yyyy-mm-dd
	 * @param p incremento percentuale esempio 10, cioè 10 percento.
	 */
	public Season(String Hotel, String Type, String Start, String End, int p) {
		this.hotel=Hotel;
		this.type=Type;
		this.start=Start;
		this.end=End;
		this.percIncrease=p;
		
	}
	
	public String getHotel() { return hotel; }
	public String getType() { return type; }
	public String getStart() { return start; }
	public String getEnd() { return end; }
	public int getPercIncrease() { return percIncrease; }
}
