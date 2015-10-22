// WeiYing Lee
// Homework 3
// Completed on 10/17/15

#include <iostream>
#include <math.h>

// function prototypes
double sum(double[], int);
double mean(double[], int);
double stdDev(double[], int);

int main(){

	using namespace std;
	
	int size = 10; //array size
	double values[size];
	
	// Ask users to enter fill in the array
	for(int i = 0; i < size; i++){
		cout << "Enter the value for index [" << i << "]: ";
		cin >> values[i];
	}
	
	//print out the standard deviation
	cout << "The standard deviation of the array is " << stdDev(values, 10) << endl;
	
	return 0;
}

double sum(double values[], int size){
	
	double result = 0;
	
	for(int i = 0; i < size; i++){
		result += values[i];
	}
	
	return result;
}

double mean(double values[], int size){
	return sum(values, size) / size;
}

double stdDev(double values[], int size){
	
	double meanForValues = mean(values, size); // the mean of all of the values
	double sqrDev[size]; // Squared deviations array
	double meanForSqrDev; // The mean of the squared deviation
	
	// Find all the squared deviations
	for(int i = 0; i < size; i++){
		sqrDev[i] = pow((values[i] - meanForValues), 2);
	}
	
	meanForSqrDev = mean(sqrDev, size);
	
	return sqrt(meanForSqrDev);
}
