package parser;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import QueryContainer.*;
import parser.exception.InvalidQueryException;
import query.container.*;
import query.container.SelectQuery;
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
		}
		else if (query.toLowerCase().contains("create")) {
			CreateQueryProcessor createQueryProc = this.queryParserExecutor.getCreateQueryProcessor();
			ArrayList<String> clmnNull = new ArrayList<>();
			for(int kk=0; kk<=createQueryProc.getColumns().size();kk++){
				             clmnNull.add("false");
			}
			CreateQuery createQuery = new CreateQuery(createQueryProc.getColumns(), createQueryProc.getDatatype(), clmnNull,
					createQueryProc.getTableName(), this.dbName, createQueryProc.getPrimaryKey(),
					createQueryProc.getForeginKey(), createQueryProc.getRefTable(), createQueryProc.getRefId());

			response = QueryHandler.executeQuery(createQuery, SqlType.CREATE);
			printResponse(response.getResponseType().toString(), response.getDescription());
		}

		else if (query.toLowerCase().contains("select")) {
			SelectQueryProcessor selectQueryProc = this.queryParserExecutor.getSelectQueryProcessor();
			SelectQuery selectQuery = new SelectQuery(selectQueryProc.getColumns(), selectQueryProc.getTableName(),
					this.dbName, selectQueryProc.getColumnInWhere(), selectQueryProc.getWhereCond(),
					selectQueryProc.getFactor(), selectQueryProc.isAllColumn());

			response = QueryHandler.executeQuery(selectQuery, SqlType.SELECT);
			printResponse(response.getResponseType().toString(), response.getDescription());
		}
		else if (query.toLowerCase().contains("insert")) {
			InsertQueryProcessor insertQueryProc = this.queryParserExecutor.getInsertQueryProcessor();
			InsertQuery insertQuery = new InsertQuery(insertQueryProc.getColumns(),insertQueryProc.getTableName(),this.dbName,insertQueryProc.getValues());

			response = QueryHandler.executeQuery(insertQuery, SqlType.INSERT);
			printResponse(response.getResponseType().toString(), response.getDescription());
		}
		else if (query.toLowerCase().contains("delete")) {
			DeleteQueryProcessor delQueryProc = this.queryParserExecutor.getDeleteQueryProcessor();

			DeleteQuery delQuery = new DeleteQuery(delQueryProc.getTableName(),this.dbName,delQueryProc.getColumns(),WhereCond.EQUALS,delQueryProc.getValue());

			response = QueryHandler.executeQuery(delQuery, SqlType.DELETE);
			printResponse(response.getResponseType().toString(), response.getDescription());
		}
		else if (query.toLowerCase().contains("use")) {
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
