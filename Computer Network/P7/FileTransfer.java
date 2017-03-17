import java.io.*;
import java.security.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class FileTransfer {

	//calculate the CRC32
	private long computeCRC32(byte bytes[]) {
        Checksum checksum = new CRC32();
        checksum.update(bytes, 0, bytes.length);
        long checksumValue = checksum.getValue();

        return checksumValue;
    }

	//Generate the RSA key pair for serialized forms to files public.bin and private.bin
	public void makeKeyPairs() {
		try {
			KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
			gen.initialize(4096); // you can use 2048 for faster key generation
			KeyPair keyPair = gen.genKeyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("public.bin")))) {
				oos.writeObject(publicKey);
			}
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("private.bin")))) {
				oos.writeObject(privateKey);
			}
		}catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace(System.err);
		}
	}

	//Server mode handle
	public void serverMode(String privateBin, String portNum) throws Exception{
		try(ServerSocket serverSocket = new ServerSocket(Integer.parseInt(portNum))){
			while(true){
				Socket socket = serverSocket.accept();
				int chunkNum = 0;

				//server sending from here
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				
				//server getting from client
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

				Message message = (Message)in.readObject();
				MessageType response = message.getType();
				StartMessage startMsg = (StartMessage)message;
				
				long fileSize = startMsg.getSize();
				int chunkSize = startMsg.getChunkSize();
				byte[][] chunkData = new byte[chunkSize][];
				byte[] sessionKey = startMsg.getEncryptedKey();
				boolean success = false;
				SecretKey decryptedSessionKey;

				//handle session key
				FileInputStream inputFileStream = new FileInputStream(privateBin);
				ObjectInputStream objectIS = new ObjectInputStream(inputFileStream);
				PrivateKey privateKey = (PrivateKey)objectIS.readObject();

				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.UNWRAP_MODE, privateKey);
				decryptedSessionKey = (SecretKey)cipher.unwrap(sessionKey, "AES", Cipher.SECRET_KEY);
				success = true;

				while(true){

					//Client send the DiscoonectMessage: close connection and wait fro a new one
					if(response == MessageType.DISCONNECT){
						socket.close();
					}
					/*
						Client send the StartMessage: prepare for a file transfer base on info in message
						respond to the client with an AckMessage with sequence 0
						if server is unable to begin the file transfer, respond AckMessage with sequence num -1
					*/				
					else if(response == MessageType.START){
						if(success){
							out.writeObject(new AckMessage(0));
						}else {
							out.writeObject(new AckMessage(-1));
							break;
						}
					}
					//Client send StopMessage: discard the associated file transfer and respond AckMessage with -1
					else if(response == MessageType.STOP){
						out.writeObject(new AckMessage(-1));
						break;
					}
					//Client send Chunk: initiated the file transfer, handle Chunk
					else if(response == MessageType.CHUNK){
						
						Chunk chunk = (Chunk)message;

						
						/*
							(a)The chunk'seq num must be the next expected seq num by the server
							
							(b)The server should decrypt the data stored in the chunk using the session key from the transfer init step
							
							(c)The server should calculate the CRC32 value for the decrypted data and compare it with CRC32 included in chunk
							
							(d)if these values match and the seq num of the chunk is the next expected seq num
								the server should accept the chunk by storing the data and incrementing the next expected seq num
							
							(e)The server should then respond with an AckMessage with seq num of the next expected chunk
						*/

						//decrypt the data
						cipher = Cipher.getInstance("AES");
						cipher.init(Cipher.DECRYPT_MODE, decryptedSessionKey);
						byte[] decryptedData = cipher.doFinal(chunk.getData());

						//check CRC32 to accept/store the data
						if(chunk.getCrc() == (int)computeCRC32(decryptedData)){
							chunkData[chunkNum] = decryptedData;
							System.out.println("Chunk received [" + (++chunkNum) + "/" + chunkSize + "].");

							if(chunkNum >= (int)(fileSize / chunkSize)){
								System.out.println("Transfer complete.");
								break;
							}
						}

						//send the Ack
						out.writeObject(new AckMessage(chunkNum));

					}					
				}

				//output path
				try{
					FileOutputStream output = new FileOutputStream("test2.txt", true);
					for(byte[] data: chunkData){
						output.write(data);
					}
					out.close();
					System.out.println("Output path: test2.txt");
				}catch(NullPointerException e){
					System.out.println("Transfer failed.");
				}
			}
		}
	}

	//Client mode handle
	public void clientMode(String pubKeyFileName, String host, String portNum) throws Exception{

		int port = Integer.parseInt(portNum);

		// generate session key
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256);
		SecretKey sessionKey = keyGen.generateKey();

		FileInputStream is = new FileInputStream(pubKeyFileName);
		ObjectInputStream os = new ObjectInputStream(is);
		PublicKey publicKey = (PublicKey) os.readObject();

		// encrypt the session key(AES) using public key(RSA)
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.WRAP_MODE, publicKey);
		byte[] encryptedSessionKey = cipher.wrap(sessionKey);

		// ask the client where the file is located
		System.out.print("Enter path: ");
		Scanner keyboard = new Scanner(System.in);
		String filename = keyboard.nextLine();

		// get the file the client wants to send
		boolean fileExist = false;
		try{
			FileInputStream fis = new FileInputStream(filename);
			fileExist = true;
		}catch(FileNotFoundException e){
			fileExist = false;
		}
		
		int userChunkSize = 0;
		if(fileExist){
			System.out.print("Enter chunk size [1024]: ");
			userChunkSize = keyboard.nextInt();
		}else{
			System.out.println("File does not exist.");
			System.exit(0);
		}

		// send the server a StartMessage
		try (Socket socket = new Socket(host, port)) {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			out.writeObject(new StartMessage(filename, encryptedSessionKey, userChunkSize));
			AckMessage ackMessage = (AckMessage) in.readObject();
			if (ackMessage.getSeq() == -1) {
				System.out.println("Transfer failed.");
				socket.close();
			}

			// read file
			Path pathForArray = Paths.get("./");
			byte[] data = Files.readAllBytes(pathForArray);

			int numOfChunk = data.length / userChunkSize;
			byte[] chunkData = new byte[numOfChunk];

			// encrypt using SessionKey
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.WRAP_MODE, sessionKey);
			for (int i = 0; i < numOfChunk; i++) {
				for (int j = 0; j < userChunkSize; j++) {
					chunkData[j] = data[j + (i * 3)];
				}
				chunkData = sessionKey.getEncoded();
				byte[] encryptedChunk = cipher.wrap(sessionKey);
				int crc = (int) computeCRC32(encryptedChunk);
				out.writeObject(new Chunk(i, encryptedChunk, crc));
				System.out.println("Chunk completed [" + (i + 1) + "/" + numOfChunk + "]");
				if (ackMessage.getSeq() != i + 1) {
					System.out.println("Transfer failed.");
					socket.close();
				}
			}
		}
	}

	public static void main(String[] args) throws Exception{
		
		FileTransfer ft = new FileTransfer();
		
		if(args.length != 0){
			switch(args[0]){
				case "makekeys":
					/*
						first command line arg is makekeys, generate a public/private.bin
						RSA key pair. Then exit
					*/
					ft.makeKeyPairs();
					break;
				case "server":
					/*
						first command line arg is server, your program will operate in server mode. In this case,
						you must include two more command line arguments:
							1. name of the file that contains the private key. 
							2. the port number the server will listen on.
					*/

					//Handle the require arguments
					if(args.length == 3){
						String privateBin = args[1],
							   portNum = args[2];
						
						ft.serverMode(privateBin, portNum);
					}else{
						System.out.println("Three command line arguments needed.");
					}
					break;
				case "client":
					/*
						first command line arg is client, your program will operate in client mode. In this case,
						you must include three more command line arguments: 
							1. the name of the file that contains the public key.
							2. the host to connect to (where the server is running). 
							3. the port number the server is listening on.
					*/

					//Handle the require arguments
					if(args.length == 4){
						String publicBin = args[1], 
							   host = args[2],
							   portNum = args[3]; 

						ft.clientMode(publicBin, host, portNum);
					}else{
						System.out.println("Four command line arguments needed.");
					}
					break;
				default:
					System.out.println("Invalid command line argument.");
					break;
			}
		}else{
			System.out.println("No arguments inputed.");
		}
	}
}