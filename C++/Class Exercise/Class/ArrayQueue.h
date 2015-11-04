#ifndef ARRAYQUEUE_H
#define ARRAYQUEUE_H

class ArrayQueue{
	private:
		int queue[10];
		int rear;
		int front;
		int size;
		
	public:
		ArrayQueue();
		void enqueue(int data);
		int denqueue();
};

#endif