package parser;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import QueryContainer.CreateDatabaseProcessor;
import QueryContainer.CreateQueryProcessor;
import QueryContainer.SelectQueryProcessor;
import QueryContainer.UseDatabaseQueryProc;
import parser.exception.InvalidQueryException;
import query.container.CreateQuery;
import query.container.CreateSchema;
import query.container.SelectQuery;
import query.container.SqlType;
import query.manager.QueryHandler;
import query.manager.SchemaHandler;
import query.response.Response;
import query.response.ResponseType;

public class WriteQueries {
	public static String dbName = "";
	public static String query = "";
	QueryParserExecutor queryParserExecutor;
	public boolean dbCreated=false;
	
	  
    public WriteQueries() {
    	 queryParserExecutor=new    QueryParserExecutor();
    }

	void takeInput() {
		Scanner sc = new Scanner(System.in);

		/*
		 * if(dbName.equals("")){ System.out.println("Write the database name:"); dbName
		 * = sc.nextLine(); }
		 */
		System.out.println("Write the query()");
		query = sc.nextLine();
		System.out.println(query);
		try {
			
		
			
			if(dbName.equals("") && !query.toLowerCase().contains("use") && !query.toLowerCase().contains("create")) {
				System.out.println("Database is not selected");
			}
			else {
				boolean success=queryParserExecutor.processQuery(query);				
				if(success) {
				setDataAndExecuteQuery(query);
				}
			}
			
		
		} catch (InvalidQueryException e) {
			System.out.println(e.getErrorMsg());
		}

	}

	private Response setDataAndExecuteQuery(String query) {

		Response response = null;
		
		
		if (this.queryParserExecutor.isCreDbQuery(query)) {
			CreateDatabaseProcessor createDatabaseProc = this.queryParserExecutor.getCreateDatabaseProc();
			CreateSchema createSchema = new CreateSchema(createDatabaseProc.getDbName());
			response = SchemaHandler.executeSchemaCreateQuery(createSchema);
			printResponse(response.getResponseType().toString(), response.getDescription());
		} else if (query.toLowerCase().contains("create")) {
			CreateQueryProcessor createQueryProc = this.queryParserExecutor.getCreateQueryProcessor();
			CreateQuery createQuery = new CreateQuery(createQueryProc.getColumns(), createQueryProc.getDatatype(), null,
					createQueryProc.getTableName(), this.dbName, createQueryProc.getPrimaryKey(),
					createQueryProc.getForeginKey(), createQueryProc.getRefTable(), createQueryProc.getRefId());

			response = QueryHandler.executeQuery(createQuery, SqlType.CREATE);
		} else

		if (query.toLowerCase().contains("select")) {
			SelectQueryProcessor selectQueryProc = this.queryParserExecutor.getSelectQueryProcessor();
			SelectQuery selectQuery = new SelectQuery(selectQueryProc.getColumns(), selectQueryProc.getTableName(),
					this.dbName, selectQueryProc.getColumnInWhere(), selectQueryProc.getWhereCond(),
					selectQueryProc.getFactor(), selectQueryProc.isAllColumn());

			response = QueryHandler.executeQuery(selectQuery, SqlType.SELECT);
		} else
		if (query.toLowerCase().contains("use")) {
			UseDatabaseQueryProc useDatabaseQueryProc = this.queryParserExecutor.getUseDatabaseQueryProc();
			CreateSchema createSchema = new CreateSchema(useDatabaseQueryProc.getDbName());
			response = SchemaHandler.checkSchemaQuery(createSchema);
			if (response.getResponseType().toString().equals(ResponseType.SUCCESS.toString())) {
				this.dbName = useDatabaseQueryProc.getDbName();
				this.dbCreated = true;
			}
			printResponse(response.getResponseType().toString(), response.getDescription());

		}
	

		return response;
	}
	
	
	private void printResponse(String status, String desc) {
		System.out.println(status + ":" + desc);
	}

	public boolean manager() {
		takeInput();

		return true;
	}

}
