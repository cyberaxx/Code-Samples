public class intKnapsack {

  public static void main(String[] args) {
    // list of item values, they do NOT have to be integer
    int [] valueArray = new int []{3,2,4,4};
    // item sizes have to be integer:
    int [] sizeArray = new int []{4,3,2,3};
    // Total capacity has to be integer:
    int totalCapacity = 6; 
    
    // We would like to compute the maximum
    // value that could fit to the capacity
    int [][] DP = intKnapsackValue(valueArray, sizeArray, totalCapacity);
    int maxValue = DP[valueArray.length][totalCapacity];
    System.out.println("The maximum value that fit into the knapsack is: " + maxValue);
    pathRecunstruction (DP,valueArray,sizeArray);
  }

  public static int [][] intKnapsackValue(int [] valueArray, int [] sizeArray, int totalCapacity) {
    // We want to fill out knapsacks with different integral capacities
    // considering all of the items:
    // Since the values are all chosen to be integer, the type of the matrix that keeps track
    // of value is integer as well:
    int [][] DP = new int [valueArray.length+1][totalCapacity+1];

    // Base cases:
    // no items:
    for (int s = 1; s<= totalCapacity; s++)
      DP[0][s] = 0;
    // 0 capacity:
    for (int i = 0; i < valueArray.length; i++)
      DP[i][0] = 0;

    // The dimensions of the DP matrix denotes the index of items we have considered so far
    // and the remaining space in the knapsack, respectively
    for (int s = 1; s <= totalCapacity; s++ ) {
      for (int i = 1; i <= valueArray.length; i++) {
	  // if the item size is bigger than the space avaiable in the
	  // knapsack, simply do NOT take that item: 
	  if ( s - sizeArray[i-1] < 0 )
	    DP[i][s] = DP[i-1][s]; 
	  else 
	    DP[i][s] = Math.max(DP[i-1][s - sizeArray[i-1]] + valueArray[i-1], DP[i-1][s]); 
      }
    }
 
   matrixRenderer(DP);
   return DP;

  }

  public static void pathRecunstruction (int [][] DP, int [] valueArray, int [] sizeArray) {
    int itemCount = DP.length - 1;
    int knapsackSize = DP[0].length - 1;
    int i,j;
    i = itemCount;
    j = knapsackSize;

    System.out.println("The list of items in the knapsack: (item index start from 1)");
    while (i>0 && j>0){
      // if the item i has been taken:
      if( DP[i][j] > DP[i-1][j] ) {
	System.out.println("Item index: " + i);
	// reduce the space:
	j = j - sizeArray[i-1];
	// reduce the item counter
	i--;
      }
      // otherwise (if object i was not chosen), simply keep the space the same
      // but decrement the item counter by 1.
      else i--;
    }
  }

  public static void matrixRenderer(int [][] matrix){
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + "  ");
      }
      System.out.print("\n");
    }
    System.out.print("\n");
  }

}
