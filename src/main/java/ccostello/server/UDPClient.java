package ccostello.server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPClient
{
   public static void main(String args[]) throws Exception
   {
      BufferedReader inFromUser =
         new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("centos1");
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      String sentence = "QOTM?";
      sendData = sentence.getBytes();
      long start = System.currentTimeMillis();
      for ( int i = 0; i < 10000; i ++ ) {
	      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
	      clientSocket.send(sendPacket);
	      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	      clientSocket.receive(receivePacket);
	      String modifiedSentence = new String(receivePacket.getData());
	      try {
	    	  Thread.sleep( 2 );
	      } catch ( Exception e ) {}
      }
      long stop = System.currentTimeMillis();
      System.out.println( "Took " + (stop-start) + "ms to send 10000 msgs." );
      //System.out.println("FROM SERVER:" + modifiedSentence);
      clientSocket.close();
   }
}