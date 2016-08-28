public class Test {
  public static void main (String [] args) {
    int [] vals = new int[]{3,3,4,2};
    int [] weights = new int[]{5, 6, 10, 12}; // weights are integral
    int capacity = 23; // capacity is integral
    System.out.println(intKnapsack(vals, weights, capacity));
  }

  public static int intKnapsack(int [] values, int [] weights, int capacity) {
    // conside n items as a sequence 1 ... n with integer weights and values:
    int n=values.length;
    int[][] knapsack=new int[n+1][capacity+1]; // n and canpacity inclusive

    // BASE CASE: 1. 0 item, 2. capacity
    // 1. 0 item, capacity from 0-capacity:
    for(int j=0; j<=capacity; j++)  knapsack[0][j]=0;  
    // 1. 0 capacity, items from 0-n:
    for(int i=0; i<=n; i++)  knapsack[i][0]=0;  
    
    // RECURRENCE: bottom up
    for(int j=1; j<=capacity; j++) { // considering remaining capacity from 1 ... capacity
      for(int i=1; i<=n; i++) {// considering 1 ... n items
        // check if the weight of the item i is greater than remaining capacity:
        int w=weights[i-1]; // weights matrix is 0-based index
        int v=values[i-1]; // values matrix is 0-based index
        if(w>j)  knapsack[i][j]=knapsack[i-1][j]; // Do NOT take item i, and remaining space stays the same
        else  knapsack[i][j]=Math.max(knapsack[i-1][j],knapsack[i-1][j-w]+v);
      }
    }
    return knapsack[n][capacity];
  }
}
