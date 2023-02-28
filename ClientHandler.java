/*
        Names: Pedro Malavet,Demetre Battiste, Matthew Emerick
        Date:10/14/2020
        Description: This class is the Client Hander for the Server witch handles the requests made by the Clinet
*/

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler implements Runnable
{
   private Socket client;
   private BufferedReader in;
   private PrintWriter out;

   //Sets Delay
   public void Delay()
   {
        long delay = 1000;
        System.out.println("[Server] Delaying for " + delay + "milli sec(s)");

        try
        {
                 Thread.sleep(delay);
        }
        catch(InterruptedException e)
        {
                e.printStackTrace();
        }

   }

   //Constuctor For Class
   public ClientHandler(Socket clientSocket) throws IOException
   {
      this.client = clientSocket;
      in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      out = new PrintWriter(client.getOutputStream(),true);
   }
   
    //Runs Linux command and sends Output to client
   public void runLinuxCmd(String cmd)
   {
        String c;
        Process p;
        out.println("[Server]");
        try
        {
                p = Runtime.getRuntime().exec(cmd);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

                while (true)
                {

                        c = br.readLine();
                        if(c != null)
                        {
                                out.println(c);
                                Delay();
                        }
                        else
                        {
                                break;
                        }


                }
                p.waitFor();
                p.destroy();
        }
		catch (Exception e)
        {
                e.printStackTrace();
        }
   }
   
   //Gets date and time and sends to Client
   public void getDateAndTime()
   {
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
         LocalDateTime now = LocalDateTime.now();
         out.println("[Server] " + dtf.format(now));
   }


   //Tells client to stop geting info
   public void SendQuitRequest()
   {
        out.println("quit");
        System.out.println("[Server] QR sent");
   }
   
     @Override
   public void run()
   {
      try
      {

         while(true)
         {
            String request = in.readLine();
            if(request.contains("Date and Time")) //Date and Time
            {
                  getDateAndTime();
                  System.out.println("[Server] Sent date and time to client");
                  Delay();
                  SendQuitRequest();
            }
            else  if(request.contains("Uptime")) //Uptime
            {
                   runLinuxCmd("uptime");
                   System.out.println("[Server] Sent Uptime");
                   Delay();
                   SendQuitRequest();
            }
            else if(request.contains("Memory Use")) //Memory use
            {
                runLinuxCmd("free -m");
                System.out.println("[Server] Sent Memory Use");
                Delay();
                SendQuitRequest();
            }
			else if(request.contains("Netstat")) //NetStat
            {
                  runLinuxCmd("netstat");
                  System.out.println("[Server] Sent NetStats");
                  Delay();
                  SendQuitRequest();
            }
            else if(request.contains("Current Users")) //Current Users
            {
                runLinuxCmd("who");
                System.out.println("[Server] Sent Current Users");
                Delay();
                SendQuitRequest();
            }
            else //Running Processes
            {
                runLinuxCmd("ps -A");
                System.out.println("[Server] Sent Running Processes");
                Delay();
                SendQuitRequest();
            }

         }

      }