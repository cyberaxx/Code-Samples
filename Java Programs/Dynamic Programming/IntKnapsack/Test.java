public class Test {
  public static void main (String [] args) {
    int [] vals = {3,3,4,2};
    int [] weights = {5, 6, 10, 12};
    int capacity = 23;
    System.out.println(intKnapsack(vals, weights, capacity));
  }

  public static int intKnapsack(int [] values, int [] weights, int capacity) {
    int [][] optimalVal = new int [values.length+1][capacity+1]; // n*W matrix
    // consider items being a seq of n items, in no particular order

    // initial subproblems:
    // 1. 0 capacity
    for(int i=0; i<=optimalVal.length; i++) optimalVal[i][0]=0;
    // 2. 0 items
    for(int j=0; j<=optimalVal[0].length; j++) optimalVal[0][j]=0;
  
    // solutions to subproblems are prefixes of the optimal solutions
    for (int s=1; s<=capacity; s++) {
      for (int j=1; j<value.length; j++) {
	// if item is larger than the capacity do Not take it
        if(weights[i] > s) optimalVal[s][j] = optimalVal[s][j]; 
      }
    }
  }


}