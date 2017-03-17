//Authors: WeiYing Lee & Max Y. Kim

import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;


public class Ex2Client {
    
    public static void main(String args[]) throws Exception {
        
        try (Socket socket = new Socket("codebank.xyz", 38102)) {
            
            // byte a = 127 works, byte a = 128 does not; range: -128 to 127
            // using 2's complement
            
            byte halfByte1;
            byte halfByte2;
            byte shiftedByte;
            byte wholeByte;
            byte[] bytes = new byte[100];
            
            // // show the connected message
            System.out.println("Connected to server");
            
            // // Receive bytes from server
            InputStream is = null;
            // perform the construction for bytes for 100 times
            for (int i = 0; i < 100; i++) {
                is = socket.getInputStream();
                halfByte1 = (byte) is.read();
                
                is = socket.getInputStream();
                halfByte2 = (byte) is.read();
                
                // shift 4 for constructing the one whole byte
                shiftedByte = (byte) (halfByte1 << 4);
                
                // add together
                wholeByte = (byte) (shiftedByte | halfByte2);
                
                bytes[i] = wholeByte;
            }
            
            //compute CRC32 code
            showReceivedData(bytes);
            long checksumValue = (int) computeCRC32(bytes);
            
            //String hex = Long.toHexString(checksumValue);
            //print the Generated CRC32
            System.out.println("Generated CRC32:" + Long.toHexString(checksumValue));
            
            byte[] sendPackage = generateCRCtoBytes(checksumValue);
            
            OutputStream os = socket.getOutputStream();
            os.write(sendPackage);
            
            if (is.read() == 1)
                System.out.println("Response good");
            else
                System.out.println("Response bad");
            
            // show the disconnection message
            System.out.println("Disconnected from server");
            
        }
    }

    public static byte[] generateCRCtoBytes(long value){
	    byte firstPiece = (byte)(value >> 24);
        byte secondPiece = (byte)(value >> 16);
        byte thirdPiece = (byte)(value >> 8);
        byte fourthPiece = (byte)(value);
        
        byte [] array = {firstPiece, secondPiece, thirdPiece, fourthPiece};
    	
    	return array;
    }

    public static void showReceivedData(byte bytes[]) {
        System.out.println("Data Received:");
        int lineCounter2 = 0;
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
            lineCounter2++;
            if (lineCounter2 % 10 == 0)
                sb.append("\n");
        }
        System.out.print(sb.toString());
    }
    
    public static long computeCRC32(byte bytes[]) {
        Checksum checksum = new CRC32();
        checksum.update(bytes, 0, bytes.length);
        long checksumValue = checksum.getValue();

        return checksumValue;
    }
    
}
