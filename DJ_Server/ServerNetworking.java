package DJ_Server;

import DataTypes.*;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerNetworking {

    //networking details
    private final int portNumber = 8080;

    private int connectedClients = 0;
    private ServerSocket serverSocket;

    //this is so we still have access to it even after we finish starting the server
    protected ServerNetworkingHelperThread helper;

    //constructor
    public ServerNetworking()
    {



        startServer();
    }

    //this is the function that will start all the threads for server networking
    private void startServer()
    {
        try
        {
            serverSocket = new ServerSocket(portNumber);

            System.out.println("Server Socket Opened: " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());

            //create a new runnable object
            helper = new ServerNetworkingHelperThread( this);

            Thread netThread = new Thread(helper, "Networking Helper Thread");
            netThread.start();
        }
        catch(IOException e)
        {
            System.out.println("Opening server failed.");
        }
    }


    public synchronized void addConnectedClient()
    {
        connectedClients++;
        System.out.println("Added client. Connected Clients: " + connectedClients);
    }

    public void removeConnectedClient()
    {
        connectedClients--;
    }

    public int getConnectedClients()
    {
        return connectedClients;
    }

    public ServerSocket getServerSocket()
    {
        return serverSocket;
    }



}
