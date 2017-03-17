import java.io.IOException;

public class ProcessTest {

	public static void main(String args[]) throws IOException {
		
		//set subprocess
		ProcessBuilder builder = new ProcessBuilder("java");
		builder.inheritIO();
		Process process = builder.start();
	}

}