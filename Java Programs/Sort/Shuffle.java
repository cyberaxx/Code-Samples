import java.util.Random;
import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

public class Shuffle {

  // implement Knuth Shufflning:
  // randomly shuffle items in an array of Comparables
  // Run time is O(n) linear, scanning the array and exchange randomly
  public static void knuthShuffling (Comparable [] items) {
    Random rd = new Random();
    int randomIndex;
    for (int i=0; i<items.length; i++) {
      // generate a random number from 0 to i or from i to n-1
      // swap the ith item with the item at the random index:
      randomIndex = rd.nextInt(i+1); // (0, i)
      // randomIndex = i + rd.nextInt(items.length - i); // (i, N-1)
      exch(items, i, randomIndex);
    }
  }

  // O(nlogn) run time governed by the sort subroutine (Merge Sort)
  public static void randomSortShuffling(Comparable [] items) {
    // HashMap keyed with the random Integer number, and valued by the
    // Comparable items inside the array:
    HashMap<Integer, Comparable> hm = new HashMap<Integer, Comparable>();
    Random random = new Random();
    int key;
    for (int i=0; i<items.length; i++) {
      // for each item we want to generate a random key, no collision
      key = random.nextInt();
      while(hm.containsKey(key)) key = random.nextInt(); // generate a new random key if the generated key exists in the hash table
      // if key does NOT exist in the hash table, put the (key, value) record in the table:
      hm.put(key, items[i]);
    }

    // retrieve all keys and put them into a list:
    ArrayList<Integer> keyList = new ArrayList<Integer>(hm.keySet());  
    // sort the list: System sort is going to be Merge Sort O(nlogn)
    Collections.sort(keyList);

    // copy the values from Hash Table to the items array
    // following sorted random key order
    int index = 0;
    for(Integer k : keyList) {
      items[index] = hm.get(k);
      index++;
    }
  }

  private static void exch(Comparable [] items, int i, int j) {
    // NOTE: int [] cannot be converted to Comparable [], 
    // int is a primitive type and probably that's why...
    // Instead Integer wrapper type can be used and that
    // should work with Comparable objects
    Comparable temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  public static void main (String [] args) {
    Integer [] arrayInt = new Integer [] {1,2,3,4,5,6,7,8,9,10};

    for(int i=0; i<arrayInt.length; i++) System.out.print(arrayInt[i]+ " ");
    System.out.println();

    Shuffle.knuthShuffling(arrayInt);
    for(int i=0; i<arrayInt.length; i++) System.out.print(arrayInt[i]+ " ");
    System.out.println();

    Shuffle.randomSortShuffling(arrayInt);
    for(int i=0; i<arrayInt.length; i++) System.out.print(arrayInt[i]+ " ");
    System.out.println();

    // NOTE: int [] cannot be converted to Comparable [], 
    // int is a primitive type and probably that's why...
    // Instead Integer wrapper type can be used and that
    // should work with Comparable objects
  }
}

