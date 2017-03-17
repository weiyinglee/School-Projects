import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

public class PhysLayerClient {

	public static String getHalfByte(String[] array, String previous, int parts, int section){
		
		//array is the highs and lows
		//previous is the previous status(high/low)
		//parts is which bytes of 32 bytes we are looking at
		//section is which half we are looking at (first 5, or last 5)

		StringBuilder sb = new StringBuilder();
		for(int i = 0 + section; i < 5 + section; i++){
			String current = array[parts * 10 + i];
			if(current.equals(previous)){
				sb.append("0");
			}else{
				sb.append("1");
			}
			previous = current; 
		}
		return sb.toString();
	}

	public static String calculatePrevious(String[] array, int parts, int section){
		return array[parts * 10 + section];
	}

	public static byte table4B5B(String fiveBits){
		
		String resultBinary = null;
		int fourBits;

		switch(fiveBits){
			case "11110":
				resultBinary = "0000";
				break;
			case "01001":
				resultBinary = "0001";
				break;
			case "10100":
				resultBinary = "0010";
				break;
			case "10101":
				resultBinary = "0011";
				break;
			case "01010":
				resultBinary = "0100";
				break;
			case "01011":
				resultBinary = "0101";
				break;
			case "01110":
				resultBinary = "0110";
				break;
			case "01111":
				resultBinary = "0111";
				break;
			case "10010":
				resultBinary = "1000";
				break;
			case "10011":
				resultBinary = "1001";
				break;
			case "10110":
				resultBinary = "1010";
				break;
			case "10111":
				resultBinary = "1011";
				break;
			case "11010":
				resultBinary = "1100";
				break;
			case "11011":
				resultBinary = "1101";
				break;
			case "11100":
				resultBinary = "1110";
				break;
			case "11101":
				resultBinary = "1111";
				break;
			default:
				System.out.println("Error");
				fourBits = -1;
		}
		fourBits = Integer.parseInt(resultBinary, 2);
		return (byte)fourBits;
	}

	public static void showByte(byte[] bytes){
		System.out.print("Received 32 bytes: ");
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        System.out.print(sb.toString() + "\n");
	}

	public static double calculateBaseline(int[] preamble){

		double sum = 0;

		for(int i = 0; i < preamble.length; i++){
			sum += preamble[i];
		}

		return (sum / preamble.length);

	}
	
	public static void main(String args[]) throws Exception{
		try(Socket socket = new Socket("codebank.xyz", 38002)){

			int[] preamble = new int[64];
			double baseline;

			//Show server connect message
			System.out.println("Connected to server.");

			//Receive the preamble from server (64 bytes)
			InputStream is = socket.getInputStream();

			for(int i = 0; i < 64; i++){
				preamble[i] = is.read();
			}

			//get the baseline
			baseline = calculateBaseline(preamble);
			System.out.println("Baseline established from preamble: " + baseline);

			//decode the data and record the 32 bytes data from 320 bytes
			int byteCode[] = new int[320];

			for(int i = 0; i < 320; i++){
				byteCode[i] = is.read();
			}

			//Compare to baseline then get all highs and lows
			String[] signalStatus = new String[320];

			for(int i = 0; i < 320; i++){
				if(byteCode[i] >= baseline){
					signalStatus[i] = "HIGH";
				}else{
					signalStatus[i] = "LOW";
				}
			}

			//base on the highs and lows, form the 5 bits
			byte[] message = new byte[32];

			String previous = "LOW";

			for(int i = 0; i < 32; i++){
				String firstHalf = getHalfByte(signalStatus, previous, i, 0);
				previous = calculatePrevious(signalStatus, i, 4);
				String secHalf = getHalfByte(signalStatus, previous, i, 5);
				previous = calculatePrevious(signalStatus, i , 9);

				byte first = table4B5B(firstHalf);
				byte second = table4B5B(secHalf);

				byte shiftByte = (byte)(first << 4);
				byte wholeByte = (byte)(shiftByte | second);

				message[i] = wholeByte;
			}

			//show receive byte
			showByte(message);

			//send the 32 bytes back to server and check if it matchs the server
			OutputStream os = socket.getOutputStream();
			os.write(message);

			if((byte)is.read() == 1){
				System.out.println("Response good.");
			}else{
				System.out.println("Response bad.");
			}

			//Show server disconnect message
			System.out.println("Disconnected from server.");

		}
	}
}