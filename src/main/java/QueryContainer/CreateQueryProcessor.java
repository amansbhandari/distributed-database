package QueryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.QueryParser;
import parser.RegexConstant;

public class CreateQueryProcessor {

	private List<String> columns; // Columns to query

	private String tableName; // name of the table

	private String database; // name of the schema

	private List<String> datatype;
	
	private String primaryKey;
	
	private String refTable;
	
	private String refId;
	
	

	public CreateQueryProcessor() {
		this.columns = new ArrayList<String>();
		this.datatype = new ArrayList<String>();

	}

	public void parseCreateQuery(String createQuery) {

		parser1(RegexConstant.GET_COLUMNS_REGEX, createQuery);
		parser2(RegexConstant.CREATE_TABLE_REGEX, createQuery);

	}

	private void parser1(String regx, String query) {

		System.out.println(regx);
		final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);

		while (matcher.find()) {
			// System.out.println("Full match: " + matcher.group(0));
			String temp = matcher.group(1).substring(1,matcher.group(1).length());

			String metadata[] = temp.split(",");

			for (String data : metadata) {

				String columnData[] = data.split("\s+");
				this.columns.add(columnData[0]);
				this.datatype.add(columnData[1]);

				if (columnData.length > 2) {
					if(data.toLowerCase().contains("primary")) {
						this.primaryKey=columnData[0];
					}
					
					if(data.toLowerCase().contains("foreign")) {
						
						String refData=columnData[5];
						
						this.refTable=refData.substring(0, refData.indexOf("("));
						this.refId=refData.substring(refData.indexOf("(")+1, refData.indexOf(")"));
						
					}
					
				}

			}

			/*
			 * for (int i = 1; i <= matcher.groupCount(); i++) { System.out.println("Group "
			 * + i + ": " + matcher.group(i)); }
			 */
		}

	}

	private void parser2(String regx, String query) {

		System.out.println(regx);
		final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);

		while (matcher.find()) {

			if (matcher.group(4).contains(".")) {
				database = matcher.group(4).substring(0, matcher.group(4).length());
			} else {
				tableName = matcher.group(4);
			}

		}

	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public List<String> getDatatype() {
		return datatype;
	}

	public void setDatatype(List<String> datatype) {
		this.datatype = datatype;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getRefTable() {
		return refTable;
	}

	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	
}
