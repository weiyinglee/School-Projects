import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ThreadTest {

	private static volatile boolean reading = true;
	private static volatile boolean processing = true;
	private static BlockingQueue<File> filePacket = new LinkedBlockingQueue<>();
	private static BlockingQueue<String> textPacket = new LinkedBlockingQueue<>();
	private static int[] counts = new int[3];

	private static final class IOThread implements Runnable {
		public void run() {
			Thread fileRead = new Thread(new FileReadThread());
			Thread process = new Thread(new ProcessThread());

			fileRead.start();
			process.start();

			Scanner keyboard = new Scanner(System.in);
			String command;

			boolean running = true;

			while(running) {
				System.out.print("> ");
				command = keyboard.nextLine();
				String[] commandList = command.split(" ");

				switch(commandList[0]) {
					case "read":
						try {
							File file = new File(commandList[1]);

							if(file.exists() && file.isFile()) {
								filePacket.add(file);
							}else {
								System.out.println("File does not exist");
							}
						}catch(ArrayIndexOutOfBoundsException e) {
							System.out.println("Please input a file");
						}

						break;
					case "counts":
						System.out.println("Lowercase: " + counts[0]);
						System.out.println("Uppercase: " + counts[1]);
						System.out.println("Number(0~9): " + counts[2]);
						break;
					case "exit":
						try {
							reading = false;
							processing = false;
							running = false;
							System.exit(0);
						}catch(Exception e){
							System.out.println(e);
						}
						break;
					default:
						System.out.println("Invalid command");
				}
			}

		}
	}

	private static final class FileReadThread implements Runnable {
		public void run() {
			
			Scanner fileReader;

			while(reading) {
				try {
					fileReader = new Scanner(filePacket.take());

					while(fileReader.hasNext()) {
						textPacket.put(fileReader.nextLine());
					}

					fileReader.close();
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	private static final class ProcessThread implements Runnable {
		public void run() {
			while(processing) {
				try {
					String line = textPacket.take();
					char[] text = line.toCharArray();
					for(int i = 0; i < text.length; i++) {
						if(Character.isLowerCase(text[i])) {
							counts[0]++;
						}else if(Character.isUpperCase(text[i])) {
							counts[1]++;
						}else if(Character.isDigit(text[i])) {
							counts[2]++;
						}
					}
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
	}

	public static void main(String args[]) {
		Thread ioThread = new Thread(new IOThread());
		ioThread.start();
	}
}