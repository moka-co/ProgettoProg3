package hoppin.packages;

/**
 * 
 * Cache implementata come singleton che mantiene una stringa SelectedPckg
 * che contiene un pacchetto selezionato.
 *
 * @see hoppin.packages.PackagesManagement
 */
public class PackagesCache {
	protected static PackagesCache id = null;
	private String SelectdPckg;
	
	public PackagesCache() {
		super();
	}
	
	
	public static PackagesCache getInstance() {
		if ( id == null )
			id = new PackagesCache();
		
		return id;
	}
	
	public void setSelectedPackage(String st) { this.SelectdPckg = st; }
	public String getSelectedPackage() { return this.SelectdPckg; }
}
