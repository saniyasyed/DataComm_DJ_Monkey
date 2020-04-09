package DJ_Client;

import DataTypes.GlobalVariables.NetworkingVars;
import DataTypes.HelperClasses.NetworkMessage;
import DataTypes.Song;
import DataTypes.VoteMessage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientNetworkingHelperThread implements Runnable {

    protected ClientNetworking networking;
    protected Socket socket;
    protected DataInputStream inputStream;
    protected DataOutputStream outputStream;

    protected boolean stayConnected = true;
    protected OutgoingProcessorThread outgoingProcessor;

    protected Queue outputQueue;

    public ClientNetworkingHelperThread(ClientNetworking networking)
    {
        this.networking = networking;
        this.socket = networking.socket;

        outputQueue = new ConcurrentLinkedQueue<>(); //new LinkedBlockingQueue<Runnable>(10);

    }

    @Override
    public void run() {
        try
        {
            System.out.println("Attempting Connection...");
            socket = new Socket(networking.ipAddress, networking.portNum);

            System.out.println("Socket Connected: " + socket.isConnected());

            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream os = new ObjectInputStream(socket.getInputStream());

            outgoingProcessor = new OutgoingProcessorThread(objectOutputStream);
            Thread outgoingThread = new Thread(outgoingProcessor, "Outgoing Processor Thread");
            outgoingThread.start();


            String clientMessage = "", serverMessage = "";

            //if the stay connected variable is true and the client hasn't said bye
            while(stayConnected || outputQueue.size() > 0)
            {

                //sending to server
//                if(outputQueue.size() > 0)
//                {
//                    Object obj = outputQueue.poll();
//
//                    objectOutputStream.writeObject(obj);
//                    objectOutputStream.flush();
//
//                }


                //reading from server


                NetworkMessage message = (NetworkMessage)os.readObject();
                switch (message.GetTypeName())
                {
                    case "String":
                        serverMessage = (String)message.message;
                        System.out.println("From Server: " + serverMessage );
                        break;
                    default:
                        System.out.println("Not sure what came in. Type: " + message.objType.getTypeName());
                }

            }

            //do the disconnection stuff
            DisconnectSocket();


        } catch (UnknownHostException e) {
            System.out.println("Unknown Host Exception");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    protected class OutgoingProcessorThread implements Runnable
    {
        public boolean stayConnected = true;

        protected ObjectOutputStream os;
        public OutgoingProcessorThread(ObjectOutputStream os)
        {
            this.os = os;
        }

        @Override
        public void run() {

            while(stayConnected || outputQueue.size() > 0)
            {
                if(!outputQueue.isEmpty())
                {
                    try
                    {
                        Object obj = outputQueue.poll();

                        os.writeObject(obj);
                        os.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public synchronized void StartDisconnection()
    {
        //if the socket is connected...
        if(socket.isConnected())
        {
            stayConnected = false;
            outgoingProcessor.stayConnected = false;
        }

    }

    private void DisconnectSocket()
    {
        //if the socket is connected...
        if(socket.isConnected())
        {
            try
            {
                SendString(NetworkingVars.DisconnectionString);

                System.out.println("Disconnecting from server");
                outputStream.close();
                inputStream.close();
                socket.close();

            } catch (IOException e) {
                System.out.println("Error during client disconnection:");
                e.printStackTrace();
            }
        }

    }

    public boolean isSocketConnected()
    {
        return socket.isConnected();
    }

    /**
     * This function sends a string through the server
     * @param message
     * @return a bool representing if the sending was successful
     */
    public synchronized boolean SendString(String message)
    {
        if(isSocketConnected())
        {
            NetworkMessage netObj = new NetworkMessage(new String().getClass(), message);
            outputQueue.add(netObj);

            if(message.equals("bye"))
                StartDisconnection();


            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * This function sends a song through the server
     * @param song
     * @return a bool representing if the sending was successful
     */
    public synchronized boolean SendSong(Song song)
    {
        if(isSocketConnected())
        {
            NetworkMessage netObj = new NetworkMessage(new Song().getClass(), song);
            outputQueue.add(netObj);

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * This function sends a song vote to the server
     * @param song
     * @return a bool representing if the sending was successful
     */
    public synchronized boolean SendVote(Song song, boolean isUpVote)
    {
        if(isSocketConnected())
        {
            VoteMessage vote = new VoteMessage(isUpVote, song);

            NetworkMessage netObj = new NetworkMessage(new VoteMessage().getClass(), vote);
            outputQueue.add(netObj);

            return true;
        }
        else
        {
            return false;
        }
    }


}
