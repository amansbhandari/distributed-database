package parser;

import java.io.IOException;
import java.util.Scanner;


import parser.exception.InvalidQueryException;

public class console {


    boolean picker(int no) {
        if(no==1){
            WriteQueries wq = new WriteQueries();

            boolean chk=wq.manager();
            if(!chk){
                System.out.println("Something went wrong");
            }
            return true;
        }
        if(no==2){


            return true;
        }
        if(no==3){

            return true;
        }
        if(no==4){

            return true;
        }
        return false;
    }


    void userInput(){

        Scanner sc = new Scanner(System.in);
        System.out.println();

        System.out.println("======MENU======");
        System.out.println();

        System.out.println("1. Write Queries");
        System.out.println("2. Export");
        System.out.println("3. Data Model");
        System.out.println("4. Analysis");
        int no = 1;
                //sc.nextInt();
        Boolean success= picker(no);
       // if(!success){
         //   System.out.println("Invalid selection, select again");
            userInput();
        //}

    }
    void auth(Boolean passed) throws IOException {

        LoginSignup ls = new LoginSignup();
        if(passed) {
            console con = new console();
            Boolean flag = true;
            while (flag) {
                Scanner sc = new Scanner(System.in);
        /*    String quit="quit";
            System.out.println("press 'q' to exit");

            if(quit.equalsIgnoreCase(sc.next())){
                flag=false;
            }s
          */
                con.userInput();

                sc.close();

            }
            System.out.println("Quitted!!");
        }
        else {
            System.out.println("Try Again!!");
            passed= ls.runHere();
            auth(passed);
        }
    }

    public static void main(String[] string) throws IOException {
        LoginSignup ls = new LoginSignup();
        Boolean passed= ls.runHere();
        console con = new console();
        con.auth(passed);


    }
}

