#include <iostream>
#include "ArrayStack.h"
#include "ArrayStack.cpp"
#include "ArrayQueue.cpp"

int main(){
	
	ArrayStack as;
	ArrayQueue aq;
	
	std::cout << "For stack: " << std::endl;
	for(int i = 0; i < 10; i++){
		as.push(i);
	}
	for(int i = 0; i < 10; i++){
		std::cout << "Index " << i << " is: " << as.pop() << std::endl; 
	}
	
	std::cout << "\n";
	
	std::cout << "For queue: " << std::endl;
	for(int i = 0; i < 10; i++){
		aq.enqueue(i);
	}
	for(int i = 0; i < 10; i++){
		std::cout << "Index " << i << " is: " << aq.denqueue() << std::endl; 
	}
	
	return 0;
}