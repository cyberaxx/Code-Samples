import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.Arrays; // We want to implement the Arryas.binarySearch(int [] a, int item)
import java.util.Collections;
import java.util.ArrayList;

public class BinarySearch {
  // the binarySearch function, returns the index of the item if it was found, -1 otherwise:
  public static int binarySearch (ArrayList<Integer> input, int item) {
    // The input is a sorted list:
    int lo, hi, mid;
    // Intialize lo and hi pointer to point to the begining and the end of the list:
    lo=0; hi=input.size()-1;
    while (lo <= hi) { 
      // initialize the mid value to the middle of the list/sublist:
      mid = (lo+hi)/2;
      // search on its left:
      if ( item < input.get(mid) ) hi = mid - 1; // lo stays the same
      // search on its right:
      else if ( item > input.get(mid) ) lo = mid + 1; // hi stays the same
      else // it has to be equal, then return the index
        return mid;
    } 
    return -1; // the item has not been found
  }

  public static int recursiveBinarySearch (ArrayList<Integer> input, int lo, int hi, int item) {
    // base case:
    if (lo > hi) return -1;

    // while lo < hi
    else {
      int mid = (lo+hi)/2;
      if (item < input.get(mid)) return recursiveBinarySearch (input, lo, mid - 1, item); // searching the left
      else if (item > input.get(mid)) return recursiveBinarySearch (input, mid + 1, hi, item); // searching the right
      else return mid;
    }
  }

  // Whenever you are using file handlers do NOT forget to handling IOException
  public static ArrayList<Integer> fileScanner (String fileName) throws IOException {
    ArrayList<Integer> list = new ArrayList<Integer>();
    FileReader fr = new FileReader (fileName);
    BufferedReader br = new BufferedReader (fr);
    String line;
    while ( ( line = br.readLine() ) != null)
      list.add( Integer.parseInt( line.trim() ) );
    // must close the BufferedReader after you are done with the br object:
    br.close();

    return list;
  } 

  // Whenever you are using file handlers do NOT forget to handling IOException
  public static void main(String [] args) throws IOException {
    ArrayList<Integer> needles = fileScanner ("tinyW.txt");
    ArrayList<Integer> haystack = fileScanner ("tinyT.txt");
    // Caution: if too many duplicates exist, inefficient Qsort may take O(n^2) 
    // Sort elements in the haystack (can have duplicates) O(nlogn)
    Collections.sort(haystack); 
    // Print out the entire list:
    // System.out.println(haystack);

    int index;
    // for each element in the needles list, run binarySearch on the haystack list
    for(Integer needle : needles) {
      if ( ( index = binarySearch(haystack, needle) ) >= 0)
        System.out.println(index);
    }

    System.out.println();

    for(Integer needle : needles) {
      if ( ( index = recursiveBinarySearch(haystack, 0, haystack.size()-1, needle) ) >= 0)
        System.out.println(index);
    }
  
  }
}
