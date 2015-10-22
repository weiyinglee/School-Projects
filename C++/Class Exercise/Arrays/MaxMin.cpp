#include <iostream>

//function prototypes
int max(int[], int);
int min(int[], int);

int max(int values[], int size){
	
	int result = values[0];
	
	for(int i = 1; i < size; i++){
		if(result < values[i]){
			result = values[i];
		}
	}
	return result;
}

int min(int values[], int size){
	
	int result = values[0];
	
	for(int i = 1; i < size; i++){
		if(result > values[i]){
			result = values[i];
		}
	}
	
	return result;
}

int main(){
	
	using namespace std;
	
	int value[5] = {5, 4, 3, 2, 1};
	cout << "max = " << max(value, 5) << endl;
	cout << "min = " << min(value, 5) << endl;
	return 0;
}