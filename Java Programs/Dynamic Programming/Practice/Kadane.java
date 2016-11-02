import java.util.*;
import java.lang.*;
import java.io.*;

public class Kadane {
	public static void main (String[] args) {
		//code
		Scanner scanner=new Scanner(System.in);
		// 1. LINEAR many (t) TEST CASES:
		int num=scanner.nextInt();
		// NOTE: matrix datatype (may not be int)
		int[][] testMatrix=matrixTestReader(scanner, num);
		scanner.close();

		for(int i=0; i<num; i++) {
			// Path stack
   		Deque<Integer> path=new ArrayDeque<Integer>();
		  System.out.println(kadane(testMatrix[i], path));
 		  System.out.println(path);
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
	
	private static int kadane(int[] x, Deque<Integer> path) {
	    int[]dp=new int[x.length]; // keep track of SP to each vertex
	    int[]parent=new int[x.length]; // parent pointer to reconstruct the path
	    
	    // initialization: SP with length 1:
	    for(int i=0; i<x.length; i++) {
	        dp[i]=x[i];
	        parent[i]=i;
	    }
	        
	    // Relaxation:
	    for(int i=1; i<x.length; i++) {
	        if(dp[i-1]+x[i]>dp[i]) {
	            dp[i]=dp[i-1]+x[i];
	            parent[i]=i-1;
	        }
	    }
	    
	    // path reconstruction
	    int max=max(dp);
	    int v=max;
	    while(parent[v]!=v) {
	      path.push(x[v]);
	      v=parent[v];
	    }
	    // the last vertex:
	    path.push(x[v]);
	    
	    return dp[max];
	}
	
	private static int max(int[] input) {
	    int max=0;
	    for(int i=1; i<input.length; i++) {
	        if(input[i]>input[max]) {
	            max=i; // update the max index
	        }
	    }
	    return max;
	}
}
