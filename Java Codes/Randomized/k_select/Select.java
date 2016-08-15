import java.util.Random;
import java.util.Arrays;

public class Select {

  /* Select K-th smallest, K-th largest element of the array:
     Approaches:
     1. Sort array (NlogN)-(N^2) and return the K-th item
     2. Quick Select with random pivot -> O(n) {without a random pivot it may take O(n^2) [min/max as a pivot at each recursive call]}
     T(N) = T(size of subproblem) + O(N){for partitioning}
  */
 
  public static Comparable selection(Comparable[] items, int k) {
    if (k<1 || items.length==0) return null;
//    return items[recursiveSelection(items, 0, items.length-1, k-1)]; // arrays are 0-base we need to compute k-1
    return items[selection(items, 0, items.length-1, k-1)]; // arrays are 0-base we need to compute k-1
  } 


  public static Comparable recusriveDijkstraSelection(Comparable [] items, int k) {
     if (k<1 || items.length==0) return null;
     return items[recusriveDijkstraSelection(items, 0, items.length-1, k-1)];
  }

  private static int recusriveDijkstraSelection(Comparable [] items, int lo, int hi, int k) {
    // 1. base case: (a point to stop division): one item or less
    if(hi<=lo) return lo;

    // 2a. Pivot selection: choose a random pivot with range of the subarray [lo hi] inclusive
    Random random=new Random();
    int pivotIndex=random.nextInt(hi+1-lo)+lo;
    exch(items, lo, pivotIndex); // ready for partitioning:

    // 2b. Partitioning: use 3-way partitioning to put inplace pivot and all keys equal to the pivot (all at once)
    int i, lt, gt;
    i=lo; // scanner pointer (initially at the left end of the subarray) 
    lt=lo; // less than pointer (initially and alway point to the pivot element)
    gt=hi; // greater than pointer (initially at the right end of the subarray)

    // while all items has not been scanned yet:
    while(i<=gt) {
      if(items[i].compareTo(items[lt])<0)  exch(items, i++, lt++);
      else if(items[i].compareTo(items[lt])>0) exch(items, i, gt--);
      else i++;
    }

    // 3. recursive call:
    if(k>=lt && k<=gt)  return lt; // if k is within [lt gt] range, we found it!
    else if(k<lt) return recusriveDijkstraSelection(items, lo, lt-1, k);
    else // if k>gt
      return recusriveDijkstraSelection(items, gt+1, hi, k);
    
  }

  // Dutch National Flag partitioning in Selection (randomized)
  public static Comparable dijkstraSelection(Comparable [] items, int k) {
    if(k<1 || items.length==0) return null;

    // Random Pivot selection
    Random random=new Random();
    int pivotIndex;
    Comparable pivot;

    // Partitioning
    int i, lt, gt;

    // pointers to array boundaries:
    int lo=0;
    int hi=items.length-1;

    // scanning the array while hi>lo:
    while(hi>lo) {
      // choose a random pivot with the range [lo hi]
      pivotIndex=random.nextInt(hi+1-lo)+lo;
      exch(items, lo, pivotIndex);
      
      // Partition subarray [lo hi] w.r.t. the pivot element
      // Invariance to be preserved (for all observed items):
      // Anything less than the pivot is on lt's left, anything greater than the pivot is on gt's right, and anything equal to the pivot is between lt and gt
      pivot=items[lo];
      i=lo; // scanner
      lt=lo; // less than pointer
      gt=hi; // greater than pointer
      while(i<=gt) {
        if(items[i].compareTo(pivot)<0) exch(items, lt++, i++);
        else if(items[i].compareTo(pivot)>0) exch(items, i, gt--); // push the scanned item to the right end of the array and advance gt
        else // if pivot is equal to the scanned item, advance the scanner and preserve the pivot index lt
          i++;
      }

      // subarray is partitioned:
      // 1. if k is within [lt gt]
      if(k>=lt && k<=gt) return items[lt]; // return the pivot
      // 2. if k is less than lt: search on lt's left
      else if(k<lt) hi=lt-1;
      else  // if k> gt search on gt's right:
        lo=gt+1;
    }
 
    return items[lo];
    
  }
  
  private static int selection(Comparable[] items, int lo, int hi, int k) {
    Random random=new Random();
    int pivotIndex;
    // works on different portion of the array    
    while(hi>lo) {
      // choose a random pivot within [lo hi] inclusive range:
      pivotIndex=random.nextInt(hi+1-lo)+lo;
      // place the pivot at the beginning of the subarray
      exch(items, lo, pivotIndex);

      // Partition the subarray w.r.t. pivot element:
      pivotIndex=partition(items, lo, hi);
      
      // check if the rightful position of the pivot (random) is equal k:
      if(pivotIndex==k) return pivotIndex; // the index of the pivot element in the sorted array
      else if (k>pivotIndex) {
        // update subarray boundaries:
        lo=pivotIndex+1;
      }
      else // if k<pivotIndex
        hi=pivotIndex-1;
    }
    return lo;
  }

  public static void shuffle(Comparable[] items) {
    Random random=new Random();
    int newIndex;
    int hi=items.length-1;
    // scan the array from left to right and swap the item at the i-th position with item at any position form after it:
    for(int i=0; i<items.length; i++) {
      newIndex=random.nextInt(hi+1-i)+i;
      // check if the random index is not the current index i
      if(newIndex!=i)  exch(items, i, newIndex);
    }
  }

