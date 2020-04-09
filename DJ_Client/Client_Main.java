package DJ_Client;

import DataTypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Client_Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello from Client!");


        ClientNetworking networking = StartNetworking();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //System.out.println("Socket is connected: " + networking.isConnectedToServer());

        String clientMessage = "";
        try {
            while(!clientMessage.equals("bye"))
            {
                System.out.println("Write a message to send to the server:");
                clientMessage = br.readLine();
                networking.SendStringToServer(clientMessage);

            }

            networking.DisconnectFromServer();

        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    public static ClientNetworking StartNetworking()
    {
        System.out.println("Starting Client Networking...");

        ClientNetworking networking = new ClientNetworking();

        return networking;
    }
}