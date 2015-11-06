//worst case performance: O(n^2)
#include <iostream>
#include <vector>

std::vector<int> bubbleSort(std::vector<int> vec){
	
	int i, j; //outer and inner loop
	int temp; //variable for swapping
	
	for(int i = 0; i < vec.size() - 1; i++){ //because we compare as pair a time, the iteration will be shorten 1
		for(int j = 0; j < vec.size() - 1 - i; j++){ //exclude the index that already sorted, so subtract i 
			if(vec[j] > vec[j + 1]){
				//swap
				temp = vec[j];
				vec[j] = vec[j + 1];
				vec[j + 1] = temp;
			}
		}
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
	
	sortVec = bubbleSort(vec);
	
	std::cout << "The list after bubble sort are: " << std::endl;
	for(int i = 0; i < size; i++){
		std::cout << sortVec[i] << " ";
	}
	std::cout << "\n";
	
	return 0;
}