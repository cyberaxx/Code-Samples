import java.util.*;
import java.lang.*;
import java.io.*;

public class MinCoinChange {
	public static void main (String[] args) {
		//code
		Scanner scanner=new Scanner(System.in);
		int testCase=scanner.nextInt();
		for(int i=0; i<testCase; i++) {
		    int amount=scanner.nextInt();
		    int num=scanner.nextInt();
		    int[] coins=arrayTestReader(scanner, num);
		    System.out.println(coinChange(amount, coins));
		} 
		scanner.close();
	}
	
	/* this method computes the min number of coins required to change the value: */
	private static int coinChange(int amount, int[] coins) {
	    /* memo table to keep optimal values to subproblems: */
	    int[] count=new int[amount+1]; // +1 for 0 amount
	    
	    /* 1. initialization step: */
	    count[0]=0; /* min number of coins to represent 0 amount, is 0 */
	    for(int i=1; i<=amount; i++) count[i]=Integer.MAX_VALUE;
	    
	    /* 2. edge relaxation is topological order: */
	    for(int i=0; i<coins.length; i++) {
	         for(int w=1; w<=amount; w++) {
	            /* if there exists an edge: */
	            if(w>=coins[i] && count[w-coins[i]]!=Integer.MAX_VALUE) {
	                /* relax: */
	                if(count[w]>count[w-coins[i]]+1) {
	                    count[w]=count[w-coins[i]]+1;
	                }
	            }
	        }
	    }
	    
	    /* 3. optimal solution to the original problem: */
	    if(count[amount]==Integer.MAX_VALUE) return -1;
	    else return count[amount]; 
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
