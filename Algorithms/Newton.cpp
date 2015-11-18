#include <iostream>
#include <cmath>

//helper function
double originFunctions(double x){
	return pow(x - 30, 2) * pow(x - 60, 2) * (pow(x, 2) - 2) - 10;
}

double devFunctions(double x){
	return (2 * x) * (x - 30) * (x - 60) * (3 * pow(x, 3) - 180 * pow(x, 2) + 1796 * x + 180);
}

double newton(double x, int n, double fx, double dfx){
	
	double xnext = x - fx / dfx;
	
	std::cout << "x" << n << " = " << x << std::endl;
	
	if(std::abs(x - xnext) > 10E-8){
		if(std::abs(dfx) < 10E-5){
			std::cout << "WARNING: f'(x) is within 10^-5 of zero" << std::endl;
			return x;
		}
		return newton(xnext, ++n, originFunctions(xnext), devFunctions(xnext));
	}
	
	return x;
}