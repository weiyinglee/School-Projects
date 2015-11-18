#include <iostream>
#include <cmath>

double bisection(double a, double b){
	
	double fa = pow(a - 30, 2) * pow(a - 60, 2) * (pow(a, 2) - 2) - 10;
	double c = (a + b) / 2;
	double fc = pow(c - 30, 2) * pow(c - 60, 2) * (pow(c, 2) - 2) - 10;
	
	std::cout << "a = " << a << std::endl;
	std::cout << "b = " << b << std::endl;
	std::cout << "c = " << c << std::endl;
	std::cout << "f(a) = " << fa << std::endl;
	std::cout << "f(c) = " << fc << std::endl;
	std::cout << "\n";
	
	if(std::abs(b - a) > 1){
		if(fa * fc < 0){
			b = c;
		}else{
			a = c;
		}
		return bisection(a, b);
	}
	
	return c;
}

int main(){
	
	std::cout << "Root is " << bisection(0, 100) << " by the Bisection method" << std::endl;
	
	return 0;
}