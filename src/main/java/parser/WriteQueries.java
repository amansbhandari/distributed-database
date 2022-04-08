package parser;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import QueryContainer.CreateQueryProcessor;
import QueryContainer.InsertQueryProcessor;
import parser.exception.InvalidQueryException;
import query.container.CreateQuery;
import query.container.SqlType;
import query.manager.QueryHandler;
import query.response.Response;

public class WriteQueries {
    public static String dbName="";
    public static String query="";
    QueryParserExecutor queryParserExecutor;
  
    
    
    public WriteQueries() {
    	 queryParserExecutor=new    QueryParserExecutor();
    }
    
    void takeInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Write the query()");
        query= sc.nextLine();
        System.out.println(query);
   
        
        try {
			queryParserExecutor.processQuery(query);
			setDataAndExecuteQuery(query);
		} catch (InvalidQueryException e) {
			System.out.println(e.getErrorMsg());
		}
        
        

    }
	/*
	 * void parse(){ String[] type = query.split(" ");
	 * if(type[0].equalsIgnoreCase("USE")){ Boolean chk=
	 * parsers("(?i)(use)\\s+(\\w+)\\s*;"); if(!chk){
	 * System.out.println("Something went wrong"); } }
	 * if(type[0].equalsIgnoreCase("DELETE")){ Boolean chk=
	 * parsers("(?i)(DELETE FROM)\\s+(\\w+)(\\s+(where)?\\s+(.*)?)?;"); if(!chk){
	 * System.out.println("Something went wrong"); } }
	 * if(type[0].equalsIgnoreCase("UPDATE")){ Boolean chk= parsers(
	 * "(?i)(update)\\s+(\\w+)\\s+(?i)(set)\\s+(\\w+.*)\\s+(?i)(where)\\s+(\\w+.*);"
	 * ); if(!chk){ System.out.println("Something went wrong"); } }
	 * if(type[0].equalsIgnoreCase("INSERT")){ String r1="(?<=\\()(.*?)(?=\\))";
	 * String
	 * r2="(?i)(INSERT INTO)\\s+(\\S+)\\s+\\((.*?)\\)\\s+(VALUES).*\\s+\\((.*?)\\);"
	 * ; String r3="(?i)(INSERT INTO)\\s+(\\S+).*\\s+(VALUES)\\s+\\((.*?)\\);";
	 * Boolean chk= insertParsers(r1,r2,r3); if(!chk){
	 * System.out.println("Something went wrong"); } }
	 * if(type[0].equalsIgnoreCase("CREATE")){ String r1=
	 * "((create)\\s+(table)\\s+(\\w+.)?(\\w+)\\s*)([(](\\s*\\w+\\s+(int|varchar)(\\s+primary\\s+key|foreign\\s+key\\s+references\\s+\\w+[(]\\w+[)])?\\s*,?)+\\s*[)];)";
	 * String r2="[(]\\s*[(\\w+\\s+(INT|varchar|float|boolean),[)]]+";
	 * 
	 * Boolean chk= parsers(r1); Boolean chk1= parsers(r2);
	 * 
	 * if(!chk || !chk1){ System.out.println("Something went wrong"); } } }
	 */



    private Response setDataAndExecuteQuery(String query) {
    	
    	Response response=null;
    	
    	if(query.toLowerCase().contains("create")) {
    		CreateQueryProcessor createQueryProc=this.queryParserExecutor.getCreateQueryProcessor();
    		CreateQuery createQuery=new CreateQuery(createQueryProc.getColumns(),createQueryProc.getDatatype(),null,createQueryProc.getTableName(),createQueryProc.getDatabase(),
    				createQueryProc.getDatabase(),createQueryProc.getForeginKey(),createQueryProc.getRefTable(),createQueryProc.getRefId());
    		
    		response=QueryHandler.executeQuery(createQuery, SqlType.CREATE);
    	}
    	
	
    	return response;
	}

	boolean insertParsers(String regx,String regx1,String regx2){

        boolean chk=false;
        int counter=0;
        System.out.println(regx);
        final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(query);

     
        
        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));

            for (int i = 1; i <= matcher.groupCount(); i++) {
                counter+=1;
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
            chk=true;
        }
        if (counter==1){
            Boolean chk1= parsers("(?i)(INSERT INTO)\\s+(\\S+).*\\s+(VALUES)\\s+\\((.*?)\\);");
            if(!chk1){
                System.out.println("Something went wrong");
            }
        }
        if (counter==2){
            Boolean chk1= parsers("(?i)(INSERT INTO)\\s+(\\S+)\\s+\\((.*?)\\)\\s+(VALUES).*\\s+\\((.*?)\\);");
            if(!chk1){
                System.out.println("Something went wrong");
            }
        }

        if(chk){
            return true;
        }
        return false;

    }

    boolean parsers(String regx){

        boolean chk=false;
        System.out.println(regx);
        final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(query);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));

            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
            chk=true;
        }
        if(chk){
            return true;
        }
        return false;
    }

    public boolean manager() throws InvalidQueryException{
        takeInput();

        return true;
    }

}
