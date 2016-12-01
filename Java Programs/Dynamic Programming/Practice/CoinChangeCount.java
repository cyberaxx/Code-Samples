import java.util.*;
import java.lang.*;
import java.io.*;

public class CoinChangeCount {
	public static void main (String[] args) {
		//code
		Scanner scanner=new Scanner(System.in);
		int testCase=scanner.nextInt();
		for(int i=0; i<testCase; i++) {
		    int num=scanner.nextInt();
		    int[] coins=arrayTestReader(scanner, num);
		    int amount=scanner.nextInt();
		    System.out.println(coinChange(amount, coins));
		}
		scanner.close();
	}
	
	/* this method compute the number of ways to change a given value using coins: */
	private static long coinChange(int amount, int[] coins) {
	    /* memo table to keep track of values of subproblems: */
	    long[] memo=new long[amount+1]; // +1 for amount 0
	    
	    /* 1. initialization step: */
	    memo[0]=1; /* there is only one way to change 0 amount, using 0 coins */
	    
	    /* 2. relaxation step: */
	    for(int j=0; j<coins.length; j++) {
	        for(int i=1; i<=amount; i++) {
	            /* check if the coin value is not greater than the amount: */
	            if(i>=coins[j]) {
	                memo[i]+=memo[i-coins[j]];
	            }
	        }
	    }
	    
	    /* 3. optimal solution to the original problem: */
	    return memo[amount];
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
