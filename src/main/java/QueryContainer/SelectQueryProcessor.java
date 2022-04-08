package QueryContainer;

import java.util.List;

import parser.RegexConstant;
import query.container.WhereCond;

public class SelectQueryProcessor {

	   private List columns;           //Keep it empty for all the columns

	    private String tableName;       //name of the table

	    private String database;        //name of the schema

	    private String columnInWhere;   //L.H.S of the where clause

	    private WhereCond whereCond;    //Condition of the where clause

	    private String factor;          //R.H.S in where clause
	    
	    private boolean allColumn=false;


	
		/*
		 * public void parseInsertQuery(String insertQuery) {
		 * 
		 * if(insertQuery.contains("*") && insertQuery.toLowerCase().contains("where"))
		 * { queryWithStarAndWhere(); }else if(!insertQuery.contains("*") &&
		 * insertQuery.toLowerCase().contains("where")) { queryWithOnlyWhere(); }else
		 * if(insertQuery.contains("*") && !insertQuery.toLowerCase().contains("where"))
		 * {
		 * 
		 * }else if(!insertQuery.contains("*") &&
		 * !insertQuery.toLowerCase().contains("where")) {
		 * 
		 * }
		 * 
		 * }
		 */	
	    
	    
}
