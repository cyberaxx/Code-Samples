import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;

import java.lang.IllegalArgumentException;

import java.util.Collections; // Class (Collection is an interface)
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.Stopwatch;

public class TwoSum {
  
  // check if all elements in array are distinct:
  public static HashSet<Long> distinctelements (long [] input) {
    // use a HashSet to takes all input elements as keys, if the set already contains a key we 
    // about to add to it, then that key must be a duplicate key: O(n)
    HashSet<Long> s = new HashSet<Long>();
    for(int i=0; i<input.length; i++) s.add(input[i]%10000); // this way we get rid of duplicates:
    return s;
  }

  // check if there exists a couple (x,y) such that x+y=t:
  public static boolean contains (HashSet<Long> s, long t) {
    for(Long item : s) // O(n)
      if(s.contains(t-item)) return true;
    return false;
  }

  // count the number couples in the input array in which their sume is equal to t (t in the interval [-10000,10000] (inclusive))
  public static int count (HashSet<Long> s) {
   int count = 0;
   for(long t=-1; t<=1; t++)
     // Stopwatch stopwatch = new Stopwatch();
     if (contains(s, t)) count++;
     // double time = stopwatch.elapsedTime();
     // System.out.println(time);
   return count;
  }

  public static void main (String [] args) throws IOException {
    FileReader fr = new FileReader ("2sum.txt");
    BufferedReader br = new BufferedReader (fr);
    String line;
    
    long [] input = new long [1000000];
    int index = 0;
    while( (line = br.readLine()) != null ) {
      input[index] = Long.parseLong(line);
      index++;
    }

    // find the distinct elements in the input array:
    HashSet<Long> s = distinctelements (input);
    System.out.println(s.size());
    System.out.println(count(s));

  }
}
