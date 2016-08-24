import java.util.Random;
import java.util.Comparator;
import java.io.File;


public class Quick {
  public static void sort(Comparable [] items, int lo, int hi) {
    // In-place sort, using a randomly chosen Pivot element:
    // Because of the randomness of pivot, the Running Time of
    // the subroutine on Average (Expected running time on worst case keys)
    // is going to be O(nlogn)

    // 1. Base case of the recurrence relation:
    if(hi<=lo) return;

    // 2. Randomized Combine phase:
    // Choose a random pivot in [lo,hi] range:
    Random rd = new Random();
    int randomPivot = rd.nextInt(hi-lo+1)+lo;
    
    // move the pivot element to the begining of the array:
    exch(items, lo, randomPivot);

    // Partition the Array arround the pivot element
    int sortedPivot = partition(items, lo, hi);

    // 3. Recursive calls: (Division)
    // Call quick sort on subarrays
    // reside on each side of the pivot element:
    sort(items, lo, sortedPivot -1);
    sort(items, sortedPivot +1, hi);

    // As you can see the worst case could be sorting
    // one subarray of size 1, and one subarray of size n-1
    // at each each recursive call. This scenario will end up
    // at QUADRATIC running time: O(n^2) which is BAD.

    // The best case would be choosing the pivot element, everytime
    // being the median of elements in the subarray of interst, then
    // at each recursive call we break down the array into 2 subarrays of
    // half size of the original array: t(n) = 2t(n/2) + O(n) for partitioning
    // Which similar to the merge sort, will end up being linearithmic O(nlong)

  }
  
  public static void sort (Comparable [] items) { sort(items, 0, items.length-1); }

  // partition the items array around its pivot element (Which has already been
  // placed at the items[lo] position).
  // return the rightful position of the pivot element:
  public static int partition (Comparable [] items, int lo, int hi) {
    // keep track of the part of items array at which items are smaller than the pivot element
    // Any element before i, is less than a pivot
    int i = lo + 1; 

    // scan the array from the element right after pivot to the hith element:
    // using j pointer to keep track of how much array we have scanned already
    for(int j=lo+1; j<=hi; j++) {
      // the items at the jth position is smaller than pivot:
      if(less(items[j], items[lo])) { // items[j] < items[pivot]
        exch(items, i, j);
        i++; // advance the ith pointer
      }
      // For items greater than the pivot item, do nothig,
      // keep i at its postion, because everything after i
      // is going to be greater than pivot
      // Do nothing otherwise: items[j]>items[pivot], just advance j pointer
    }
    // put pivot at its rightful position:
    exch(items, lo, i-1);
    return i-1;

    // the invariant that partition routine maintains is:
    // every elements of the array which got scanned is already
    // partitioned.
  }
  public static void exch (Comparable [] items, int i, int j) {
    Comparable temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  public static boolean less (Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  public static void main (String[] args) {
    File directory = new File(args[0]);
    File [] files = directory.listFiles();
    Quick.sort(files);
    for(int i=0; i<files.length; i++) System.out.println(files[i].getName());

  }
}
