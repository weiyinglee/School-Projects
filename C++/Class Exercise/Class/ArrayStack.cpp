#include <iostream>
#include <vector>
#include "ArrayStack.h"

ArrayStack::ArrayStack(){
	top = -1;
}

void ArrayStack::push(int data){
	stack[++top] = data;
}

int ArrayStack::pop(){
	if(top > -1)
		return stack[top--];
	else
		return 0;
}

