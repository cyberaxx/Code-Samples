import java.util.Random;

public class Select {
  // find the k-th smallest element in the array:
  // assuming there were no duplicate elements in the array: Elements are DISTINCT for simplicity
  // duplicate items can be simply removed in O(n) using HashSet
  public static Comparable kSelect(Comparable [] items, int k, int lo, int hi) {
    // BASE CASE IS WRONG!!!!!!
    // base case: for recursion
    if(hi<=lo) return items[lo];

    // Pick a random pivot in [lo,hi] range:
    Random random = new Random();
    int randomPivot = (int) (Math.random()*(hi+1-lo) + lo);

    // move the random pivot to the first position in the 
    // the input array:
    exch(items, lo, randomPivot);

    // Partion the input array around the random pivot 
    int pivotSorted = partition(items, lo, hi);

    if (pivotSorted == k) return items[pivotSorted];
    else if (pivotSorted > k) return kSelect(items, k, lo, pivotSorted-1);
    else return kSelect(items, k, pivotSorted+1, hi);

  }

  public static Comparable kSelect(Comparable [] items, int k) { return kSelect(items, k, 0, items.length-1); } 

  public static int partition (Comparable [] items, int lo, int hi) {
    // partition the array of items around its pivot element (items[lo]):
    // i keeps track of separation between elements < pivot and elements > pivot:
    int i = lo+1; // point to the next elemnt on the right side of the pivot
    // j scans the array from lo+1 to hi (left to right)
    for(int j=lo+1; j<=hi; j++) {
      // if at some point items[j]<items[pivot]:
      // j found itself pointing to an item less than the pivot item:
      if (less(items[j], items[lo])) {
        // swap items[j] with items[i] and 
        // advance i to the next element
        exch(items, i, j);
        i++;
      }
      // Do nothing if items[j] > items[lo], keep the i at its previous position
    }
    // put the pivot element at its rightful position:
    exch(items, lo, i-1);
    // return the rightful position of the pivot element:
    return i-1;
  }
  public static void exch (Comparable [] items, int i, int j) {
    Comparable temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  public static boolean less (Comparable v, Comparable w) {
     return v.compareTo(w) < 0;
  }

  public static void main (String [] args) {
    Integer [] a = new Integer [] {1,2,3,4,5,6,7,8};
    System.out.println(Select.kSelect(a,0));
    System.out.println(Select.kSelect(a,1));
    System.out.println(Select.kSelect(a,2));
    System.out.println(Select.kSelect(a,3));
    System.out.println(Select.kSelect(a,4));
    System.out.println(Select.kSelect(a,5));
    System.out.println(Select.kSelect(a,6));
    System.out.println(Select.kSelect(a,7));
  }
}
