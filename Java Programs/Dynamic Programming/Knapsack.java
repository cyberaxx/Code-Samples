import java.util.List;
import java.util.ArrayList;

public class Knapsack {
  // return the optimal value and reconstuct the optimal path
  public static int maxKnapsackValue(int[] values, int[] sizes, int capacity, List<Integer> path) {
    // number of available items:
    int N=values.length;

    // Considering N items in a sequence 1...N
    // Max value of the knapsack considering prefixes of input sequence and space available in the knapsack:
    // so the optimal value would be in the dp[N][capacity] element of the array: the optimal solution considering all N items, and W space
    int[][] dp=new int[N+1][capacity+1];

    // Base cases:
    // 1. knapsack with 0 capacity, there is no room to take any item, so optimal value would be 0:
    for(int i=0; i<=N; i++) dp[i][0]=0;
    // 2. knapsack with 0 items in it, optimal value would be 0 for any possile capacity from [0 capacity]:
    for(int j=0; j<=capacity; j++) dp[0][j]=0;

    // Recursion and memoization:
    // from 0 to capacity, for any possible value for available capacity, find the optimal value of the knapsack
    // by considering items in a sequence from 1...N, meaning take item or items that makes the value of the knapsack
    // maximal, given the room available in the knapsack
    for(int W=0; W<=capacity; W++) {
      // consider items in a 1...N sequence:
      for(int i=1; i<=N; i++) {
	// retrieve the weight and value of the item i:        
        int w=sizes[i-1];
	int v=values[i-1];

	// check for if there exist enough space remaining in the knapsack to take items i
	// there is not enough space in the knapsack to take the item i:
        if(W<w) 
          dp[i][W]=dp[i-1][W];

        // recursion: 
	// There are only two possible CHOICES for each subproble (prefix of the item sequence): choose among following choices to max solution
	// 1. DO take the item and find the optimal solution for the remaining space in the knapsack using remaining items
	// 2. DO NOT take the item and find the optimal solution for the remaining space in the knapsack using remaining items
        else 
          dp[i][W]=Math.max(dp[i-1][W-w]+v, dp[i-1][W]);
      }
    }
    
    // path reconstruction:
    int r=N; int c=capacity;
    while (r>1 && c>0) {
      // compare the optimal solution with optimal solution by considering i-1 items:
      if(dp[r][c]==dp[r-1][c]) {
	// do not take the item:
	r--; // consider the prefix 1...i-1
      }
      else {
        // take the item:
        path.add(r-1);
        r--;
        c-=sizes[r-1];
      }
    }

    return dp[N][capacity];
  } 
   
  public static void main(String [] args) {
    int[] v={3,2,4,4};
    int[] w={4,3,2,3};
    int W=6;
    List<Integer> path=new ArrayList<Integer>();// empty list of integers

    System.out.println("Max knapsack value: "+ maxKnapsackValue(v,w,W,path));
    System.out.println("Indexes of items in knapsack: "+ path);

  }

}
