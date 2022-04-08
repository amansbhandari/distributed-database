package parser;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import QueryContainer.CreateQueryProcessor;
import QueryContainer.SelectQueryProcessor;
import parser.exception.InvalidQueryException;
import query.container.CreateQuery;
import query.container.SelectQuery;
import query.container.SqlType;
import query.manager.QueryHandler;
import query.response.Response;

public class WriteQueries {
	public static String dbName = "";
	public static String query = "";
	QueryParserExecutor queryParserExecutor;

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
			queryParserExecutor.processQuery(query);
			setDataAndExecuteQuery(query);
		} catch (InvalidQueryException e) {
			System.out.println(e.getErrorMsg());
		}

	}

	private Response setDataAndExecuteQuery(String query) {

		Response response = null;

		if (query.toLowerCase().contains("create")) {
			CreateQueryProcessor createQueryProc = this.queryParserExecutor.getCreateQueryProcessor();
			CreateQuery createQuery = new CreateQuery(createQueryProc.getColumns(), createQueryProc.getDatatype(), null,
					createQueryProc.getTableName(), createQueryProc.getDatabase(), createQueryProc.getPrimaryKey(),
					createQueryProc.getForeginKey(), createQueryProc.getRefTable(), createQueryProc.getRefId());

			response = QueryHandler.executeQuery(createQuery, SqlType.CREATE);
		}

		if (query.toLowerCase().contains("select")) {
			SelectQueryProcessor selectQueryProc = this.queryParserExecutor.getSelectQueryProcessor();
			SelectQuery selectQuery = new SelectQuery(selectQueryProc.getColumns(), selectQueryProc.getTableName(),
					selectQueryProc.getDatabase(), selectQueryProc.getColumnInWhere(), selectQueryProc.getWhereCond(),
					selectQueryProc.getFactor(), selectQueryProc.isAllColumn());

			response = QueryHandler.executeQuery(selectQuery, SqlType.SELECT);
		}

		return response;
	}

	public boolean manager() {
		takeInput();

		return true;
	}

}
