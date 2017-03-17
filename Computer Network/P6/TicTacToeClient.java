import java.net.Socket;
import java.util.Scanner;
import java.io.*;

public class TicTacToeClient {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Scanner keyboard;

	public TicTacToeClient(Socket socket){
		try{
			this.socket = socket;
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			keyboard = new Scanner(System.in);
		}catch(IOException e){
			System.out.println(e);
		}
	}

	public void userConnection() throws IOException{
		System.out.print("Enter your name: ");
		ConnectMessage connnectMessage = new ConnectMessage(keyboard.nextLine());
		out.writeObject(connnectMessage);
		System.out.println("User Connected: " + connnectMessage.getUser());
	}

	public void gameStart() throws IOException{
		CommandMessage commandMessage = new CommandMessage(CommandMessage.Command.NEW_GAME);
		out.writeObject(commandMessage);
		System.out.println("Start the game.");
	}

	public void gameProcess() throws IOException, ClassNotFoundException{
		while(true){
			Message message = (Message)in.readObject();
			MessageType response = message.getType();
			if(response == MessageType.ERROR){
				System.out.println(((ErrorMessage)message).getError());
				System.out.println("Please select another move!");
				setMove();
			}else{
				BoardMessage boardMessage = (BoardMessage)message;
				byte[][] board = boardMessage.getBoard();
				printBoard(board);
				//Determine the game
				if(boardMessage.getStatus() == BoardMessage.Status.IN_PROGRESS){
					//send movemessage to server
					setMove();
				}else if(boardMessage.getStatus() == BoardMessage.Status.PLAYER1_VICTORY){
					System.out.println("Player 1 won!");
					break;
				}else if(boardMessage.getStatus() == BoardMessage.Status.PLAYER2_VICTORY){
					System.out.println("Player 2 won!");
					break;
				}else if(boardMessage.getStatus() == BoardMessage.Status.STALEMATE){
					System.out.println("Players tied");
					break;
				}else{
					System.out.println("Something happened");
					break;
				}
			}
		}			
	}

	public void printBoard(byte[][] board){
		System.out.println("--------------");
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				char symbol = ' ';
				if(board[row][col] != (byte)0){
					symbol = board[row][col] == (byte)1 ? 'X' : 'O';
				}
				System.out.print(" " + symbol + " |");
			}
			System.out.print("\n");
			System.out.println("---------------");
		}		
	}

	public void setMove() throws IOException{
		System.out.println("Enter your move");
		System.out.print("Row: ");
		byte row = keyboard.nextByte();
		System.out.print("Col: ");
		byte col = keyboard.nextByte();
		out.writeObject(new MoveMessage(row, col));		
	}

	public static void main(String args[]) throws Exception {
		try(Socket socket = new Socket("codebank.xyz", 38006)){
			TicTacToeClient ttt = new TicTacToeClient(socket);
			//Identify self
			ttt.userConnection();
			//Send command to server to start new game
			ttt.gameStart();
			//playing game
			ttt.gameProcess();
		}
	}
}