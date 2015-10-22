#include <iostream>

//function prototype here
int pow(int, int);
int getStatic();
void modifyX(int&);
int getPositiveInt();
int getNegativeInt();
int fibonacci(int);

int main(){

	using namespace std;

	cout << "getPosivtiveInt and getNegativeInt exercise: " << endl;

	int pos = getPositiveInt();
	int neg = getNegativeInt();

	cout << pos << " is positive" << endl;
	cout << neg << " is negative" << endl;

	cout << "\n";
	cout << "\n";
	
	cout << "getStatic exercise" << endl;

	int x = 2;
	for (int y = 0; y <= 10; y++){
		cout << x << " ^ " << y << " = " << pow(2, y) << endl;
	}
	
	for (int i = 0; i < 10; i++){
		cout << getStatic() << endl;
	}

	cout << "\n";
	cout << "\n";

	int n = 5;
	cout << "Fibonacci exercise: " << endl;
	cout << n << " is the term, the answer is: " << fibonacci(n) << endl;
	
	cout << "\n";
	cout << "\n";

	cout << "pass by reference exercise: " << endl;
	
	cout << n << endl;
	modifyX(n);	//the n will be affected by this function, normally not
	cout << n << endl;

	return 0;
}

// default initization, if variables are defined then use that, if not then use default value
int pow(int x = 2, int y = 3){
	
	int result = 1;

	for (int i = 0; i < y; i++){
		result *= x;
	}

	return result;
}

int getStatic(){
	
	static int x = 0; //initialization only happens once
	
	return ++x;
}

/*
	Now this function can affect the caller, it passes back the reference value
	cannot pass a constant value to the parameter, it would be an error
*/
void modifyX(int& x){
	x += x;	// normally this wouldn't affect the caller
}

int getPositiveInt(){
	
	using namespace std;

	int n;

	cout << "Enter a positive integer: ";
	cin >> n;

	while (n < 0){
		cout << "It's not positive, try again! : ";
		cin >> n;
	}

	return n;
}

int getNegativeInt(){
	
	using namespace std;

	int n;

	cout << "Enter a negative integer: ";
	cin >> n;

	while (n > 0){
		cout << "It's not negative, try again! : ";
		cin >> n;
	}

	return n;
}

int fibonacci(int n){

	if (n == 0 || n == 1){
		return 1;
	}
	return fibonacci(n - 1) + fibonacci(n - 2);
}