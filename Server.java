/*
     Names: Pedro Malavet,Demetre Battiste, Matthew Emerick
     Date:10/14/2020
     Description: This program is the sever that accepts requests from the Client
*/

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.net.*;
import java.time.*;

public class Server
{

   public Server()
   {

       ServerSocket listener;
       Socket client;
       DataInputStream din;
       DataOutputStream dout;
       ArrayList<ClientHandler> clients = new ArrayList<>();
       ExecutorService pool = Executors.newFixedThreadPool(4);

      try
      {
            Scanner input = new Scanner(System.in);

            //Get port number
            int port = 1112;
            System.out.println("[Server] Waiting for Client");
            listener = new ServerSocket(port);


             //Listen For requests
             while(true)
             {
                  client = listener.accept();
                  System.out.println("[Server] Client Connected");
                  ClientHandler clientThread = new ClientHandler(client);
                  clients.add(clientThread);

                  pool.execute(clientThread);
             }
			 





      }
      catch(IOException e)
      {
            e.printStackTrace();
      }

   }


   //Call Constructor
   public static void main(String [] args)
   {
      new Server();
   }

}