#include <iostream>

void swap(int&, int&);
void swap(int*, int*);

int main(){
	
	using namespace std;
	
	int x = 5;
	int y = 10;
	
	cout << "x is " << x << " and y is " << y << endl;
	
	swap(x, y);
	
	cout << "After reference swap: x is " << x << " and y is " << y << endl;

	cout << "Now x is " << x << " and y is " << y << endl; 
	swap(&x, &y);
	
	cout << "After pointer swap: x is " << x << " and y is " << y << endl; 
	
	return 0;
}

void swap(int &x, int &y){
	int swapV = x;
	x = y;
	y = swapV;
}

void swap(int* x, int* y){
	int swapV = *x;
	*x = *y;
	*y = swapV;
}