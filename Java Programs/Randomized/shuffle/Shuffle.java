import java.util.Random;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class Shuffle {
  public static void main (String[] args) {
    int [] input = new int[]{1,2,3,4,5,6,7,8,9,10};
    int [][] bootstrap = new int [50][10];

    for (int i=0; i<50; i++) {
      knuthShuffling(input);
      //shuffleBySort(input);
      for(int j=0; j<input.length; j++) {
        System.out.print(input[j] + " ");
	bootstrap[i][j] = input[j];
      }
    System.out.println();
    }

    System.out.println();
    System.out.println();
    double [] freqs = freqCalculator(bootstrap);
    for(int i=0; i<freqs.length; i++)
      System.out.println(freqs[i]);
  }
  
  // O(n)
  public static int findDuplicates(int [] results) {
    HashSet<Integer> hs = new HashSet<Integer>();
    int count = 0;
    for(int i=0; i<results.length; i++) {
      // check if the element is already in the HashSet:
      if (hs.contains(results[i])) count++;
      else hs.add(results[i]);
    }
    return count;
  }

  public static double [] freqCalculator(int [][] bootstrap) {
    HashMap<Integer, Integer> hm = new HashMap<Integer,Integer>();
    double [] freqs = new double [bootstrap[0].length];
    int count;
    for(int i=0; i<bootstrap.length; i++) {
      for(int j=0; j<bootstrap[i].length; j++) {
        if (hm.containsKey(bootstrap[i][j])) {
          count = hm.get(bootstrap[i][j]);
	  count++;
 	  hm.put(bootstrap[i][j], count);
        }
	else  hm.put(bootstrap[i][j], 1);
      }
    }     

    int index = 0;
    for(Integer key : hm.keySet()) {
      freqs[index] = (double) hm.get(key);
      index++;
    }

    return freqs;

  }

  public static void exch (int [] items, int i, int j) {
     int temp = items[i];
     items[i] = items[j];
     items[j] = temp;
  }

  // Shuffle elements in the array of int objects, such that the elements
  // distributed in the array unifromly at random in Linear time (Knuth Shuffling)
  public static void knuthShuffling (int [] items) {
    Random rd = new Random();
    int randomIndex;
    // We want to randomly choose and index to exchange elements UNIFORMLY at RANDOM
    for(int i=0; i<items.length; i++) {
      // Randomly choose an index in range of (i, n-1)
      // randomIndex = rd.nextInt(items.length - i) + i; // rd.nextInt(max + 1 - min) + min
      // or (0,i)
      randomIndex = rd.nextInt(i+1);
      if (randomIndex != i) exch(items, i, randomIndex);
    }
  }
 
  // Shuffelling algorithm based on sorting psudo random real numbers: O(nlogn)
  // Using Arrays.sort() which uses "Dual-Pivot QuickSort" on primitive types and
  // MergeSort on Objects (Reference types) because:
  // MergeSort is Stable, and stability matters when sorting object with different attributes that they can be sorted on,
  // Performance garauntee of O(nlogn):
  // stackoverflow.com/questions/15154158/why-collections-sort-uses-merge-sort-instead-of-quicksort
  public static void shuffleBySort (int [] input) {
    // Use hashmap to main the rand number assignmnet:
    HashMap<Double, Integer> hm = new HashMap<Double, Integer>();
    double [] rands = new double [input.length];
 
    // to use the Random class in order to generate
    // random numbers, you need to instantiate from the
    // class, first, before using.
    // The alternative is to call the random method from 
    // the Math class: Match.random() that generates a real (double) random
    // number in [0,1) => {(Max - Min) + Min} * rand in [0,1) would be a random
    // number in [Min, Max) range.
    Random rd = new Random();
    double tempKey;

    // assign each input element an random real number using rd instance from the java's Random Class:
    for(int i=0;i<input.length; i++) {
      tempKey = rd.nextDouble();
      // assign distinct random numbers to array elements (avoid collision and overwriting values for records with same keys)
      while (hm.containsKey(tempKey)) tempKey = rd.nextDouble();
      // assign rand real number to the array elements
      hm.put(tempKey, input[i]);
      rands[i] = tempKey;
    }

    // Use java system sort on array of primitive typs (Double) -> Dual-Pivoit QuickSort O(nlogn)
    Arrays.sort(rands); // sort is static, void, and inplace:
    for(Double key : rands) System.out.print(hm.get(key)+ " ");
    System.out.println();

    /*
    // NOTE: You cannot simply sort keys directly, by invoking Collections.sort on
    // the set returned by hm.keySet()!
    // to do so you must firt declare a ArrayList<Double> and initialize it by 
    // the set returned by hm.keySet(), then sort the list and so on:
    ArrayList<Double> keyList = new ArrayList<Double>(hm.keySet());
    Collections.sort(keyList);
    for(Double key : keyList) System.out.print(hm.get(key)+ " ");
    System.out.println();
    */    
  }
}
