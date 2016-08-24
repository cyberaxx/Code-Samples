import java.io.File;

public class BinaryInsertion {

  // implement the Insertion sort routine such that,
  // the outer loop (index i) scans the array from left to right,
  // at each position j, since the subarray 0-j has to be sorted (invariant that insertion sort must maintain)
  // use Binary Search to find the rightful position of the item j, call it "pos", 
  // move everything after position "pos", one position to the right, put the j element
  // in the pos position and go ahead and advance i index to the next element:
  

  // NOTE: Improvement vs typical Insertion Sort
  // Binary Insertion sorts improves the worst case number of COMPAREs from N^2 (quadratic) to NlogN

  // while still have N^2 (quadratic number of exchanges) -> to put each element at its rightful position 
  // it has has to move all elements after a certain index in the array one position to the right (~N moves for each element)
  // ~~ N^2 for N elelemts;

  public static void sort (Comparable [] items) {

    // temp keeps a copy of the item that is to in-placed:
    Comparable key;
    int lo, mid, hi;

    // we start scanning the array from left to right 0toN-1
    for(int i=1; i<items.length; i++) {
      // take a copy of the item that is ready to 
      // get into its rightful position in the array
      key = items[i];

      // find the rightful position for the "key"
      // using binary search in 0-i range
      // because 0-i must be sorted
      lo=0; hi=i;
      while(lo<hi) {
        // find the middle
        mid = (lo+hi)/2;
        // conditional search on left or right half of the array:
        if(less(key, items[mid])) hi=mid-1; // have to search the left half
	// else if (!less(key, items[mid])):
        else lo=mid+1; // have to search the right half
      }

      // we have to move elements after index "lo" one position to the right:
      // if you write it this way:
      // for(int j=lo+1; j<items.length-1; j++)
      // then everytime you are moving one position to the write, you are copying over another
      // item of the array, so instead you have to strat from the current location of the key 
      // (index i) to index "lo" (exclusive) and copy over:
      for (int j=i; j>lo; --j)  // --j because we are already at i-th index and we do not want need its value, we already copied that in key
        items[j] = items[j-1]; // copy from left to right

      // now that open up a position for "key", put it at its rightful position:
      items[lo] = key;
    }
  }

  // java built-in reference tpyes (such as String, Double, Integer, ...) are subtypes of the Comparable (as a supertype)
  // meaning they have implemented Comparable interface by providing their own implementation of compareTo method for their
  // corresponding instances.

  // It's a good practice, to have a function to compare to instances of a class in a general sense by
  // invoking comapreTo method on instances of the corresponding class:
  public static boolean less (Comparable v, Comparable w) {
    return v.compareTo(w) < 0; // if v and w are not compatible or one of is null, then the compareTo method would throw exception 
  }

  // Let's write a sort client for the Selection sort:
  public static void main (String [] args) {
    // Sort with Comparable example:
    File directory = new File (args[0]);
    File [] files = directory.listFiles();
    // sort the array of files
    BinaryInsertion.sort(files);
    for(int i=0; i<files.length;i++) System.out.println(files[i].getName());

    System.out.println();

    // Sort with Comparator example:
    String [] str = {"Shervin", "sas", "Nice", "dUde", "Dude"};
    // Sort with Comparable example:
    BinaryInsertion.sort(str);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);
  }

}
