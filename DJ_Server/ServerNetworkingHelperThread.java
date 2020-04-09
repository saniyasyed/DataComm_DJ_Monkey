package DJ_Server;

import DataTypes.HelperClasses.NetworkMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerNetworkingHelperThread implements Runnable {

    protected static ServerSocket serverSocket;
    protected static ServerNetworking networking;

    protected Queue outputQueue;
    protected Queue incomingQueue;

    protected InputQueueProcessorThread inputProcessor;
    protected Thread incomingProcessorThread;


    public ServerNetworkingHelperThread(ServerNetworking serverNetworking)
    {

        this.networking = serverNetworking;
        this.serverSocket = serverNetworking.getServerSocket();

        //these are the input output queue for all the networking
        outputQueue = new ConcurrentLinkedQueue<>();
        incomingQueue = new ConcurrentLinkedQueue<>();
    }



    @Override
    public void run() {

        //create the object that will be processing incoming requests
        inputProcessor = new InputQueueProcessorThread();
        incomingProcessorThread = new Thread(inputProcessor, "Input Queue Processor Thread");
        incomingProcessorThread.start();


        //infinite while loop to always be listening for connections
        while(true)
        {
            try
            {
                //wait for then create the connection to the client
                Socket clientConnection = serverSocket.accept(); //connection to the client

                networking.addConnectedClient();

                System.out.println("A client connected.");
                //create the class
                clientInteractorThread CIThread = new clientInteractorThread(clientConnection, networking.getConnectedClients());
                //create the thread
                Thread clientThread = new Thread(CIThread, "Connection To Client " + networking.getConnectedClients());

                //start the thread
                clientThread.start();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    protected class clientInteractorThread implements Runnable
    {

        protected Socket clientSocket;
        protected int clientNum;

        protected ObjectOutputStream objOutput;
        protected OutgoingProcessorThread outgoingProcessor;

        public clientInteractorThread(Socket clientSocket, int clientNum)
        {
            this.clientSocket = clientSocket;
            this.clientNum = clientNum;


        }


        @Override
        public void run() {

            try
            {
                //DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                //DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

                ObjectInputStream os = new ObjectInputStream(clientSocket.getInputStream());
                objOutput = new ObjectOutputStream(clientSocket.getOutputStream());

                outgoingProcessor = new OutgoingProcessorThread();
                Thread outgoingThread = new Thread(outgoingProcessor, "Outgoing Processor Thread_CLient-" + clientNum);
                outgoingThread.start();

                String clientMessage = "", serverMessage = "";

                while(!clientMessage.equals("bye"))
                {

                    //do all the sending to the client
                    //if the server has anything to send...
//                    if(outputQueue.size() > 0)
//                    {
//                        CompletableFuture.runAsync(() ->{
//                            try
//                            {
//                                Object obj = outputQueue.poll();
//
//                                objOutput.writeObject(obj);
//                                objOutput.flush();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        });
//                    }


                    //add everything from the client to a queue to be processed

                    NetworkMessage message = (NetworkMessage)os.readObject();
                    message.senderId = clientNum;

                    //this queue is processed by the "InputQueueProcessorThread"
                    incomingQueue.add(message);

                }
                //close the streams
                outgoingProcessor.stayConnected = false;

                os.close();
                objOutput.close();
                clientSocket.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            networking.removeConnectedClient();
        }

//        /**
//         * This function reads the bytes send by the client and converts it into an object.
//         * The object returned will most likely be a NetworkMessage
//         * @param theBytes
//         * @return
//         */
//        private Object ReadObjectFromBytes(byte[] theBytes)
//        {
//            try
//            {
//                ByteArrayInputStream byteInputStream = new ByteArrayInputStream(theBytes);
//                ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
//
//                Object obj = objectInputStream.readObject();
//
//                return obj;
//
//            } catch (IOException | ClassNotFoundException e) {
//                System.out.println("Error while reading object from client");
//                e.printStackTrace();
//            }
//
//            System.out.println("Returning a null? Shouldn't do this");
//            return null;
//        }


        protected class OutgoingProcessorThread implements Runnable
        {
            public boolean stayConnected = true;

            public OutgoingProcessorThread()
            {

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

                            objOutput.writeObject(obj);
                            objOutput.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

    }


    /**
     * This class is going to be the thread that handles all objects in the input queue
     */
    protected class InputQueueProcessorThread implements Runnable
    {

        public InputQueueProcessorThread()
        {

        }


        @Override
        public void run() {
            //always run
            while(true)
            {

                //make sure the queue actually has something in it before processing
                if(incomingQueue.size() > 0)
                {
                    NetworkMessage message = (NetworkMessage)incomingQueue.poll();
                    switch (message.GetTypeName())
                    {
                        case "String":
                            String clientMessage = (String)message.message;
                            System.out.println("From Client-" + message.senderId + ": " + clientMessage );
                            break;
                        default:
                            System.out.println("Not sure what came in. Type: " + message.objType.getTypeName());
                    }
                }
            }


        }
    }






}
