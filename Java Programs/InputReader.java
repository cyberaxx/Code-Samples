import java.util.*;
import java.lang.*;
import java.io.*;

class InputReader {
	public static void main (String[] args) {
		// open the scanner
		Scanner scanner=new Scanner(System.in);


	        /* -----------------------------MATRIX and ARRAY input----------------------------- */
		// 1. LINEAR many (t) TEST CASES:
		int num=scanner.nextInt();
		int[][] testMatrix=matrixTestReader(scanner, num); // num is number of test cases

		// 2. LINEAR one (1) test case:
		int len=scanner.nextInt();
		int[] testArray=arrayTestReader(scanner, len);

 		// 3. 1 two dimensional [,] matrix input  reader
		//int[][] test2=matrixReader(scanner);


	        /* -----------------------------GRAPH without weight--------------------------------------- */

		// 1. unweighted graphs using adjacency list representation
		// Getting V and E
                int V=scanner.nextInt();
		int E=scanner.nextInt();
		// Create an adjacency list		
		List[] adj= unWeightedGraphReader(V, E, true, scanner, " ");
  
		// 2. weighted graphs using adjacency list representation
		List[][] test5= weightedGraphReader(" ");  


		// close the scanner
		scanner.close();		

	}
               
/*
Sample input for unweighted GRAPH

13 13
0 5
4 3
0 1
9 12
6 4
5 4
0 2
11 12
9 10
0 6
7 8
9 11
5 3

0: [5, 1, 2, 6]
1: [0]
2: [0]
3: [4, 5]
4: [3, 6, 5]
5: [0, 4, 3]
6: [4, 0]
7: [8]
8: [7]
9: [12, 10, 11]
10: [9]
11: [12, 9]
12: [9, 11]

*/



/*
-----------------------------------------------MATRIX TEST reader---------------------------------------------------------------------------
*/
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

/*
-----------------------------------------------ARRAY TEST reader---------------------------------------------------------------------------
*/	
	private static int[] arrayTestReader(Scanner scanner, int num) {

	    // test case array:
	    int[] testArray=new int[num];

	    int i=0;	    
	    // populate the instance vector 
	    while(i<num) {
	        testArray[i]=scanner.nextInt();
	        // move on to the next test case instance:
	        i++;
	    }
	    
	    return testArray;
	}

/*
-----------------------------------------------MATRIX reader---------------------------------------------------------------------------
*/








/*
-----------------------------------------------GRAPH without Weights---------------------------------------------------------------------------
*/

  	// for integer value nodes: scans the input stream line by line 
	private static List[] unWeightedGraphReader(int V, int E, boolean undirected, Scanner scanner, String delimiter) { 
        // Adjcancy list intialization:
        // create the vertex index array of adjacency lists:
        List[] adj=new List[V];
    	for(int i=0; i<V; i++)
            adj[i]=new LinkedList<Integer>(); // empty linked list
 
        // for each test case build the array of adjacency lists associated to it:
        while(E>0) {
            int v=scanner.nextInt();
            int w=scanner.nextInt();
            // add edge between v to w
            adj[v].add(w);
            if(undirected)
                adj[w].add(v);
            E--;
        }
       
        return adj;
    }
    

/*
--------------------------------------------------------------------------------------------------------------------------
*/

	private static List[][] weightedGraphReader(String delimiter) {
 	       return null;
    }
}
