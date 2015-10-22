#include <iostream>
#include <fstream>	//file directive

int main(){
	using namespace std;

	//create a ofstream variable called 'fout'
	ofstream fout("test.txt");
	
	//check if the file is successfully opened
	if (fout){
		cout << "Successfully opened the file(ofstream)" << endl;
	}
	
	for (int i = 1; i <= 100; i++){
		fout << i << endl;	//write in to the file
	}

	//optional close file
	fout.close();

	//create a ifstream variable called 'fin'
	ifstream fin("test.txt");
	int c;

	//check if the file is successfully opened
	if (fin){
		cout << "Successfully opened the file(ifstream)" << endl;
	}

	//method to print out the data in files
	while (fin >> c){
		if (c % 2 == 0){
			cout << c << "(even)" << endl;
		}
		else{
			cout << c << "(odd)" << endl;
		}
	}
	
	//optional close file
	fin.close();

	return 0;
}