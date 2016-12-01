import java.util.*;

public class UnboundedKS {

  public static void main(String[] args) {
    System.out.println(knapsack (10,  new int[]{30, 14, 16, 9}, new int[]{6,3,4,2}));
  }


  /* this method computes the maximal value of the knapsack given values, weights and capacity constraint: */
  private static int knapsack (int capacity, int[] values, int[] weights) {
    /* memo table that maintain the optimal value of subproblems: */
    int[] memo=new int[capacity+1]; // +1 for 0 capacity
    
    /* 1. initialization step: */
    memo[0]=0; /* the maximal value of a knapsack with 0 capacity is 0 */
    
    /* 2. relaxation step: topological order of subproblems (in size of capacity) */
    for(int i=1; i<=capacity; i++) {
      /* consider all items: */
      for(int j=0; j<values.length; j++) {
        /* check if the item fits in the capacity: */
        if(i>=weights[j]) {
          /* relax: */
          if(memo[i]<memo[i-weights[j]]+values[j]) {
            memo[i]=memo[i-weights[j]]+values[j]; // longes path in dependecy DAG
          }       
        }
      }
    }
    
    /* path reconstruction: */
    Deque<Integer> path=new ArrayDeque<Integer>(); // empty stack
    int c=capacity;
    while(c>0) {
      for(int i=0; i<values.length && c>0 ; i++) {
        if(c>=weights[i]) {
          if(memo[c]==memo[c-weights[i]]+values[i]) {
            path.push(values[i]);
            c=c-weights[i];
          }
        }
      }
    }
        
    System.out.println(path);
    
    /* optimal solution to the original problem: */
    return memo[capacity];
    
  }
}
