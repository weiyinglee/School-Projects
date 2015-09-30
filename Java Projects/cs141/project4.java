package cs141;

import java.util.Random;

public class project4 {

    public static void main( String[] args ) throws Exception {

	tttBoard board = new tttBoard();

	compPlayer[] player = new compPlayer[2];

	Random R = new Random();

	int currentPlayer = R.nextInt(10) % 2;


	// Setup the game

	player[0] = new compPlayer( "Bob", 'X', board );

	player[1] = new compPlayer( "Alice", 'O', board );

	//board.clear();


	System.out.print( "New Game: " + player[ 0 ] +
 
			  " vs " + player[1] + "\n" );

	System.out.print( "          " +
 
			  player[ currentPlayer ] +
 
			  " goes first.\n\n" );


	// Play the game

	while ( ! board.winner() ) {
 
	    player[ currentPlayer ].move();

	    System.out.print( board + "\n\n" );

	    currentPlayer = ( currentPlayer + 1 ) % 2;

	}


	// Announce the winner

	if ( board.getWinnerMark() == player[0].getMark() ) {

	    System.out.print( player[0] + " Wins!\n\n" );

	} else if ( board.getWinnerMark() == player[1].getMark() ) {

	    System.out.print( player[1] + " Wins!\n\n" );

	} else {

	    System.out.print( "Tie Game.\n\n" );

	}

    }

}