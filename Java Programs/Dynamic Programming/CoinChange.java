/*
The change-making problem addresses the following question: 
how can a given amount of money be made with the least number of coins of given denominations? 
It is a knapsack type problem, and has applications wider than just currency.

Assumption:
The total amount is an INTEGER value
The value of each coin is an INTEGER value
so their corresponding subtraction is also an INTEGER value

*/

import java.util.List;
import java.util.ArrayList;

public class CoinChange {
  
  public static int minCoinChange(int[] coins, int amount, List<Integer> path) {
    /*  consider n coins a sequence indexed from 1 ... N
        the final solution considers all N coins, but optimal substructures (optimal solution to subproblems) 
	consider prefixes of 1...N coins.
	capacity constraint is another degree of freedom. For any capacity within a range of 0...amount value we
	would like to find the optimal allocation of coins, if the coin value exceed the capacity we have no choice but not taking
        the coin
    */	

    // number of coins in a sequence
    int N=coins.length;
    int W=amount;
    int inf=Integer.MAX_VALUE;
    inf=inf&0x7ffffff;

    // Memo table that chaches solution to optimal substrucutres:
    int[][] dp=new int[N+1][W+1];// the optimal solution would be the solution to the optimal substructure that considers N coins and fulfills the entire amounnt

    // base cases:
    // 1. where 0 amount needs to be changed into coins, the optimal substructure dp[i][0] for any value for i:
    for(int i=0; i<=N; i++) dp[i][0]=0; // no coin has to be used to change 0 into coins
    // 2. where 0 coin is used the optimal substructure dp[0][W] for any value for W:
    for(int w=1; w<=W; w++) dp[0][w]=inf; // there are inifintely many wasy to change non-zero values of w with 0 coins

    // recurrence: recursion + memoization
    // For all prefixes of 1...N sequence of coins and For all possible integral values for the amount
    // keep the optimal value for each combination and reuse it to solve optimal substructure of bigger size:
    for (int w=0; w<=W; w++) {
      for (int i=1; i<=N; i++) {
        int v=coins[i-1];
	// if coin's value is greater than the amount: 
	// do not take it, so the optimal solution would be the optimal substructure considering 1...i-1 coins and w amount
        if(v>w)  dp[i][w]=dp[i-1][w];

        // otherwise: there are two choices: a. taking the coins and see if you can take more of it, b. not taking the coin
        // 1. take the optimal substructure that considers 1...i-1 coins and w amount, or 
	// 2. use the coin i in the optimal substurcture (+1) and find the optimal substructure for 1...i coins and w-v amount 
        else  dp[i][w]=Math.min(dp[i-1][w], dp[i][w-v]+1);
      }
    }

    if(dp[N][W]==inf) {
      return -1;
    }

    else {
      // path reconstruction:
      int r=N;
      int c=W;
      while(r>0 && c>0) {
        if(dp[r][c]==dp[r-1][c]) {
          // coins was not taken:
          r--;
        }
        else {
          // take the coins 
    	  path.add(r-1); // index of the coin
	  c-=coins[r-1];
        }
      }
      return dp[N][W];
    }

  }

  public static void main(String[] args) {
    /*
    coins = [1, 2, 5], amount = 11
    return 3 (11 = 5 + 5 + 1)
    */
    int[] coins={1,2,5};
    int amount=11;
    List<Integer> path=new ArrayList<Integer>();
    System.out.println("Min number of coins used is: "+minCoinChange(coins, amount, path));
    System.out.println("List of indexes of coins used: "+path);

    // coin change counting: number of possible ways to represent an amount:
    /*
     10
     5
    */
    coins=new int[]{2,5,3,6};
    amount=10;
    System.out.println("Number different ways can you make change: "+coinChangeCount(coins, amount));
    
  }


  /*
    How many different ways can you make change for an amount, 
    given a list of coins?
  */
  public static int coinChangeCount(int[] coins, int amount) {
	return 0;
  }
}