  public static int recursiveSelection(Comparable[] items, int lo, int hi, int k) {
    // 1. base case: subarray of size 1 or less (are trivial)
    if(hi<=lo) return lo; // success! found the k-th smallest
 
    // 2a. random pivot (the item chosen to get in-placed) within [lo hi] range
    Random random=new Random();
    int pivotIndex=random.nextInt(hi+1-lo)+lo;
    // move the pivot to the beginning of the array
    exch(items, lo, pivotIndex);

    // 2b. Partition the subarray:
    /* put the pivot element at its rightful position (before recursive calls)*/
    pivotIndex=partitionQuadradtic(items, lo, hi);

    // 3. recursive calls:
    if(pivotIndex==k) return pivotIndex; // k-th smallest found!
    else if(pivotIndex>k) return recursiveSelection(items, lo, pivotIndex-1, k);
    else // if pivotIndex<k
      return recursiveSelection(items, pivotIndex+1, hi, k);
  }

  // Linear time inplace partitioning subroutine:
  // place pivot element at its rightful position and everything less than pivot on its left, everything greater than the pivot on its right

  private static int partition(Comparable[] items,int lo, int hi) {
    /* 1. partition the arry w.r.t to the pivot element
       2. put pivot at its rightful position 
       3. return the pivot index
       Invariance: For all observed items in the subarray: everything less than pivot is on its left, and everything greater than pivot is on its right.

    */
    Comparable pivot=items[lo];
    // scanner from left to right i (for items less than the pivot)
    int i=lo;
    // scanner from right to left j (for items greater than the pivot)
    int j=hi+1;

    while(true) {
      // scanned item at i is less than the pivot: this items must fall on pivot's left side, so keep forwarding i to make a room for the item
      while(less(items[++i], pivot))
        if(i>=hi) break; // out of bounds

      // start scanning from the end of the subarray:
      // while the pivot element is less than the scanned item at j, advance j (to make room for the item on the right of the pivot)
      while(less(pivot, items[--j]))
        if(j<=lo) break;

      // check if i and j passed each other:
      if(j<=i) break; // we are done: the array is partitioned!
      // if not:
      // item at i and j are equal to the pivot element:
      exch(items, i, j); // to evenly distribute items on both sides of the pivot
    }
    
    exch(items, lo, j);
    return j;
  }

  private static int partitionQuadradtic(Comparable[] items, int lo, int hi) {
    // pivot element initially place at the beginning of the subarray:
    Comparable pivot=items[lo];
    int i=lo; // i pointer always point to the pivot rightful (wrt observed portion of the subarray)

    // 1. PARTITION the subarray w.r.t PIVOT element:
    // scann the subarray from left to right inclusive (j is the scanner pointer)
    for(int j=lo; j<=hi; ++j) {
      // if the scanned item is less than the pivot: move i to right (to make a room on pivot's left, for this item)
      if(less(items[j], pivot)) {
        i++; // preserve the pivot index invariance // everything less than pivot reside before i index
        exch(items, i, j);
      }
    }
    // CAVEAT: this approach works poorly for partitioning array of keys with many duplicate, because it passes keys equal to the partitioning element to its 
    // one side, peel off on item from the subarray, and keep recurssing on them without doing much in terms of putting those duplicate keys inplace.

    // 2. PUT pivot IN-PLACE:
    // put pivot inplace and return its rightful position:
    exch(items, lo, i);
    return i;

  }

  // generic comparison method for Comparable type:
  // generic "less than":
  private static boolean less(Comparable v, Comparable w) {return v.compareTo(w)<0;}
  // swap method for partitioning subroutine:
  private static void exch(Comparable[]items, int i, int j) {
    Comparable temp=items[i];
    items[i]=items[j];
    items[j]=temp;
  }

  // selection cliet:
  public static void main(String[] args) {
    Integer [] items = new Integer[]{4, 4, 5, 1, 2, 2, 3, 3, 3, 4, 5, 6, 8, 0, 0, 0};
    System.out.println(selection(items, 4));
    System.out.println(selection(items, 6));
    System.out.println(selection(items, 8));

    System.out.println("Original array:");
    System.out.print(Arrays.toString(items));
    System.out.println();

    shuffle(items);

    System.out.println("Shuffled array:");
    System.out.print(Arrays.toString(items));
    System.out.println();
    System.out.println(dijkstraSelection(items, 3));
    System.out.println(dijkstraSelection(items, 5));
    System.out.println(dijkstraSelection(items, 7));
    System.out.println();

    System.out.println();
    items = new Integer[]{4, 4, 5, 1, 2, 2, 3, 3, 3, 4, 5, 6, 8, 0, 0, 0};
    System.out.println(recusriveDijkstraSelection(items, 4));
    System.out.println(recusriveDijkstraSelection(items, 6));
    System.out.println(recusriveDijkstraSelection(items, 8));

    System.out.println();
    Arrays.sort(items); // sort the array
    System.out.println("Sorted array:");
    System.out.print(Arrays.toString(items));
    System.out.println();

    System.out.println(items[3]);
    System.out.println(items[5]);
    System.out.println(items[7]);    
  }
}
