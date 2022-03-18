package hoppin.employee;

import hoppin.util.QueryBuilder;

public class EmployeeQueryBuilder extends QueryBuilder {
	private static final String qistart = "( "; //Query Incognites Start
	private static final String qiend = " )"; //QUery Incognites End
	private static final String strep =" ?"; //String To Repeat 
	
	public EmployeeQueryBuilder() {
		super();
	}
	
	public String makeQuery(int size) {
		
		sb.append(qistart);
		for (int i=0; i<size; i++) {
			sb.append(strep);
			if ( i+1 < size)
				sb.append(", ");
		}
		
		sb.append(qiend);
		query = sb.toString();
		
		return this.toString();
	}

}
