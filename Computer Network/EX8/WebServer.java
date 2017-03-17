//WeiYing Lee and Max.Y.Kim
import java.net.*;
import java.io.*;
import java.util.*;

public class WebServer {
	
	public static void main(String[] args) throws Exception{
		try(ServerSocket serverSocket = new ServerSocket(8080)){
			while(true){
				Socket socket = serverSocket.accept();
				//Read in the client side message
            	InputStream is = socket.getInputStream();
            	InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            	BufferedReader br = new BufferedReader(isr);

            	String filename = "";
            	String str = ".";

				while(true){
	      			str = br.readLine();
	      		
				    StringTokenizer st = new StringTokenizer(str);

				    // Parse the filename from the GET command
				    if (st.hasMoreElements() && st.nextToken().equalsIgnoreCase("GET") && st.hasMoreElements()){
				    	filename = st.nextToken();
				    }

				    // Remove leading / from filename
				    while (filename.indexOf("/") == 0){
						filename = filename.substring(1);
				    }

	      			System.out.println(str);

	      			if(str.equals("")){
	      				break;
	      			}
				}

            	//Write to client
            	PrintWriter out = new PrintWriter(socket.getOutputStream());
            	boolean fileExist = false;

            	try {
            		FileInputStream fis = new FileInputStream("./www/" + filename);
            		fileExist = true;
            	}catch(FileNotFoundException e){
            		fileExist = false;
            	}

            	if(fileExist){
				    out.println("HTTP/1.1 200 OK");
				    out.println("Content-Type: text/html");
				    out.println("Content-length: 124");
				    out.println("");

			        // FileReader reads text files in the default encoding.
			        FileReader fileReader = new FileReader("./www/" + filename);

		            // Always wrap FileReader in BufferedReader.
		            BufferedReader bufferedReader = new BufferedReader(fileReader);

		            String line = null;
		            String content = "";
		            while((line = bufferedReader.readLine()) != null) {
		                content += line;
		            }   

		            out.println(content);

		            // Always close files.
		            bufferedReader.close();   
				    out.flush();            		
            	}else{
            		out.println("HTTP/1.1 404 Not Found");
				    out.println("Content-Type: text/html");
				    out.println("Content-length: 126");
				    out.println("");
				   	out.println("<html>"
			         	+ "<head><title>Not Found</title></head>" 
			         	+ "<body>Sorry, the object you requested was not found.</body>"
						+ "<html>");
				   	out.flush();
            	}
			}
		}
	}
}