#include "ArrayQueue.h"

ArrayQueue::ArrayQueue(){
	int rear = 0;
	int front = 0;
	int size = 0;
}

void ArrayQueue::enqueue(int data){
	queue[rear] = data;
	rear += 1;
	size++;
}

int ArrayQueue::denqueue(){
	int result;
	result = queue[front];
	front += 1;
	size--;
	return result;
}