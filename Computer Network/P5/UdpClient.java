import java.net.Socket;
import java.io.*;
import java.util.Random;
import java.text.DecimalFormat;

public class UdpClient {

	public static short checksum(byte[] b) {

		int sum = 0; // 32 bits number as the sum

		// go through the byte array two bytes at a time to form 16 bits number
		// and add to the sum
		int index = 0;
		while (index < b.length) {

			byte firstHalf, secHalf, shiftByte;
			short wholeByte = 0; // 16 bits to add to sum

			// if the elements of array is odd
			if (index + 1 == b.length) {
				// casting to short with & 0x00FF prevents from signed
				// extension.
				wholeByte |= (((short) b[index]) & 0x00FF);
				wholeByte = (short) (wholeByte << 8);
			} else {
				firstHalf = b[index];
				secHalf = b[index + 1];

				// form 16 bits.
				wholeByte |= (((short) firstHalf) & 0x00FF);
				// casting to short with & 0x00FF prevents from signed
				// extension.
				wholeByte = (short) ((wholeByte << 8) | (((short) secHalf) & 0x00FF));
			}

			// sum += wholeBytes;
			sum += (((int) wholeByte) & 0x0000FFFF);

			// each 0's in if statement becomes 0000 in hex form.
			if ((sum & 0xFFFF0000) != 0) {
				// carry occurred. so wrap around.
				sum &= 0xFFFF;
				sum++;
			}

			index += 2;
		}

		// ~ = bitwise negation
		return (short) ~(sum & 0xFFFF);
	}

	public static byte[] ipv4Packet(Socket socket, byte[] data){
		byte[] headers = new byte[20];

		byte[] result = new byte[headers.length + data.length];

		//1.Version (4 bits)
		byte version = (byte)(4 << 4);
		//2.HLen (4 bits)
		byte hLen = (byte)5;

		headers[0] = (byte)(version | hLen);

		//3.TOS (8 bits)
		byte tos = (byte)0;
		headers[1] = tos;
		
		//4. length (16 bits)
		short length = (short)(data.length + 20);

		byte len1 = (byte)(length >> 8);
		headers[2] = len1;

		byte len2 = (byte)length;
		headers[3] = len2;

		//5.Ident (16 bits)
		short ident = (short)0;

		byte ident1 = (byte)0;
		byte ident2 = (byte)0;

		headers[4] = ident1;
		headers[5] = ident2;

		//6.Flags (3 bits) & 7. Offset (12 bits)
		//01000000 00000000
		short flagNoffset = (short)(2 << 13);

		byte flagNoffset1 = (byte)(flagNoffset >> 8);
		byte flagNoffset2 = (byte)flagNoffset;

		headers[6] = flagNoffset1;
		headers[7] = flagNoffset2;

		//8.TTL (16 bits)
		byte ttl = (byte)50;

		headers[8] = ttl;	

		//9.Protocol (8 bits)
		byte protocol = (byte)17;
		headers[9] = protocol;

		// set checksum to zero for now. make it zero.
		// after you initialize everything in the header, then
		// then you make your checksum. checksum needs to check
		// everything in your header.
		//10.Checksum (16 bits)
		short checksumValue = 0;
		headers[10] = 0;
		headers[11] = 0;

		//11.SourceAddr (32 bits)
		byte[] sourceAddr = socket.getLocalAddress().getAddress();

		headers[12] = sourceAddr[0];
		headers[13] = sourceAddr[1];
		headers[14] = sourceAddr[2];
		headers[15] = sourceAddr[3];

		//12.DestionationAddr (32 bits)
		byte[] desAddr = socket.getInetAddress().getAddress();

		headers[16] = desAddr[0];
		headers[17] = desAddr[1];
		headers[18] = desAddr[2];
		headers[19] = desAddr[3];

		// do checksum here--------------------------
		checksumValue = checksum(headers);
		byte checksum1 = (byte)(checksumValue >> 8);
		byte checksum2 = (byte)checksumValue;
		headers[10] = checksum1;
		headers[11] = checksum2;

		//generate each header field to its own desire bits length
		int index = 0;
		for(int i = 0; i < result.length; i++){
			if(i < headers.length){
				result[i] = headers[i];
			}else{
				result[i] = data[index];
				index++;
			}
			
		}

		return result;
	}

