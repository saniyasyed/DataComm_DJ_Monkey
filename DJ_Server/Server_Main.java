package DJ_Server;

import DataTypes.*;

public class Server_Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello from Server!");

        ServerNetworking networking = StartNetworking();

    }


    public static ServerNetworking  StartNetworking()
    {
        System.out.println("Starting networking...");

        ServerNetworking networking = new ServerNetworking();

        return networking;
    }

}