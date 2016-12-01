import java.util.*;
import java.lang.*;
import java.io.*;

public class JumpArray {
	public static void main (String[] args) {
		//code
		Scanner scanner=new Scanner(System.in);
		int tests=scanner.nextInt();
		for(int i=0; i<tests; i++) {
		    int num=scanner.nextInt();
		    int[] steps=arrayTestReader(scanner,num);
		    System.out.println(minJumps(steps));
		}
		scanner.close();
	}
	
	/* this method computes min number of jumps required to reach the end: */
	private static int minJumps(int[] steps) {
	    /* memo table of optimal jumps: */
	    int[] jumps=new int[steps.length];
	    
	    /* 1. initialization: */
	    jumps[0]=0; // min jumps required to get to state 0, is 0
	    for(int i=1; i<jumps.length; i++) jumps[i]=Integer.MAX_VALUE; // min jumps required to get to state i from state 0
	    
	    /* 2. relaxation step: in topological order of subproblems: */
	    for(int i=1; i<steps.length; i++) {
	        for(int j=0; j<i; j++) {
	            /* if there exist an implict edge between state i and state j: */
	            if(steps[j]>=i-j) {
	                /* relax: */
	                if(jumps[i]>jumps[j]+1 && jumps[j]!=Integer.MAX_VALUE) {
	                    jumps[i]=jumps[j]+1;
	                }
	            }
	        }
	    }
	    
	    /* optimal solution to the original problem: */
	    if(jumps[steps.length-1]==Integer.MAX_VALUE) return -1;
	    else return jumps[steps.length-1]; 
	    
	}
	
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
}
