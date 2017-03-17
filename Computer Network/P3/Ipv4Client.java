import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Ipv4Client {

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

	public static byte[] generateData(byte[] header, byte[] data) {

		byte[] array = new byte[header.length + data.length];

		//generate each header field to its own desire bits length
		int index = 0;
		for(int i = 0; i < array.length; i++){
			if(i < header.length){
				array[i] = header[i];
			}else{
				array[i] = data[index];
				index++;
			}
			
		}

		return array;
	}

	public static byte[] generateHeader(Socket socket, byte[] data){

		byte[] headers = new byte[20];

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
		byte protocol = (byte)6;
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
		int sourceAddr = 0;
		byte sourceAddr1 = (byte)(sourceAddr >> 24);
		byte sourceAddr2 = (byte)(sourceAddr >> 16);
		byte sourceAddr3 = (byte)(sourceAddr >> 8);
		byte sourceAddr4 = (byte)(sourceAddr);

 		headers[12] = sourceAddr1;
 		headers[13] = sourceAddr2;
 		headers[14] = sourceAddr3;
 		headers[15] = sourceAddr4;

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

		return headers;

	}

	public static void main(String[] args) throws Exception{
		try(Socket socket = new Socket("codebank.xyz", 38003)){
			int size = 2;
			for(int i = 0; i < 12; i++){

				//implement different header fields (160 bits of header fields plus data)
				
				byte[] data = new byte[size];
				for(int j = 0; j < data.length; j++){
					//put all zero in the data
					data[j] = 2;
				}

				byte[] headers = generateHeader(socket, data);
				//generate header
				byte[] request = generateData(headers, data);

				//send server
				OutputStream os = socket.getOutputStream();
				os.write(request);

				//response
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				System.out.println("data length: " + size);
				System.out.println(br.readLine() + "\n");

				size *= 2;

			}

		}
	}
}