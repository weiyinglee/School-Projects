// WeiYing Lee
// Homework 2
// Completed 10/11/2015

#include <iostream>
#include <fstream>

int main(){

	using namespace std;

	int userInput;	//input positive and odd width of pattern

	cout << "Enter the width/height: ";
	cin >> userInput;

	while (userInput <= 0 || userInput % 2 == 0){
		cout << "Width/height must be positive and odd, try again: ";
		cin >> userInput;
	}
	
	//creating ofstream variables
	ofstream fdiamond("diamond.txt");
	ofstream fsquare("square.txt");
	ofstream fcross("cross.txt");

	char symbol = 'X';

	/* create diamond.txt pattern */
	int centerRow = userInput / 2 + 1;

	//top
	for (int row = 1; row <= centerRow; row++){
		
		int space = centerRow - row;
		int x = 0;

		for (int col = 1; col <= userInput; col++){
			
			int numOfSym = row * 2 - 1;

			if (space < col && x < numOfSym){
				fdiamond << symbol;
				x++;
			}
			else{
				fdiamond << " ";
			}

		}
		fdiamond << "\n";
	}
	//bottom
	for (int row = centerRow - 1; row >= 1; row--){

		int space = centerRow - row;
		int x = 0;

		for (int col = 1; col <= userInput; col++){
			
			int numOfSym = row * 2 - 1;

			if (space < col && x < numOfSym){
				fdiamond << symbol;
				x++;
			}
			else {
				fdiamond << " ";
			}
		}
		fdiamond << "\n";
	}


	/* create square.txt pattern */
	for (int row = 1; row <= userInput; row++){		
		//top & base
		if (row == 1 || row == userInput){
			for (int numOfSym = 1; numOfSym <= userInput; numOfSym++){
				fsquare << symbol;
			}
			fsquare << "\n";
		}
		//middle
		if (row > 1 && row < userInput){
			for (int numOfSym = 1; numOfSym <= userInput; numOfSym++){
				if (numOfSym == 1 || numOfSym == userInput){
					fsquare << symbol;
				}
				else{
					fsquare << " ";
				}
			}
			fsquare << "\n";
		}
	}

	/* create cross.txt pattern */
	//top
	for (int row = 1; row <= centerRow; row++){
		for (int col = 1; col <= userInput; col++){
			if (col == row || col == userInput - row + 1){
				fcross << symbol;
			}
			else{
				fcross << " ";
			}
		}
		fcross << "\n";
	}
	//bottom
	for (int row = centerRow - 1; row >= 1; row--){
		for (int col = 1; col <= userInput; col++){
			if (col == row || col == userInput - row + 1){
				fcross << symbol;
			}
			else{
				fcross << " ";
			}
		}
		fcross << "\n";
	}


	//close the ofstream
	fdiamond.close();
	fsquare.close();
	fcross.close();

	return 0;
}