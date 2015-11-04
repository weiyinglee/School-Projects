//Worst case performance: O(n^2)
#include <iostream>
#include <vector>

std::vector<int> selectionSort(std::vector<int> vec){
	
	int i = 0, j = 0; //the loop values
	int minIndex; //first index contains the smallest value in the list
	int minValue; //smallest value in the list
	int temp; //value stored for swapping
	
	for(int i = 0; i < vec.size(); i++){
		minValue = vec[i];
		minIndex = i;
		for(int j = i; j < vec.size(); j++){
			if(minValue > vec[j]){
				minValue = vec[j];
				minIndex = j;
			}
		}
		//swap firstIndex value to the minIndex value
		temp = vec[i];
		vec[i] = minValue;
		vec[minIndex] = temp;
	}
	return vec;
}

int main(){
	
	int size;
	std::vector<int> vec;
	std::vector<int> sortVec;
	
	std::cout << "What is the size of data: ";
	std::cin >> size;
	while(size <= 0){
		std::cout << "Enter a positive/non-zero size: ";
		std::cin >> size;
	}
	
	for(int i = 0; i < size; i++){
		int num;
		std::cout << "Enter a value for index " << i << " : ";
		std::cin >> num;
		vec.push_back(num);
	}
	
	std::cout << "The list you've entered are: " << std::endl;
	for(int i = 0; i < size; i++){
		std::cout << vec[i] << " ";
	}
	std::cout << "\n";
	
	sortVec = selectionSort(vec);
	
	std::cout << "The list after selection sort are: " << std::endl;
	for(int i = 0; i < size; i++){
		std::cout << sortVec[i] << " ";
	}
	std::cout << "\n";
	
	return 0;
}