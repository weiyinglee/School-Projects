
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public final class EchoClient {

    public static void main(String[] args) throws Exception {
    	while(true){
	    	try (Socket socket = new Socket("localhost", 22222)) {

		        	Scanner keyboard = new Scanner(System.in);

	        		//output for client to server
	        		System.out.print("Client> ");
	        		String input = keyboard.nextLine();

		        	OutputStream os = socket.getOutputStream();
		        	PrintStream out = new PrintStream(os, true, "UTF-8");
		        	out.println(input);

		        	//print the server output
		            InputStream is = socket.getInputStream();
		           	InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		            BufferedReader br = new BufferedReader(isr);
					String message = br.readLine();
		            if(message == null){
		            	break;
		            }       
		            System.out.println(message);
	        }
    	}
    }
}















