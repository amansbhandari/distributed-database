package parser;

import java.util.Scanner;

public class writeQueries {
    public static String dbName="";

    void takeInput(){
        Scanner sc = new Scanner(System.in);

        if(dbName.equals("")){
            System.out.println("Write the query()");
            dbName = sc.nextLine();
        }
        System.out.println("Write the query()");
    }

    public boolean manager(){
        takeInput();

        return false;
    }

}
