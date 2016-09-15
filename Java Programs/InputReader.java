import java.util.*;
import java.lang.*;
import java.io.*;

class InputReader {
	public static void main (String[] args) {
		//code

		
		int[][] test=matrixReader();
	
		for(int i=0; i<test.length; i++) {
		    if(isBST(test[i]))
		        System.out.println(1);
		    else
		        System.out.println(0);
		}

/*

Sample output
for matrix of
test cases:

		for(int i=0; i<test.length; i++) {
		    System.out.println(isBST(test[i]));
		}


TEST the matrix is correctly build:


		for(int i=0; i<test.length; i++) {
		    for(int j=0; j<test[i].length; j++) {
		        System.out.print(test[i][j]+"\t");
		    }
		    System.out.println();
		}

----------------------------------------------
		
		int[] testVec=vectorReader();
		for(int i=0; i<testVec.length; i++) {
		    System.out.print(testVec[i]+"\t");
		}
		System.out.println();
*/
	}
	
	private static boolean isBST(int[] preorder){
	    return true;
	}
	
	private static int[][] matrixReader() {
	    
	    Scanner scanner=new Scanner(System.in);
	    
	    // number of test cases:
	    int t=scanner.nextInt();
	    
	    // test case matrix: each test case is a row of the matrix
	    int[][] testcase=new int[t][];
	    int i=0;
	    
	    // for each test case: 
	    while(i<t) {
	        // number of samples in the the test case instance:
	        int n=scanner.nextInt();
	        // test case instance vector
	        testcase[i]=new int[n];
	        
	        // populate the instance vector
	        int j=0;
	        while(j<n) {
	            testcase[i][j]=scanner.nextInt();
	            j++;
	        }
	        
	        // move on to the next test case instance:
	        i++;
	    }
	    
	    scanner.close();
	    return testcase;
	    
	}
	
	private static int[] vectorReader() {
	    Scanner scanner=new Scanner(System.in);
	    
	    // number of test cases:
	    int t=scanner.nextInt();
	    
	    // test case vector:
	    int[] testcase=new int[t];
	    int i=0;
	    
	    // populate the instance vector 
	    while(i<t) {
	        testcase[i]=scanner.nextInt();
	        // move on to the next test case instance:
	        i++;
	    }
	    
	    scanner.close();
	    return testcase;
	}	
}
