//Authors: WeiYing Lee & Max Y. Kim
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;

/*
 * Important notes from Professor Pantic:
 * Because short and int have different size, when the JVM performs the addition, it must
 * expand short variable to an int because JVM can't add two variables of different sizes.
 * Because short and int are signed types, when it expands short variable to an int, it performs
 * a sign extension. Therefore, whenever the leftmost bit of a short variable that is being added
 * is 1, it is interpreted as a negative numberr and gets subracted. We must force short variable
 * to be zero extended instead of sign extended when we perform the addtion.
 * ex)  int int1 = 0;
 *		short short1= -32767;
 *		System.out.println(int1+short1); // prints -32767
 *		System.out.println(int1 + (((int) short1) & 0x0000FFFF));   // prints 32767
 */

public class Ex3Client {

	/* checksum implementation follow by the algorithm */
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

	/* helper method: to show the received data */
	public static void showReceivedData(byte bytes[]) {
		System.out.println("Data Received:");
		int lineCounter = 0;
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X", b));
			lineCounter++;
			if (lineCounter % 10 == 0)
				sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	/* helper method: to make the checksum as a sequence of two bytes */
	public static byte[] generateChecksum(short value) {
		// short is 16 bits
		byte firstPiece = (byte) (value >> 8);
		byte secPiece = (byte) value;
		byte[] array = { firstPiece, secPiece };

		return array;
	}

	public static void main(String[] args) throws Exception {
		// using port 38203 instead of 38103 for now for easier debugging
		// process
		try (Socket socket = new Socket("codebank.xyz", 38103)) {

			byte[] bytePackage; // contains the total bytes
			short checksumValue; // the calculated checksum value
			int numOfBytes; // number of bytes that server is going to send

			// Show connection message
			System.out.println("Connected to server.");

			// Get single byte of data (number of next bytes) from server
			InputStream is = socket.getInputStream();
			numOfBytes = is.read();

			// Show reading bytes
			System.out.println("Reading " + numOfBytes + " bytes.");

			// Get the next bytes from server and store in the array
			bytePackage = new byte[numOfBytes];

			for (int i = 0; i < numOfBytes; i++) {
				bytePackage[i] = (byte) is.read();
			}

			// Show data received
			showReceivedData(bytePackage);

			// Pass the bytes to checksum method to calculate the correct
			// checksum
			checksumValue = checksum(bytePackage);

			// Show the checksum calculated
			System.out.println("Checksum calculated: " + Integer.toHexString(checksumValue));

			// make checksum as a sequence of two bytes for the purpose of
			// sending to server
			byte[] request = generateChecksum(checksumValue);

			// Send the checksum as a sequence of two bytes to the server
			OutputStream os = socket.getOutputStream();
			os.write(request);

			// Get final response of 0 or 1 from server
			if ((byte) is.read() == 1) {
				System.out.println("Response good.");
			} else {
				System.out.println("Response bad.");
			}

		}
	}

}
