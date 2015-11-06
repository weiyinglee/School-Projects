#include <iostream>
#include <vector>

std::vector<int> insertionSort(std::vector<int> vec){
	
	int i, j; //outer and inner loop
	int key; //the current value
	
	for(int i = 1; i < vec.size(); i++){ //outer loop starts at 1 since 0 is considered already sorted
		
		key = vec[i];
		
		for(int j = i - 1; j >= 0; j--){ //inner loop starts at i - 1 and keep checking until 0
			if(key < vec[j]){
				//swap
				vec[j + 1] = vec[j];
				vec[j] = key;
				key = vec[j];
			}else{
				//stop inner loop
				break;
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
	
	sortVec = insertionSort(vec);
	
	std::cout << "The list after insertion sort are: " << std::endl;
	for(int i = 0; i < size; i++){
		std::cout << sortVec[i] << " ";
	}
	std::cout << "\n";
	
	return 0;
}