/*
     Names: Pedro Malavet,Demetre Battiste, Matthew Emerick
     Date:10/14/2020
     Description: This program is send requests to the server
*/

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Client
{


   public Client()
   {
      try
      {

            //Get Ip address and port num
            String ipAddress = getIpAddress();
            int port = getPortNum();

            //Establish Socket
            Socket s = new Socket(ipAddress,port);

            while(true)
            {
                  //Ask user for request
                  int choice = getChoice();
                  String request = getRequest(choice);

                  //Ask user for number of requests
                  int numOfReq = getNumOfReq();

                  for(int i =0;i < numOfReq;i++)
                  {
                  //Send request to Server
                  PrintWriter pr = new PrintWriter(s.getOutputStream());
                  pr.println(request);
                  pr.flush();

                        //Get info from Server
                        String str = "";
                        while(!str.equals("quit"))
                        {
                                InputStreamReader in = new InputStreamReader(s.getInputStream());
                                BufferedReader bf = new BufferedReader(in);
                                str = bf.readLine();
						
                                if(str.equals("quit"))
                                break;
                                else
                                System.out.println(str);

                        }
                  }
            }

      }
      catch(IOException e)
      {
         e.printStackTrace();
      }

   }

   //Requests User For IP address
   public String getIpAddress()
   {
        System.out.println("[Client] What is the IP Address of the Server");
        Scanner input = new Scanner(System.in);
        String ip = input.nextLine();
        return ip;
   }

   //Requests User for port number
   public int getPortNum()
   {
        System.out.println("[Client] What is the port number");
        Scanner input = new Scanner(System.in);
        int port = input.nextInt();
        return port;
   }
   
   //Requests User for choice
   public int getChoice()
   {
        int choice;
        do
        {

                //Prompt User
                System.out.println("[Client] What data would you like to request");
                System.out.println("\t1.Date and Time");
                System.out.println("\t2.Uptime");
                System.out.println("\t3.Memory use");
                System.out.println("\t4.Netstat");
                System.out.println("\t5.Current Users");
                System.out.println("\t6.Running Processes");

                //Get choice and Validate Input
                try
                {
                        Scanner input = new Scanner(System.in);
                        choice = input.nextInt();
                }
                catch(Exception e)
                {
                        choice = 7;
                }

                if(!(choice >= 1 && choice <= 6))
                {
                        System.out.println("[Client] Error: Choose number from 1 to 6");
                }

        }while(!(choice >= 1 && choice <= 6));

       return choice;

   }
   
   //Get Num of Requests
   public int getNumOfReq()
   {
        System.out.println("[Client] How many Requests");
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        return num;
   }
   
   //Get string request
   public String getRequest(int choice)
   {

            String request;

            if(choice == 1) //Date and Time
            {
                  request = "Date and Time";
            }
            else if(choice == 2) //Uptime
            {
                  request = "Uptime";
            }
            else if(choice == 3) //Memory Use
            {
                  request = "Memory Use";
            }
            else if(choice == 4) //Netstat
            {
                  request = "Netstat";
            }
            else if(choice == 5) //Current Users
            {
                  request = "Current Users";
            }
            else //Running Processes
            {
                  request = "Running Processes";
            }

            return request;

   }

   //Call Constructor
   public static void main(String [] args)
   {
          new Client();
   }