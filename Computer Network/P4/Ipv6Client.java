import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;

public class Ipv6Client {

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

	public static byte[] generateHeader(Socket socket, byte[] data) {

		byte[] headers = new byte[40];

		//1.Version (4 bits)
		byte version = (byte)(6 << 4);

		//2.Traffic Class (8 bits)
		byte trafficClass = (byte)0;
		byte tcFirstHalf = (byte)(trafficClass >> 4);
		byte tcSecHalf = (byte)(trafficClass << 4);

		headers[0] = (byte)(version | tcFirstHalf);

		//3.Flow Label (20 bits)
		//0000 00000000 00000000
		byte flowLabel = (byte)0;

		headers[1] = (byte)(tcSecHalf | flowLabel);
		headers[2] = flowLabel;
		headers[3] = flowLabel;
	
		//4.Payload Length (16 bits)
		short payloadLen = (short)data.length;
		
		byte payloadLenFirstHalf = (byte)(payloadLen >> 8);
		byte payloadLenSecHalf = (byte)(payloadLen);

		headers[4] = payloadLenFirstHalf;
		headers[5] = payloadLenSecHalf;

		//5.Next Header (8 bits)
		byte nextHeader = (byte)17;

		headers[6] = nextHeader;

		//6.Hop Limit (8 bits)
		byte hopLimit = (byte)20;
		headers[7] = hopLimit;

		//7.Source Address (128 bits)
		byte[] sourceAddr = socket.getLocalAddress().getAddress();
		for(int i = 0; i < 10; i++){
			headers[i + 8] = (byte)0;
		}
		for(int i = 0; i < 2; i++){
			headers[i + 18] = (byte)0xFF;
		}
		headers[20] = sourceAddr[0];
		headers[21] = sourceAddr[1];
		headers[22] = sourceAddr[2];
		headers[23] = sourceAddr[3];

		//8.Destination Address (128 bits)
		byte[] desAddr = socket.getInetAddress().getAddress();
		for(int i = 0; i < 10; i++){
			headers[i + 24] = (byte)0;
		}
		for(int i = 0; i < 2; i++){
			headers[i + 34] = (byte)0xFF;
		}
		headers[36] = desAddr[0];
		headers[37] = desAddr[1];
		headers[38] = desAddr[2];
		headers[39] = desAddr[3];

		return headers;

	}

	public static void main(String[] args) throws Exception {
		try(Socket socket = new Socket("codebank.xyz", 38004)){
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
				System.out.println("data length: " + size);

				System.out.print("Response: 0x");
				for(int k = 0; k < 4; k++){
					System.out.print(String.format("%02X", (byte)is.read()));
				}
				System.out.println("\n");
				size *= 2;
			}
		}
	}
}