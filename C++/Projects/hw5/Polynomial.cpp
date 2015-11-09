#include "Polynomial.h"
#include <sstream>
#include <cmath>

Polynomial::Polynomial() 
   : coeff(new double[1]), size(1)
{
   *coeff = 0;
}

Polynomial::Polynomial(double array[], int size) 
   : coeff(new double[size]), size(size)
{ 
   for (int i = 0; i < size; i++)
      coeff[i] = array[i];
}

Polynomial::Polynomial(const Polynomial& other) //Not sure, VS gives error
   : coeff(new double[other.size]), size(other.size)
//Copy constructor
{
   for (int i = 0; i < size; i++)
      coeff[i] = other.coeff[i];
}

Polynomial::Polynomial(int n)
   : coeff(new double[1]), size(1)
{
   *coeff = n;
//convert to polynomial;put int in first index
}

Polynomial::Polynomial(double n)
   : coeff(new double[1]), size(1)
{
   *coeff = n;
//convert to polynomial;put double in first index
}

Polynomial& Polynomial::operator=(const Polynomial& other)
{
   if (this == &other)
      return *this;
   else
   {
      if (size != other.size)
      {
         delete [] coeff;
         coeff = new double[other.size];
      }
      for (int i = 0; i < other.size; i++)
         coeff[i] = other.coeff[i];
	  size = other.size;
      return *this;
   }
//copy Polynomial
}

Polynomial::~Polynomial()
{
   delete [] coeff;
}


int Polynomial::getSize() const
{
   return size;
//get size
}

int Polynomial::degree() const
{
   int degree = 0;
   for (int i = 0; i < size; i++)
   {
      if (coeff[i] != 0)
         degree = i;
   }
   return degree;
//get last array index WRONG
//get highest index that contains number
}

std::string Polynomial::str() const
{
   std::stringstream s;
   for (int i = size - 1; i >= 0; i--){
	   if (i < degree() && coeff[i] != 0){
		   s << ((coeff[i] >= 0) ? " + " : " - ");
	   }
       if (i == degree() && coeff[i] < 0){
           s << "-";
       }else if(i == degree() && coeff[i] > 0){
		   s << "";
	   }
	   if (coeff[i] != 0){
	      if (i == 1){
			 if(coeff[i] != 1 && coeff[i] != -1)
				s << std::abs(coeff[i]) << "x";
			 else
				s << "x";
	      }else if (i == 0){
	         s << std::abs(coeff[i]);
	      }else{
			  if(coeff[i] != 1 && coeff[i] != -1)
				s << std::abs(coeff[i]) << "x^" << i;
			  else
				s << "x^" << i;
          }
	   }
   }
   return s.str();
}

double Polynomial::solve(double x) const
{
   double answer = 0;
   for (int i = 0; i < size; i++)
      answer += coeff[i] * std::pow(x, i);
   return answer;
   //return answer as x is plugged
}

double& Polynomial::operator[](int n)
{
   if (n > size - 1)
   {
      double* coeff2 = new double[n + 1];
      for (int i = 0; i < n + 1; i++)
      {
         if (i >= size)
            coeff2[i] = 0;
         else
            coeff2[i] = coeff[i];
      }
      delete [] coeff;
      coeff = new double[n + 1];
      for (int i = 0; i < n + 1; i++)
      {
         coeff[i] = coeff2[i];
      }
      size = n + 1;
      delete [] coeff2;
      return coeff[n];
   }
   else
      return coeff[n];
   //modify array contents
   //return coeff[i];
}

Polynomial Polynomial::operator+(const Polynomial& other) const
{
	Polynomial result;
	
	if(size >= other.size){
		for(int i = 0; i < size; i++){
			if(i < other.size){
				result[i] = coeff[i] + other.coeff[i];
			}else{
				result[i] = coeff[i];
			}
		}
	}else{
		for(int i = 0; i < other.size; i++){
			if(i < size){
				result[i] = coeff[i] + other.coeff[i];
			}else{
				result[i] = other.coeff[i];
			}
		}
	}
	return result;
}

Polynomial Polynomial::operator-(const Polynomial& other) const
{
	Polynomial result;
	
	if(size >= other.size){
		for(int i = 0; i < size; i++){
			if(i < other.size){
				result[i] = coeff[i] - other.coeff[i];
			}else{
				result[i] = coeff[i];
			}
		}
	}else{
		for(int i = 0; i < other.size; i++){
			if(i < size){
				result[i] = coeff[i] - other.coeff[i];
			}else{
				result[i] = -(other.coeff[i]);
			}
		}
	}
	return result;
}

Polynomial Polynomial::operator*(const Polynomial& other) const
{
	Polynomial result;
	int n = (other.degree()) + degree();
	double c = 0;
	int i = 0;
	
	for(int left = 0; left < size; left++){
		for(int right = 0; right < other.size; right++){
			c = coeff[left] * other.coeff[right];
			i = left + right;
			result[i] += c;
		}
	}
	result.size = n + 1;
	return result;
}

Polynomial Polynomial::operator*(double n) const
{	
	Polynomial result;
	
	for(int i = 0; i < size; i++){
		result[i] = coeff[i] * n;
	}
	return result;
}

Polynomial& Polynomial::operator+=(const Polynomial& other)
{
	return (*this) = (*this) + other;
}

Polynomial& Polynomial::operator-=(const Polynomial& other)
{
	return (*this) = (*this) - other;
}

Polynomial& Polynomial::operator*=(const Polynomial& other)
{
	return (*this) = (*this) * other;
}


bool Polynomial::operator==(const Polynomial& other) const
{
	for(int i = 0; i < size; i++){
		if(coeff[i] != other.coeff[i]){
			return false;
		}
	}
	return true;
}

bool Polynomial::operator!=(const Polynomial& other) const
{
	return !(operator==(other));
}

std::ostream& operator<<(std::ostream& os, const Polynomial& polynomial)
{
	os << polynomial.str();
	return os;
}

