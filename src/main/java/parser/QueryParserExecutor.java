package parser;

import java.util.ArrayList;
import java.util.List;

import QueryContainer.CreateQueryProcessor;
import parser.exception.InvalidQueryException;

public class QueryParserExecutor {

	private QueryParser queryParser;
	
	private CreateQueryProcessor createQueryProcessor;

	private String queryType;

	private String databaseName;

	private String tableName;

	private List<String> columnList;

	private List<String> datatype;

	public QueryParserExecutor() {
		this.queryParser = new QueryParser();
		this.columnList = new ArrayList<String>();
		this.datatype = new ArrayList<String>();
	}

	public boolean processQuery(String query) throws InvalidQueryException {

		boolean isQueryProcessed = false;
		
		  isQueryProcessed = this.queryParser.parseQuery(query);
		  
		  if (!isQueryProcessed) { throw new
		  InvalidQueryException(this.queryParser.getErrorMessage()); }
		 

		if (query.contains("CREATE")) {
			this.createQueryProcessor=new CreateQueryProcessor();
			createQueryProcessor.parseCreateQuery(query);
			printCreateQuery(createQueryProcessor);
		}

		return isQueryProcessed;
	}

	private void printCreateQuery(CreateQueryProcessor createQueryProcessor) {
		System.out.println(createQueryProcessor.getDatabase());
		System.out.println(createQueryProcessor.getTableName());
		System.out.println(createQueryProcessor.getColumns());
		System.out.println(createQueryProcessor.getDatatype());
		System.out.println(createQueryProcessor.getPrimaryKey());
		System.out.println(createQueryProcessor.getRefId());
		System.out.println(createQueryProcessor.getRefTable());
	}

}
