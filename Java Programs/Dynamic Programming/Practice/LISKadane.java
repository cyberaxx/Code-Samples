import java.util.*;
import java.lang.*;
import java.io.*;

/*
Maximum sum increasing subsequence
Given an array of n positive integers. 
Write a program to find the sum of maximum sum subsequence of 
the given array such that the integers in the subsequence 
are sorted in increasing order.
*/

public class LISKadane {
	public static void main (String[] args) {
		//code
		Scanner scanner=new Scanner(System.in);
    // 1. LINEAR many (t) TEST CASES:
		int num=scanner.nextInt();
		// NOTE: matrix datatype (may not be int)
		int[][] testMatrix=matrixTestReader(scanner, num);
	  scanner.close();
	  
		for(int i=0; i<num; i++) {
			// Path stack:
	    Deque<Integer> path=new ArrayDeque<Integer>(); // empty stack
		  System.out.println(dp(testMatrix[i], path));
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
	
	private static int dp(int[] x, Deque<Integer> path) {
	    int[]dp=new int[x.length]; // maintain sp to all vertices
	    int[] parent=new int[x.length]; // parent pointer
	    
	    // initialization:
	    for(int i=0; i<x.length; i++) {
	        dp[i]=x[i];
	        parent[i]=i;
	    }
	    
	    // Reapting relaxations (relax all implicit edges only once)
	    for(int i=1; i<x.length; i++) { // for all vertices in TOPOLOGICAL order
	        // for all possible edges incident to them
	        for(int j=0; j<i; j++) {
	            // if such a edge exists:
	            if(x[j]<x[i]) {
	                // relax:
	                if(dp[i]<dp[j]+x[i]) {
	                    dp[i]=dp[j]+x[i];
	                    parent[i]=j;
	                }
	            }
	        }
	    }
	    
	    // Compute the optimal solution for the original problem:
	    int max=max(dp);
	    
	    // path reconstruction:
	    int v=max;
	    while(v!=parent[v]) {
	      path.push(x[v]); // value at index v in the dag
	      v=parent[v]; // move towards S vertex
	    }
	    // add S to the path:
	    path.push(x[v]); // where v=parent[v]
	    return dp[max];
	}
	
	private static int max(int[] input) {
	    int max=0;
	    for(int i=1;i<input.length;i++) {
	        if(input[i]>input[max]) {
	            max=i; // update max index
	        }
	    }
	    return max;
	}
}
