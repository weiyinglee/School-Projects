#ifndef ARRAYSTACK_H
#define ARRAYSTACK_H

class ArrayStack{
	private:
		int top;
		int stack[10];
		
	public:
		ArrayStack();
		void push(int data);
		int pop();
};

#endif