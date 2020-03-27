import DJ_Client.Client_Main;
import DJ_Server.Server_Main;
import java.util.Scanner;
public class DJ_Monkey_Starter {

    public static void main(String[] args)
    {
        System.out.println("Hello from DJ_Monkey_Starter!");

        System.out.println("Please enter the number of the option you'd like to select:");
        System.out.println("1. Server\n2. Client");

        Scanner kb = new Scanner(System.in);
        int choice = kb.nextInt();

        if(choice == 1)
        {
            Server_Main.main(new String[0]);
        }
        else if(choice == 2)
        {
            Client_Main.main(new String[0]);
        }
        else
        {
            System.out.println("That was not an option.");
        }


        System.out.println("Program is closing...");
    }


}