	public static byte[] pseudoHeader(Socket socket, byte[] udpPacket){
		byte[] result = new byte[12 + udpPacket.length];
		
		//Ipv4 Pseudo header
		//1. source IPv4 addr
		byte[] sourceAddr = socket.getLocalAddress().getAddress();
		result[0] = sourceAddr[0];
		result[1] = sourceAddr[1];
		result[2] = sourceAddr[2];
		result[3] = sourceAddr[3];

		//2. destination ipv4 addr
		byte[] desAddr = socket.getInetAddress().getAddress();
		result[4] = desAddr[0];
		result[5] = desAddr[1];
		result[6] = desAddr[2];
		result[7] = desAddr[3];

		//3. reserved: put zeros(8 bits)
		result[8] = (byte)0;

		//4. Protocol
		result[9] = (byte)17;

		//5. UDP Length
		short length = (short)udpPacket.length;
		result[10] = (byte)(length >> 8);
		result[11] = (byte)(length);

		//Add the UDP packet
		int index = 0;
		for(int i = 0; i < result.length; i++){
			if(i >= 12){
				result[i] = udpPacket[index];
				index++;
			}
		}

		return result;
	}

	public static byte[] udpPacket(Socket socket, short srcPort, short dstPort, byte[] data){

		byte[] result = new byte[8 + data.length];
		
		//headers
		//1. srcPort
		result[0] = (byte)(srcPort >> 8);
		result[1] = (byte)(srcPort);

		//2. dstPort
		result[2] = (byte)(dstPort >> 8);
		result[3] = (byte)(dstPort);

		//3. length
		short length = (short)result.length;
		result[4] = (byte)(length >> 8);
		result[5] = (byte)(length);

		//4. checksum
		//put 0 first in header checksum
		result[6] = 0;
		result[7] = 0;

		//data field
		int index = 0;
		for(int i = 0; i < result.length; i++){
			if(i >= 8){
				result[i] = data[index];
				index++;
			}
		}

		//put the checksum back to header
		byte[] checksumHeader = pseudoHeader(socket, result);
		short checksumValue = checksum(checksumHeader);
		result[6] = (byte)(checksumValue >> 8);
		result[7] = (byte)checksumValue;

		return result;

	}

	public static double avgRTT(long[] time){
		long total = 0;
		for(int i = 0; i < time.length; i++){
			total += time[i];
		}
		return (double)(total) / time.length;
	}

	public static void main(String[] args) throws Exception {
		try(Socket socket = new Socket("codebank.xyz", 38005)){
			
			//handshaking
			byte[] data = new byte[4];
			data[0] = (byte)0xDE;
			data[1] = (byte)0xAD;
			data[2] = (byte)0xBE;
			data[3] = (byte)0xEF;

			byte[] ipv4 = ipv4Packet(socket, data);

			OutputStream os = socket.getOutputStream();
			os.write(ipv4);

			InputStream is = socket.getInputStream();
			System.out.print("Handshake response: 0x");
			for(int i = 0; i < 4; i++){
				System.out.print(String.format("%02X", (byte)is.read()));
			}
			
			System.out.print("\n");

			int port1 = (is.read() << 8),
				  port2 = is.read();
			int portNum = (port1 | port2);

			System.out.println(Integer.toBinaryString(port1));
			System.out.println(Integer.toBinaryString(port2));
			System.out.println(Integer.toBinaryString(portNum));

			System.out.print("Port number received: " + (portNum & 0xFFFF));
			System.out.print("\n\n");

			//Send data package
			Random random = new Random();

			long[] rttTol = new long[12];

			int size = 2;
			for(int i = 0; i < 12; i++){
				byte[] data1 = new byte[size];
				
				random.nextBytes(data1);

				byte[] udp = udpPacket(socket, (short)0, (short)portNum, data1);

				//put udp and ipv4 together
				byte[] request = ipv4Packet(socket, udp);

				long startTime = System.currentTimeMillis();

				//send server
				os.write(request);

				System.out.println("Sending packet with " + size + " bytes of data");
				System.out.print("Response: 0x");
				for(int j = 0; j < 4; j++){
					System.out.print(String.format("%02X", (byte)is.read()));
				}

				System.out.print("\n");

				//RTT
				rttTol[i] = System.currentTimeMillis() - startTime;
				System.out.println("RTT: " + rttTol[i] + "ms");
				System.out.print("\n");
				
				size *= 2;
			}

			System.out.println("Average RTT: " + new DecimalFormat("#.##").format(avgRTT(rttTol)) + "ms");
		}
	}
}