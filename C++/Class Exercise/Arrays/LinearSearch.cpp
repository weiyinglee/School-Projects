#include <iostream>

int linearSearch(int value[], int size, int n){
	for(int i = 0; i < size; i++){
		if(value[i] == n){
			return i;
		}
	}
	return -1;
}

int main(){
	
	using namespace std;
	
	int values[5] = {5, 4, 3, 2, 19};
	int n = 19;
	
	cout << "index of " << n << " = "
		<< linearSearch(values, 5, n) << endl;
	
	return 0;
}