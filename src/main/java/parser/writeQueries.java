package parser;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class writeQueries {
    public static String dbName="";
    public static String query="";

    void takeInput(){
        Scanner sc = new Scanner(System.in);

       /* if(dbName.equals("")){
            System.out.println("Write the database name:");
            dbName = sc.nextLine();
        }*/
        System.out.println("Write the query()");
        query= sc.nextLine();
        System.out.println(query);
        parse();
    }
    void parse(){
        String[] type = query.split(" ");
        if(type[0].equalsIgnoreCase("USE")){
           Boolean chk= parsers("(?i)(use)\\s+(\\w+)\\s*;");
           if(!chk){
               System.out.println("Something went wrong");
           }
        }


    }
    boolean parsers(String regx){


        System.out.println(regx);
        final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(query);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));

            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }
        return true;
    }

    public boolean manager(){
        takeInput();

        return true;
    }

}
