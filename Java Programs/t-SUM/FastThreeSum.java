import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.lang.IllegalArgumentException;


public class FastThreeSum {

  public static int count (int [] input) {
    // if the input array includes duplicates then return -1:
    // if (containsduplicate(input)) return -1;
    if (containsduplicate(input)) throw new IllegalArgumentException ("The input array contains duplicates!");
    // sort the array of numbers
    Arrays.sort(input);
    int count = 0;
    int temp;
    for (int i=0; i<input.length; i++) {
      for (int j=i+1; j<input.length; j++) {
        temp = (-1)*( input[i] + input[j] );
        // we are looking for temp in the array from j+1 index to the end of the array
        if ( Arrays.binarySearch(input, temp) > j ) count++;
      }
    }
    return count;
  }

  public static boolean containsduplicate (int [] input) {
   // read from input and put it into a hash table, keep track of the number of occurence as value
   // if number of keys is equal to the length of array, all inputs are distinct: O(n)
   // The O(nlong) approach would be sorting elements, and loop through the array and find two consecutive elements that are equal
   // HashMap<Integer, Short> hm = new HashMap<Integer, Short>();
   Set<Integer> s = new HashSet<Integer>(); // Set is an interface, HashSet implements Set interface for HashMap instances
   for (int i=0; i<input.length; i++) {
     // keep track of number of occurence:
     if(s.contains(input[i])) return true;
     else s.add(input[i]);
   }
   return false;     
  }

  public static void main (String [] args) throws IOException {
    FileReader fr = new FileReader ("8Kints.txt");
    BufferedReader br = new BufferedReader (fr);
    String line;
    int [] input = new int [8000];
    int index = 0;
    // populating the input array from the input file:
    while ( (line = br.readLine()) != null ) {
      input[index] = Integer.parseInt(line.trim());
      index++;
    }
    
    System.out.println(count(input));
    
  }
}

