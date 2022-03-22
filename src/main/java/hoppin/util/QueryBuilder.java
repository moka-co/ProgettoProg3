package hoppin.util;

/**
 * 
 * Viene implementata da tutti i QueryBuilder nei vari sottosistemi.
 * Implementa un costruttore che costruisce un oggetto di tipo StringBuilder 
 * e un metodo toString() per restituire la stringa query
 *
 */
public abstract class QueryBuilder {
	protected String query;
	protected StringBuilder sb;
	
	public QueryBuilder() {
		sb = new StringBuilder();
	}
	
	public String toString() {
		return query;
	};
	
}
