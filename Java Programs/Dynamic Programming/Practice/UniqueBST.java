import java.util.*;
import java.lang.*;
import java.io.*;

public class UniqueBST {
	public static void main (String[] args) {
		//code
		Scanner scanner=new Scanner(System.in);
		int testCases=scanner.nextInt();
		for(int i=0; i<testCases; i++) {
		    int n=scanner.nextInt();
		    System.out.println(bst(n));
		}
		scanner.close();
	}
	
	/* this method counts the number of distinct BST given the sequence 1..N */
	private static int bst(int n) {
	    /* memo table to maintain solution to subproblems (prefixes): */
	    int[] bst=new int[n+1]; // +1 for empty bst
	    
	    /* 1. initialization: */
	    bst[0]=1; // number of unique bst's with 0 node
	    bst[1]=1; // number of unique bst's with 1 node
	    
	    /* 2. relaxation in topological order of subproblems (prefixes of input sequence) */
	    for(int i=2; i<=n; i++) {
	        /* pick the root node: */
	        for(int j=0; j<i; j++) {
	            /* sum of different possible left and right subtrees: */
	            bst[i]+=bst[j]*bst[i-j-1];
	        }
	    }
	    
	    /* 3. optimal solution to the original problem: */
	    return bst[n];
	}
}
