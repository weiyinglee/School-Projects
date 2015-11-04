import java.util.Scanner;

public class BinarySearch {
	
	public static void main(String[] args){
		
		Scanner keyboard = new Scanner(System.in);
		
		int array[] = {80, 85, 91, 105, 207, 535};
		int searchNumber;
		
		System.out.print("The given array contains: ");
		for(int elements: array){
			System.out.print(elements + " ");
		}
		System.out.print("\n");
		
		System.out.print("Enter a number you would like to search: ");
		searchNumber = keyboard.nextInt();
		
		System.out.println("\n" + searchNumber + " is at index of " + search(array, 0, array.length - 1, searchNumber));
	}
	
	public static int search(int a[], int firstIndex, int size, int element){
		
		int middleIndex = firstIndex + size/ 2;
		
		if(size <= 0){
			return -1;
		}else{
			if(element == a[middleIndex]){
				return middleIndex;
			}else if(element < a[middleIndex]){
				return search(a, firstIndex, size / 2, element);
			}else{
				return search(a, middleIndex + 1, (size - 1) / 2, element);
			}
		}
		
	
	}
	
}
