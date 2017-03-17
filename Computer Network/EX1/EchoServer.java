import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer implements Runnable {

    private Socket socket;

    //Constructor
    public EchoServer(Socket socket){
        this.socket = socket;
    }

    public static void main(String[] args) throws Exception {
        try(ServerSocket serverSocket = new ServerSocket(22222)){
            while(true){
                Socket sock = serverSocket.accept();
                EchoServer server = new EchoServer(sock);
                Thread thread = new Thread(server);
                thread.start();
            }
        }
    }

    public void run(){
        try{
            String address = socket.getInetAddress().getHostAddress();
            System.out.printf("Client connected: %s%n", address);

            //Read in the client side message
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            
            //For printing on the client side
            OutputStream os = socket.getOutputStream();                     
            PrintStream out = new PrintStream(os, true, "UTF-8");
            
            if(message.equals("exit")){
                System.out.printf("Client disconnected: %s%n", address);
            }else{
                out.println("Server> " + message);
            }
            
            is.close();
            out.close();
            socket.close();

        }catch(Exception e){
            System.out.println(e);
        }

    }
}
