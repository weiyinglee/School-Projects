#include <iostream>

double prodSumRows(double numbers[][4], int rows){
	
	double result = 1;
	
	for(int r = 0; r < rows; r++){
		
		double sum = 0;
		
		for(int c = 0; c < 4; c++){
			sum += numbers[r][c];
		}
		result *= sum;
	}
	
	return result;
	
}

int main(){
	
	using namespace std;
	double value[3][4] = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9,10, 11, 12}};
	
	cout << "The product of sum for 3 rows is: " << prodSumRows(value, 3) << endl;
	return 0;
} 