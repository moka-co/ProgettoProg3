package hoppin.util;

public abstract class QueryBuilder {
	protected String query;
	protected StringBuilder sb = new StringBuilder();
	
	public QueryBuilder() {
		sb = new StringBuilder();
	}
	
	public String toString() {
		return query;
	};
	
}
