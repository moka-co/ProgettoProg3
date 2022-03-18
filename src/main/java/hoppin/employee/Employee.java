package hoppin.employee;

/**
 * 
 * Rappresenta un impiegato o proprietario dell'Hotel
 * 
 *
 */

public class Employee {
	public Integer id;
	public String email;
	public String completeName;
	
	/**
	 * 
	 * @param id del dipendente
	 * @param email 
	 * @param name
	 */
	
	public Employee(int id, String email, String name) {
		super();
		this.id=id;
		this.email=email;
		this.completeName=name;
	}
	
	/**
	 * 
	 * @return un intero che rappresenta l'id
	 */
	
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return una stringa che rappresenta un email
	 */
	
	public String getEmail() {
		return email;
	}
	
	/**
	 * 
	 * @return una stringa che rappresenta nome e cognome del dipendente o propeitario
	 */
	
	public String getCompleteName() {
		return completeName;
	}
}
