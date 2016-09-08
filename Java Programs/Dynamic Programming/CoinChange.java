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
    System.out.println("List of coins usde: "+path);

    /*
     10
     5
    */
    coins={2,5,3,6};
    int amount=10;
  }


  /*
    How many different ways can you make change for an amount, 
    given a list of coins?
  */
  public static int coinChangeCount(int[] coins, int amount) {
  }
}
