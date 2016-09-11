/*
  More formally, we are given n items {1, . . . , n}, and each has a given
  nonnegative weight w i (for i = 1, . . . , n). 

  We are also given a bound W. 

  We would like to select a subset S of the items so that i∈S w i ≤ W and, subject
  to this restriction, i∈S w i is as large as possible.

  We will call this the Subset
  Sum Problem.
*/

import java.util.Deque;
import java.util.ArrayDeque;

public class SubsetSum {

  public static int subsetSum(int[] weights, int cutoff, Deque<Integer> items) {
    // number of requests:
    int N=weights.length;
    // all weights and cuttoff are integer values
    int W=cutoff;
    
    // if there have been only one instance of each requests we need
    // to keep track of which prefix of request sequence has been consider for 
    // each integral value of the cutoff:
    int[][] subsetSum=new int[N+1][W+1];

    // BASE CASE:
    // 1. if remaining cutoff is 0: 0 request can be processed so the sum of the subset would be 0
    for(int i=0; i<=N; i++) subsetSum[i][0]=0;
    // 2. if 0 request available: no matter what the curoff value is sum of the subset would be 0
    for(int w=1; w<=W; w++) subsetSum[0][w]=0;

    // RECURRENCE:
    // for all integral values of cuttoff from 0....W
    // considering all available requests 1....N
    for(int w=0; w<=W; w++) {
      for(int i=1; i<=N; i++) {
        // if the cuttoff is less than the weight required by process i:
        if(w<weights[i-1])  subsetSum[i][w]=subsetSum[i-1][w];
        else subsetSum[i][w]=Math.max(subsetSum[i-1][w],subsetSum[i-1][w-weights[i-1]]+weights[i-1]);
      }
    }
    
    // path reconstruction
    int r=N; int c=W;
    while(r>0 && c>0) {
      if(subsetSum[r][c]==subsetSum[r-1][c]) r--;
      else {
	items.push(weights[r-1]);
	c-=weights[r-1];
	r--;
      }
    }
    return subsetSum[N][W];
  } // O(NW)

  public static void main(String[] args) {
    Deque<Integer> path=new ArrayDeque<Integer>();
    int[] weights=new int[]{2,2,3};
    int capacity=6;
    System.out.println("Max value of the subset is: "+ subsetSum(weights,capacity,path));
    System.out.println("Items in the optimal subset is: "+ path);
  }
}
