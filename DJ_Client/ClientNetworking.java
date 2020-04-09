package DJ_Client;

import DataTypes.Song;

import java.net.Socket;

public class ClientNetworking {

    protected Socket socket;
    protected Thread helperThread;
    protected ClientNetworkingHelperThread clientHelper;

    protected String ipAddress = "localhost";
    protected int portNum = 8080;

    public ClientNetworking()
    {

        ConnectToServer(ipAddress, portNum);
    }

    /**
     * This function connects to the server with the given IP and port
     * @param ip
     * @param port
     */
    public void ConnectToServer(String ip, int port)
    {
        ipAddress = ip;
        portNum = port;

        clientHelper = new ClientNetworkingHelperThread(this);

        helperThread = new Thread(clientHelper, "Connection To Server");

        helperThread.start();

    }

    /**
     * This function tells the networking thread to disconnect from the server.
     */
    public void DisconnectFromServer()
    {
        clientHelper.StartDisconnection();
    }

    /**
     * This function checks to see if the socket is connected
     * @return whether the thread's socket is connected
     */
    public boolean isConnectedToServer()
    {
        return clientHelper.isSocketConnected();
    }

    /**
     * This function will send a given string to the server.
     * @param message
     * @return boolean as to whether the sending was successful or not
     */
    public boolean SendStringToServer(String message)
    {
        //first check to see if the client is connected at all
        if(isConnectedToServer())
        {
            clientHelper.SendString(message);
            return true;
        }
        //if the client isn't connected, return false
        else
        {
            return false;
        }
    }

    /**
     * This function sends a vote to the server
     * @param isUpVote
     * @param song
     * @return
     */
    public boolean SendVoteToServer(boolean isUpVote, Song song)
    {
        if(isConnectedToServer())
        {
            return clientHelper.SendVote(song, isUpVote);
        }
        else
        {
            return false;
        }
    }


//    //uses the default ip and port values
//    public void ConnectToServer()
//    {
//        ConnectToServer(ipAddress, portNum);
//    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public int getPortNum()
    {
        return portNum;
    }


}
