/*
RadIRC Java Client 
So far this sends a single line of text to an IRC server. Tested and verified working on UnrealIRCd server stable v4.0

*/

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RadIrc 
{
   private static Thread t; //instantiate thread
   private static boolean pongvalidation;
    static void send(BufferedWriter bw, String str) 
    {
      try 
      {
         bw.write(str + "\r\n"); //sends input to remote IRC server
         bw.flush();
      }
      catch (Exception e) 
      {
         System.out.println("Exception: "+e);
      }
   }
   
   public static void main(String args[]) 
   {
      try 
      {
         String server = "localhost";
         int port = 6667;
         String nickname = "oryx17";
         String channel = "#Cheers";
         String msg = "Hello World!!";
   
         Socket socket = new Socket(server,port);
         InputDumper t = new InputDumper(socket.getInputStream(), socket.getOutputStream()); //assign thread to an InputDumper object which im feeding I/O
         t.setDaemon(true); // open the debugging daemon 
         t.start();        //start the debugging deamon 
         System.out.println("*** Connected to server.");
         OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
         System.out.println("*** Opened OutputStreamWriter.");
         BufferedWriter bw = new BufferedWriter(outputStreamWriter);
         System.out.println("*** Opened BufferedWriter.");
         send(bw,"NICK "+nickname);
         /*  Not needed but keeping to remember for future implementation
         
          while(pongvalidation = t.getPongValidation() != true) //wait for pong
          {
            pongvalidation = t.getPongValidation();
          }
          */
          send(bw,"USER "+nickname+ " null null "+nickname); //USER, server, host, NICK
          send(bw,"JOIN : "+channel);                       //join Channel 
          TimeUnit.SECONDS.sleep(1);                       //wait 1 second for server to catch up
          send(bw,"PRIVMSG "+channel+" : "+msg);          //send message
          bw.close();                                    //when it hits this point it terminates connection
      }
      catch (Exception e) 
      {
         e.printStackTrace();
      }
   }   
}
