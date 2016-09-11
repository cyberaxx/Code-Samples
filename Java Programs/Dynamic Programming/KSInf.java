/*
  Knapsack problem with infinity many items avaiable of each of 1....N items
*/

import java.util.Deque;
import java.util.ArrayDeque;

public class KSInf {

  public static int knapsackInf(int[] values, int[] weights, int capacity) {
    // input sanity check:
    if(values==null || weights==null) throw new NullPointerException();

    // number of items in a sequence of 1....N items:
    int N=values.length;
    // a sequence of all possible integral remaining capacity of the knapsack:
    int W=capacity; // 0 .... W
    // memo table: maintain the optimal value associate with each optimal subproblem (optimal substructure)
    int[] ks=new int[W+1];
    int[] items=new int[N+1];
    // space required: O(W+N)

    // base case:
    items[0]=0; 
    ks[0]=0; // the max value of 0-capacity knapsack is 0
    // for possible integral remaining space in the knapsack:
    for(int w=1; w<=W; w++) {
      for(int i=1; i<=N; i++) {
	// consider the weight and value of the i-th item in the input sequence:
        int weight=weights[i-1];
	int val=values[i-1];
        // if the item does not fit the knapsack, 1 choice remains: not picking the i-th item:
	if(weight>w) items[i]=0;
	// Otherwise:
	else items[i]=Math.max(ks[w-weight]+val, items[i-1]);
      }
      ks[w]=items[N];
    }
    return ks[W];
  }

  public static int knapsack(int[] values, int[] weights, int capacity, boolean inf, Deque<Integer> items) {
    // input sanity check:
    if(values==null || weights==null || items==null) throw new NullPointerException();

    // number of items in a sequence of 1....N items:
    int N=values.length;
    // a sequence of all possible integral remaining capacity of the knapsack:
    int W=capacity; // 0 .... W
    // memo table: maintain the optimal value associate with each optimal subproblem (optimal substructure)
    int[][] ks=new int[N+1][W+1];

    // base case: 
	// 1. 0 remaininf space and sequence of N items to pick from: Max value is 0
    for(int i=0; i<=N; i++) ks[i][0]=0; // no item can be taken, there is no room!!!
	// 2. 0 items to take from, for all possible integral avaiable room in the knapsack
    for(int w=1; w<=W; w++) ks[0][w]=0;

    // For possible integral remaining space in the knapsack: consider all possible prefixes of the sequence of input items:
    for(int w=0; w<=W; w++) {
      for(int i=1; i<=N; i++) {
	// consider the weight and value of the i-th item in the input sequence:
        int weight=weights[i-1];
	int val=values[i-1];
        // if the item does not fit the knapsack, 1 choice remains: not picking the i-th item:
	if(weight>w) ks[i][w]=ks[i-1][w];
	// Otherwise:
	else {
	  // if there were infinitely many of each item avaiable or if there were only one of each item available:
	  // Possible choices: 1. taking the item i, 2. not taking the item i
	  // try both options and pick the one that maximizes the value of the knapsack
	  if(inf) ks[i][w]= Math.max(ks[i-1][w], ks[i][w-weight]+val);
	  else ks[i][w]= Math.max(ks[i-1][w], ks[i-1][w-weight]+val);
	}
      }
    }

    // path reconstruction from underlying DAG:
    int r=N; int c=W;
    while(r>0 && c>0) {
      if(ks[r][c]==ks[r-1][c]) {r--;} // not picking the item
      else if(ks[r][c]==ks[r-1][c-weights[r-1]]+values[r-1]) {
        // take the item (inf is false):
        items.push(values[r-1]);
	c-=weights[r-1];
	r--;
      }
      else if(ks[r][c]==ks[r][c-weights[r-1]]+values[r-1]) {
        // take the item (inf is true):
        items.push(values[r-1]);
	c-=weights[r-1];
      }	
    }
    return ks[N][W];
  }

  public static void main(String[] args) {
    Deque<Integer> path=new ArrayDeque<Integer>();
    int[] values=new int[]{30,14,16,9};
    int[] weights=new int[]{6,3,4,2};
    int capacity=10;
    boolean inf=true;
    System.out.println("Max value of the knapsack (unlimited): "+ knapsack(values,weights,capacity,inf,path));
    System.out.println("Items in the optimal knapsack (unlimited): "+ path);

    System.out.println("Max value of the knapsack (unlimited): "+ knapsackInf(values,weights,capacity));

    path=new ArrayDeque<Integer>();
    System.out.println("Max value of the knapsack (limited): "+ knapsack(values,weights,capacity,false,path));
    System.out.println("Items in the optimal knapsack (limited): "+ path);
  }
}

