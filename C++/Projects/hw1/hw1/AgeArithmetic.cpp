// WeiYing Lee
// Homework 1
// Completed 10/3/2015

#include <iostream>

int main(){

	using namespace std;

	const int MY_AGE = 22;

	int age = MY_AGE;					// stores my age
	int	ageSquared = MY_AGE * MY_AGE;	// stores my age squared
	int	ageHalved = MY_AGE / 2;			// stores half of my age

	// Have the program first print out a line showing each variable and its value
	cout << "age: " << age 
		 << ", ageSquared: " << ageSquared
		 << ", ageHalved: " << ageHalved
		 << endl;
	
	// Multiply age by ageHalved
	cout << "Multiply age by ageHalved: " << age * ageHalved << endl;

	// Divide ageSquared by ageHalved
	cout << "Divide ageSquared by ageHalved: " << ageSquared / ageHalved << endl;

	// Take the remainder of ageSquared divided by ageHalved
	cout << "The remainder of ageSquared divided by ageHalved: " << ageSquared % ageHalved << endl;

	// Substract ageHalved from age
	cout << "Substract ageHalved from age: " << age - ageHalved << endl;

	// Divide age by ageSquared
	cout << "Divide age by ageSquared: " << age / ageSquared << endl;

	// Add ageSquared to age
	cout << "Add ageSquared to age: " << age + ageSquared << endl;

	// Using floating point division, find the mean of age, ageSquared, and ageHalved
	cout << "The mean of age, ageSquared, and ageHalved: " << (double)(age + ageSquared + ageHalved) / 3 << endl;

	return 0;
}