/* I added this class for debugging purposes and PING/PONG */

import java.net.*;
import java.io.*;
import java.util.*;
import java.io.PrintWriter;
public class InputDumper extends Thread
{
   private DataInputStream in;
   private DataOutputStream out;
   private boolean pongvalidation;
   
   protected InputDumper(InputStream in, OutputStream out)
   {
      this.in = new DataInputStream(in);
      this.out = new DataOutputStream(out); // this was added to handle the ping debugging (see below)
      pongvalidation = false;
   }
   
   public void run()
   {
      try
      {
         String msg;
         // I referenced (stole from)the following URL for some of the while loop below:  https://gist.github.com/thefinn93/1842069 
         
         while((msg = in.readLine()) != null)            // While there is still data coming in
         { 		                                         
		      System.out.println(msg);					    //  ****This is printing PING yo console
		      String[] parsedline = msg.split(" ",4);	// Split it by the first four spaces. After that is the actual message (except for some cases, see next two lines).
			   if(parsedline[0].equals("PING"))         // If the message starts with PING.
            {			
			   out.writeBytes("PONG "+ parsedline[1]+ "\n");	// Send back "PONG" followed by the ID thing they send us.
               //pongvalidation = true; 
               System.out.println("PONG " + parsedline[1]+"\r\n"); //printing PONG to console
			}
         }
         
       
      }
      catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}
   
 /*
   Not needed but keeping for future implementation
   
   public boolean getPongValidation() //used to validate PONG
   {
      return pongvalidation;
   }
}
*/
