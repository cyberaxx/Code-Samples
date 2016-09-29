import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
	public static void main (String[] args) {
		//code
		Scanner scanner=new Scanner(System.in);

		// 1. LINEAR many (t) TEST CASES:
		int num=scanner.nextInt();
		// NOTE: matrix datatype (may not be int)
		int[][] testMatrix=matrixTestReader(scanner, num);
		
		for(int t=0; t<testMatrix.length; t++) {
		    if(isBst(testMatrix[t]))
		        System.out.println(1);
		    else
                System.out.println(0);		        
		}
	}
	
	// scanner and number of test cases:
	private static int[][] matrixTestReader(Scanner scanner, int num) {
	    // test case matrix: each test case is a row of the matrix
	    int[][] testMatrix=new int[num][];

	    int i=0;	    
	    // for each test case: 
	    while(i<num) {
	        // number of samples in the the test case instance:
	        int n=scanner.nextInt();
	        // test case instance vector
	        testMatrix[i]=new int[n];
	        
	        // populate the array associated with i-th test case:
	        int j=0;
	        while(j<n) {
	            testMatrix[i][j]=scanner.nextInt();
	            j++;
	        }
		
		// proceed to the next test case (if exists one)
	        i++;
	    }
	
	    return testMatrix;
	}	
	private static boolean isBst(int[] preorder) {
	    // instatiate a stack from Deque interface:
	    Deque<Integer> stack=new ArrayDeque<Integer>();
	    // initial boundary for the left child:
	    int root=Integer.MIN_VALUE;
	    
	    for(Integer key:preorder) {
	        if(key<=root) return false;
	        // each time we have turned right, pop the left subtree
	        // out of the stack and update the root (boundary on keys can th
	        // can be accpected by not violating bst property)
	        while(!stack.isEmpty() && key>stack.peek())
	            root=stack.pop();
	        stack.push(key);
	    }
	    
	    return true;
	}
}
