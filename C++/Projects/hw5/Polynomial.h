#ifndef POLYNOMIAL_H
#define POLYNOMIAL_H
#include <string>
#include <iostream>
class PolyTester;

class Polynomial{
   friend class PolyTester;
private:
   double* coeff;
   int size;
public:
   Polynomial();
   Polynomial(double array[], int size);
   Polynomial(const Polynomial& other);
   Polynomial(int n);
   Polynomial(double n);
   Polynomial& operator=(const Polynomial& other);
   ~Polynomial();
   int getSize() const;
   int degree() const;
   std::string str() const;
   double solve(double x) const;
   double& operator[](int i);
   Polynomial operator+(const Polynomial& other) const;
   Polynomial operator-(const Polynomial& other) const;
   Polynomial operator*(const Polynomial& other) const;
   Polynomial operator*(double n) const;
   Polynomial& operator+=(const Polynomial& other);
   Polynomial& operator-=(const Polynomial& other);
   Polynomial& operator*=(const Polynomial& other);
   bool operator==(const Polynomial& other) const;
   bool operator!=(const Polynomial& other) const;
   friend std::ostream& operator<<(std::ostream& os, const Polynomial& 
polynomial);
};

#endif
