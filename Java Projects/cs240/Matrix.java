package cs240;

/*
 *  Name:		Lee, Weiying
 *  Homework:	#1
 *  Due:		Jan.27 2015
 *  Course:		cs-240-02-w15
 *  
 *  Description:
 *  			Add two class methods to the Matrix class,
 *  			1. multiplication: A = B * C and A = c * B
 *  			2. addition: A = B + C
 *  			3. Throw invalid dimensions as IllegalArgumentException
 *  			4. Provide a class method main to verify 1, 2, and 3.
 */

public class Matrix {
    
    public static int[][] transpose (int[][] matrix) {
        int[][] transposeMatrix = new int[matrix[0].length][matrix.length];

        for (int i = 0; i < transposeMatrix.length; i++) {
            for (int j = 0; j < transposeMatrix[i].length; j++) {
                transposeMatrix[i][j] = matrix[j][i];
            }
        }        
        
        return transposeMatrix;
    }
    
    //multiplication method for multiplying two matrix together
    public static int[][] multiplication(int[][] A, int[][] B) {
    	int[][] matrix = new int[A.length][B[0].length];
    	
    	for(int x = 0; x < matrix[0].length; x++){
    		for(int r = 0; r < A.length; r++){
    			for(int c = 0; c < A[0].length; c++){
    				matrix[r][x] += A[r][c] * B[c][x];
    			}
    		}
    	}
    	
    	return matrix;
    }
    
    //Overloaded multiplication method for scalar * Matrix(different argument)
    public static int[][] multiplication(int[][] A, int c){
    	int[][] matrix = new int[A.length][A[0].length];
    	
    	for(int row = 0; row < matrix.length; row++){
    		for(int col = 0; col < matrix[0].length; col++){
    			matrix[row][col] = A[row][col] * c;
    		}
    	}
    	
    	return matrix;
    }
    
    //addition method for adding two matrix together
    public static int[][] addition(int[][] A, int[][] B) {
    	int[][] Matrix = new int[A.length][A[0].length];
    	
    	//Since the rule says you cannot add two matrices with different sizes
    	if(A.length == B.length && A[0].length == B[0].length){
    		for(int r = 0; r < Matrix.length; r++){
    			for(int c = 0; c < Matrix[0].length; c++){
    				Matrix[r][c] = A[r][c] + B[r][c];
    			}
    		}
    	}
    	else
    		//throw invalid dimensions as IllegalArgumentException
    		throw new IllegalArgumentException("Two matrices are not same sizes!");
    	
    	return Matrix;
    }
    
    public static int[][] identity (int n) {
        int[][] ident = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ident[i][i] = 1;
            }
        }
        
        return ident;
    }
    
    public static void printMatrix (String tag, int[][] matrix) {
        System.out.println(tag);
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.printf("%4d ", element);
            }
            System.out.println("");
        }
    }
    
    //class method called main to verify our result
    public static void main(){
    	//Example as one of the test cases
    	int[][] matrix1 = new int[][]{{1 ,3, 5, 7}, {2, 4, 6, 8}};
    	int[][] matrix2 = new int[][]{{1, 8, 9}, {2, 7, 10}, {3, 6, 11}, {4, 5, 12}};

    	printMatrix("Matrix 1:", matrix1);
    	printMatrix("Matrix 2:", matrix2);
    		
        printMatrix("The multiplication of two matrices:", multiplication(matrix1, matrix2));
        printMatrix("The multiplication of matrix 1 times 3:", multiplication(matrix1, 3));
        try{
        	printMatrix("The addition of two matrices:", addition(matrix1, matrix2));
        }catch(IllegalArgumentException ie){
        	System.out.println(ie);
        	System.out.println("Since matrix 1 and matrix 2 are not same size," +
        						" they cannot add each other,");
        	int[][] matrix3 = new int[][]{ {1, 2, 3, 4}, {5, 6, 7, 8} };
        	printMatrix("we try other matrix called matrix 3({{1, 2, 3, 4},{5 ,6, 7, 8}}):",
        					addition(matrix1, matrix3));
        }
    }
    
    public static void main(String[] args) {
        int[][] matrixa = new int[][]{ { 1 , 2, 3 }, { 4, 5, 6 } };
        printMatrix("matrix A:", matrixa);
        int[][] matrixb = transpose(matrixa);
        printMatrix("transpose A:", matrixb);
        
        main(); // Method that test our result
        
        int[][] ident = identity(5);
        printMatrix("identity:", ident);              
    }
}