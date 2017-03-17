import java.io.InputStream;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public final class ChatClient implements Runnable {
	
	private Socket socket;

	//Constructor
	public ChatClient(Socket socket){
		this.socket = socket;
	}

	public Socket getSocket(){
		return socket;
	}

	public void run(){
		try{
			while(true){
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String response = br.readLine();
				System.out.println(response);
				//if the response is "Name in use.", close the server
				if(response.equals("Name in use.")){			
					break;
				}
			}
			socket.close();
		}catch(Exception e){
			System.out.println(e);
		}			
	}

	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("codebank.xyz", 38001);
		
		ChatClient client = new ChatClient(socket);
		Scanner keyboard = new Scanner(System.in);

		System.out.print("Enter the username: ");
		String username = keyboard.nextLine();

		//send it to the server
		OutputStream os = client.getSocket().getOutputStream();
		PrintStream out = new PrintStream(os, true, "UTF-8");
		out.println(username);

		Thread thread = new Thread(client);
		thread.start();

		while(thread.isAlive()){
			String message = keyboard.nextLine();
			out.println(message);
		}
	}
}