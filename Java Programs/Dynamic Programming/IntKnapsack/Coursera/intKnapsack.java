import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class intKnapsack {

  public static void main(String[] args) throws IOException {
  
    File inputFile = new File ("knapsack1.txt");
    int [][] inputArrayList;
    // since the inputHandler method throws IOException, all its caller must also throw IOException
    inputArrayList = inputHandler(inputFile);
    // list of item values, they do NOT have to be integer
    int [] valueArray = inputArrayList[0];
    // item sizes have to be integer:
    int [] sizeArray = inputArrayList[1];
    // Total capacity has to be integer:
    int totalCapacity = inputArrayList[2][0]; 
    
    // We would like to compute the maximum
    // value that could fit to the capacity
    int maxValue = intKnapsackValue(valueArray, sizeArray, totalCapacity);
    System.out.println("The maximum value that fit into the knapsack is: " + maxValue);

  }

  // Reading input from the file:
  public static int [][] inputHandler(File inputFile) throws IOException {

    FileReader fr = new FileReader (inputFile);
    BufferedReader br = new BufferedReader (fr);
    String line;
    int lineCounter = 0;

    int itemCount, knapsackSize;
    int [][] inputArrayList = new int [3][];
    int arrayIndx = 0;

    while( (line = br.readLine()) != null){

      lineCounter++;

      if (lineCounter == 1) {
	itemCount = Integer.parseInt(line.split(" ")[1]);
	knapsackSize = Integer.parseInt(line.split(" ")[0]);

	inputArrayList [0] = new int [itemCount];
	inputArrayList [1] = new int [itemCount];
	inputArrayList [2] = new int [1];

	inputArrayList [2][0] = knapsackSize;
      }

      else {
        inputArrayList [0][arrayIndx] = Integer.parseInt(line.split(" ")[0]);
        inputArrayList [1][arrayIndx] = Integer.parseInt(line.split(" ")[1]);
        arrayIndx++;	
      }

    }

    br.close();
    fr.close();
    
    return inputArrayList;

  }

  public static int intKnapsackValue(int [] valueArray, int [] sizeArray, int totalCapacity) {
    // We want to fill out knapsacks with different integral capacities
    // considering all of the items:
    // Since the values are all chosen to be integer, the type of the matrix that keeps track
    // of value is integer as well:
    int [][] DP = new int [valueArray.length+1][totalCapacity+1];

    // Basecases:
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
	  if ( s - sizeArray[i-1] < 0 )
	    DP[i][s] = DP[i-1][s]; 
	  else 
	    DP[i][s] = Math.max(DP[i-1][s - sizeArray[i-1]] + valueArray[i-1], DP[i-1][s]); 
      }
    }
 
   //matrixRenderer(DP);
   return DP[valueArray.length][totalCapacity];

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
