package hoppin.packages;

public class PackagesCache {
	protected static PackagesCache id = null;
	private String SelectdPckg;
	
	public PackagesCache() {
		
	}
	
	
	public static PackagesCache getInstance() {
		if ( id == null )
			id = new PackagesCache();
		
		return id;
	}
	
	public void setSelectedPackage(String st) { this.SelectdPckg = st; }
	public String getSelectedPackage() { return this.SelectdPckg; }
}
