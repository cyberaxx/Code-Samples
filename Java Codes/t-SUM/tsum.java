import java.util.Collections;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// to use Stopwatch
import edu.princeton.cs.algs4.*;

public class tsum {
  public static void main (String [] args) throws IOException {
    int [] input = new int []{5,2,3,4,2,2,5,1};
    // printSum (input, 7);

    int [] fileInput = new int [32000];
    FileReader fr = new FileReader("32Kints.txt");
    BufferedReader br = new BufferedReader (fr);
    String line;
    int index = 0;
    while ( (line = br.readLine()) != null ) {
      fileInput[index] = Integer.parseInt(line.trim()); // convert string value into an integer value
      index++;
    }

    Stopwatch st = new Stopwatch();
    int count = q3sum(fileInput, 0);
    double time = st.elapsedTime();
  
    System.out.println(count);
    System.out.println(time);
  }

  public static int count (int [] input, int target) {
    int count = 0;
    HashSet<Integer> hs = new HashSet<Integer> ();
    for (int i=0 ;i<input.length; i++)
      hs.add(input[i]); // to remove duplicate elements
    
    // iterate over elements in the HashSet: O(n)
    for (Integer item : hs)
      if ( hs.contains(target-item) ) count++;

    return count;
  }

  // O(n^2):
  /*
  3-SUM in quadratic time. Design an algorithm for the 3-SUM problem that takes 
  time proportional to N2 in the worst case. You may assume that you can sort the 
  N integers in time proportional to N2 or better.
  */
  public static int q3sum (int [] input, int target) {
    int negSum;
    int count=0;

    // sort the input array: O(nlogn)
    Arrays.sort(input);

    // Assuming the input elements are distince we can use a hashset keyed by input elemet:
    HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
    for (int i=0; i<input.length; i++) // O(n)
      hm.put(input[i], i);


    for (int i=0; i<input.length; i++) {
      for (int j=i+1; j<input.length; j++) {
        negSum = -1 * (input[i] + input[j]);
	// look up the hashset for desired element:
        if (hm.containsKey(negSum)) {
	  if (hm.get(negSum) > j)  count++; // To get rid of duplicates (even though elements are distinct but their combination may not be)
	}
      }
    }
    return count;
  }
  public static void printSum (int [] input, int target) {
    HashMap<Integer, ArrayList<Integer>> hm = new HashMap<Integer,ArrayList<Integer>>(); // similar idea to chaining
    int tempKey;
    int tempVal;
    ArrayList<Integer> vals;
    // remove duplicates in keys and keep track of their corresponding indexes in the values
    for(int i=0;i<input.length;i++) {
      if (hm.containsKey(input[i]))
        vals = hm.get(input[i]);
      else
        vals = new ArrayList<Integer>();

      vals.add(i);
      hm.put(input[i], vals);
    
    }
    
    // iterate over keys in the array
    for (int i=0; i<input.length; i++) {
      if(hm.containsKey(target-input[i])) {
        ArrayList<Integer> vals_temp = hm.get(target-input[i]);
        for (Integer val :  vals_temp) {
          if (i < val) // important modification to get rid of symmetric duplicate results
            System.out.println("item in following indeces: " + i + " and " + val + " sum up to: " + target);
        }
      }
    }
         
  }
}

