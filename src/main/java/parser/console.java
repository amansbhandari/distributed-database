package parser;

import java.util.Scanner;

public class console {


    boolean picker(int no){
        if(no==1){
            writeQueries wq = new writeQueries();
            boolean chk=wq.manager();
            if(!chk){
                System.out.println("Something went wrong");
            }
            return true;
        }
        if(no==2){

            analysis als = new analysis();
            boolean chk=als.manager();
            if(!chk){
                System.out.println("Something went wrong");
            }
            return true;
        }
        if(no==3){

           export ex = new export();
            boolean chk=ex.manager();
            if(!chk){
                System.out.println("Something went wrong");
            }
            return true;
        }
        if(no==4){

            analysis als = new analysis();
            boolean chk=als.manager();
            if(!chk){
                System.out.println("Something went wrong");
            }
            return true;
        }
        return false;
    }

    void userInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Write Queries");
        System.out.println("2. Export");
        System.out.println("3. Data Model");
        System.out.println("4. Analysis");
        Boolean success= picker(sc.nextInt());
        if(!success){
            System.out.println("Invalid selection, select again");
            userInput();
        }
        sc.close();
    }

    public static void main(String[] main){

        console con = new console();
        Scanner sc = new Scanner(System.in);
        while(true){
            String quit="";
            System.out.println("type 'Quit' to exit");
            quit= sc.nextLine();
            if(quit.equalsIgnoreCase("quit")){
                break;
            }
            con.userInput();
        }
        System.out.println("Quitted!!");

        sc.close();

    }
}
