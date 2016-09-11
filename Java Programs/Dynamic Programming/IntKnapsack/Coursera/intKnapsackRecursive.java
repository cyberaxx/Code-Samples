import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.HashMap;

public class intKnapsackRecursive {

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
    

    HashMap<Integer, HashMap<Integer, Integer>> memo=new HashMap<Integer, HashMap<Integer, Integer>>();

    // We would like to compute the maximum
    // value that could fit to the capacity
    int maxValue = intKnapsackValue(valueArray, sizeArray, totalCapacity, sizeArray.length, memo);
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

  public static int intKnapsackValue(int[] values, int[] weights, int W, int N, HashMap<Integer, HashMap<Integer, Integer>> memo) {
    // if recursion has been already memoized:
    if(memo.containsKey(W)) {
      if(memo.get(W).containsKey(N))
	return memo.get(W).get(N);
    }

    // Base cases:
    // case 1: if knapsack has 0 capacity
    if(W==0) {
      HashMap<Integer, Integer> hm;
      // Add the new solution to the memo table:
      if(memo.containsKey(W))
	hm=memo.get(W);
      else
	hm=new HashMap<Integer, Integer>();
      hm.put(N,0);
      memo.put(W, hm);

     // The return 0
     return 0;
    }

    // case 2: if there is no item to pick
    if(N==0) {
      HashMap<Integer, Integer> hm;
      // Add the new solution to the memo table:
      if(memo.containsKey(W))
	hm=memo.get(W);
      else
	hm=new HashMap<Integer, Integer>();
      hm.put(N,0);
      memo.put(W, hm);

     // The return 0
     return 0;
    }

    // case 3: if knapsack capacity is smaller than object size:
    if(weights[N-1]>W) {
      HashMap<Integer, Integer> hm;
      // Add the new solution to the memo table:
      if(memo.containsKey(W))
	hm=memo.get(W);
      else
	hm=new HashMap<Integer, Integer>();
      hm.put(N,0);
      memo.put(W, hm);

     // The return 0
     return 0;
    }

    int ks=Math.max(
		intKnapsackValue(values, weights,W,N-1, memo),
		intKnapsackValue(values, weights,W-weights[N-1],N-1, memo)+values[N-1]
		);

    // Add the new solution to the memo table:
    HashMap<Integer, Integer> hm;
    if(memo.containsKey(W))
      hm=memo.get(W);
    else
      hm=new HashMap<Integer, Integer>();

    hm.put(N,ks);
    memo.put(W, hm);

    return ks;
  }
}
