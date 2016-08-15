import java.util.Comparator;
import java.util.Random;
import java.util.Arrays;

public class Sort {

  /* inp-lace sort an array of Comparable type using devide and conquer 
     algorithm design paradigm, and randomization to provide performance garauntee.
     
     BIG picture:
     unlike Merge Sort, quick sort does the partioning (main body of sort routine) 
     BEFORE making recursive calls. Use the correct position of the partiotioning element
     to break the sort problem into subproblems.

     Therefore, partitioning plays a major role in Qsort. In worst case if the partioning element
     end up at each endpoint of the array on all recursive calls, Quick sort will take QUADRATIC time N^2
     because of making N calls to the LINEAR-TIME partioning subroutine with input size O(n), and it takes
     total time of O(n^2).

     In the best case, being, the partioning routine placed the partitioning element to the middle (median) of the array
     Quick sort will be as quick as Merge Sort O(NlogN), and even faster, because of doing much less in the partitioning routine
     compare to merge routine.
  */

  public static void quick (Comparable [] items) {
    quick(items, 0, items.length-1);
  }

  public static void quick (Object [] items, Comparator comparator) {
    // recursive quick method:
    quick(items, 0, items.length-1, comparator);
  }

  // recursive (D&C) implementation of quick sort: in-place, with Linear time partition and random pivot
  private static void quick (Object[] items, int lo, int hi, Comparator comparator) {
    /* D&C approach to solve the problem: 
       1. Divde:
          Divde the problem to subproblem (base case: trival case--> solved)
       2. Conquer:
	  Solve subproblems recursively
       3. Combine (2-3 recurrence relation):
          Combine solutions of recursive call to solve the original problem
       T(problem) = a{number of subproblems}T(recursive calls) + T(Combine)
    */
     
    // 1. Always start with base cases: where to stop recursion (stop dividing problem to subproblems)
    // for subarrays of size 1 or less, stop dividing!
    if (hi<=lo) return ;

    // 2a. pick a pivot element from elements in the subarray, uniformly at random (randomness provides perfomance garauntee)
    Random random = new Random();
    int pivotIndex = random.nextInt(hi+1-lo)+lo; // random int within [lo, hi] inclusive
    // 2b. put the pivot element at the begining of the subarray:
    exch(items, lo, pivotIndex);

    // 3. partition the subarray from lo to hi using generic comparison defined by the comparator object:
    // put the pivot element at its rightful position, put <pivot on its left, and >pivot on its right (LINEAR, IN-PLACE), return the rightful position of the pivot
//    pivotIndex = partitionQuadratic(items, lo, hi, comparator);
    pivotIndex = partition(items, lo, hi, comparator);


    // 4. recursively sort elements on left and right of the pivot element:
    // quick sort, put one element at its rightful position on each recursive calls
    quick(items, lo, pivotIndex-1, comparator);
    quick(items, pivotIndex+1, hi, comparator);    
  }

  // Partitioining use the generic comparison implemented by the less routine, so we have to
  // pass the comparator object to the partitioning subroutine to enable it to compare two instances
  // using the generic comparison less routine:

  // LINEAR, IN-PLACE partition that puts PIVOT element at its rightful position, everything LESS on its left
  // and everythinng greater on its right. Returns the rightful position of the PIVOT (Always works fine by evenly distributing keys
  // equal to the PIVOT element on its both sides)
  private static int partition(Object[] items, int lo, int hi, Comparator comparator) {
    // pivot is at the begining of the subarray:
    Object pivot=items[lo];
    // two pointer at the begining and end of the subarray:
    int i, j;
    i=lo;
    j=hi+1;

    // iterate over the subarray
    while(true) {
      // move items less than pivot to left of i (unless reaching the end of the array)
      // STOPs at item equal to the pivot
      while(less(items[++i], pivot, comparator))
        if(i>=hi) break;
    
      // move everything that pivot is less than to the right of the j (unless you reach the beginning of the array)
      // STOPs at item equal to the pivot
      while(less(pivot, items[--j], comparator))
        if(j<=lo) break;

      // if subarray is partitioned already:
      if(j<=i) break;

      // evenly distribute keys equal to the pivot to its both sides
      // if pivot is equal to the item at i and/or j exchange them
      exch(items, i, j);
    }
    
    // put pivot at its rightful position
    exch(items, lo, j);
    return j; 
  }
  
  public static void dijkstraQuick(Object[] items, int lo, int hi, Comparator comparator) {
   // 1. base case: (stop dividing by recusion)
   if(hi<=lo) return ; // subarry has size one or less => is sorted

   // 2a. choose a pivot element in subarray UNIFORMLY at RANDOM (in order to provide performance garauntees)
   Random random=new Random();
   int pivotIndex=random.nextInt(hi+1-lo)+lo; // random index within [lo, hi] range
   // move the pivot to the beginning of the subarray:
   exch(items, lo, pivotIndex);

   /* 2b. Partition the subarray [lo hi]: 
      LINEAR IN-PLACE, put pivot and everything equals to pivot at their rigthtful position: everything less on left, everything greater on right:

      DIJKSTRA 3-way partitioning -> result in LINEAR sort for array of many duplicate keys
      Compare the pivot element with the element at lt:
      1. If item at i index is less than the pivot element:
         Exchange item at i with the item at lt and advance both pointers
      2. If pivot is equal to the item at i index: advance i (the pivot element does NOT change)
      3. if the item at index i is greater than the pivot element:
         Exchange it with the item at gt and advance gt backward:
      do NOT advance scanner index i because items[i] with new values has NOT been compared with the pivot yet
      pivot does NOT move, because the scanned item is NOT less than the pivot (lt index remains the same)
  */

   int i, lt, gt;
   i=lo; // scanner index starting from the begining
   lt=lo; // less than index, pointg to the pivot
   gt=hi; // greater than index, pointing the end of the subarray

   // while the scanner has not scanned the entire array:
   while (i<=gt) {
     if(less(items[i], items[lt], comparator))  exch(items, i++, lt++);
     else if (equal(items[i], items[lt], comparator)) i++;
     else // if items[i] is greater than the pivot: push it to the end of the subarray and move gt towards the beginning of the array
       exch(items, i, gt--);
   }

   // 3. recursive calls:
   // left of the pivot (keys less than the pivot)
   dijkstraQuick(items, lo, lt-1, comparator);
   // between lt-gt is sorted already (all equal keys)
   // right of the pivot (keys greater than the pivot)
   dijkstraQuick(items, gt+1, hi, comparator);

  } 


  // LINERAR partition subroutine that works fine ONLY for DISTINCT keys, and cause Quadratic performance for quick sort 
  // in presence of many duplicate keys:
  private static int partitionQuadratic(Object[] items, int lo, int hi, Comparator comparator) {
    Object pivot = items[lo];
    int i=lo; // index pointer characterizes the rightful position of the pivot (everything befoe i is "less than" pivot and everything after i is "greater or equal"
   
    // iterate through the subarray from lo+1 to to hi inclusive:
    for(int j=lo; j<=hi; ++j) {
      // 1. compare pivot element with the subarray element at j:
      // if items[j] were "less than" the pivot (using less routine powered that uses comparator instance to compare)
      if(less(items[j], pivot, comparator)) {
        // advance i index pointer:
        i++;
        // exchange items[j] with items[i]: anything at position i must be less than the pivot (if not pivot)
        exch(items, i, j);
      }
      // Do nothing otherwise: Because if items[j] is "greater or equal" to pivot it must be on its right
      // so the i should not move and exchange should not happen
    }
    
    // finally: put pivot at its rightful position i:
    exch(items, lo, i);
    return i;
  }

  // generic comparison routine:
  // returns true of v "is less than w", false otherwise ("greater or equal")
  private static boolean less(Object v, Object w, Comparator comparator) {
     return comparator.compare(v, w) < 0;
  }

  private static boolean equal(Object v, Object w, Comparator comparator) {
    return comparator.compare(v,w)==0;
  }

  private static boolean greater(Object v, Object w, Comparator comparator) {
    return comparator.compare(v,w)==0;
  }

  // exchange method for Object []
  private static void exch(Object[] items, int i, int j) {
    Object temp=items[i];
    items[i]=items[j];
    items[j]=temp; 
  }

  private static void quick (Comparable [] items, int lo, int hi) {
    // 1. Base Base: the smallest subproblem to solve (trivial case)
    if(hi<=lo) return ; // for one element, it is already sorted.

    /* for more than one item: when hi>lo
       2a. Random PIVOT:
           Choose a pivot element uniformly at random from the portion of the array
           that has been characterized by lo and hi in the recursive call:
    */ 
    Random random=new Random();
    int pivotIndex=random.nextInt(hi-lo+1)+lo; // generate a random number within [lo, hi] range
    exch(items, lo, pivotIndex); // move the pivot item to the "lo" index of the input array

    /*
       2b. PARTITIONING:
           Put the pivot element at its rightful position using a LINEAR time, IN-PLACE partitioning
           subroutine:
    */
    // pivotIndex = partitionQuadratic(items, lo, hi);
    pivotIndex = partition(items, lo, hi); // pivot initially placed at the "lo" position

     /*
       3. recursive calls around partitioning element to sort the entire array:
     */
      
      // left portion of the array
      quick(items, lo, pivotIndex-1);
      quick(items, pivotIndex+1, hi);
  }

  public static void dijkstraQuick(Comparable [] items, int lo, int hi) {
    // 1. Base case:
    if(hi<=lo) return ; // subarray of size 1 or less are already sorted

    // 2a. Pivot select (uniformly at random):
    Random random=new Random();
    int pivotIndex=random.nextInt(hi+1-lo)+lo; // an index within [lo, hi] range
    // place the random pivot at the beginning of the subarray:
    exch(items, lo, pivotIndex);

    // 2b. Partition the subarray [lo, hi] - Linear Time and In-place:
    /* Dutch National Flag partitioning (3-way partitioning) the pivot element and all keys equal to the
     pivot element in-place (all at once). 
     Therefore, results in Linear time sort (using random pivot or shuffling) where many keys are equal (many duplicate keys)
    */

    int i, lt, gt;
    // i scan through the subarray from lo to hi
    i=lo;
    // lt maintain the index of the pivot element (intially lo) and everything "less than" than the pivot element falls on lt's left
    lt=lo;
    // anything greater than the pivot element falls on gt's right (initially gt point to the last element of the subarray)
    gt=hi;

    /* scan the array from left to right and move i, lt, and gt accordingly:
        1. If the scanned item (items[i]) is less than the pivot: exchange items[i] and items[lt] and advace both i and lt
        2. else if the scanned item (items[i]) is greater than the pivot then exchange items[i] with items[j] and move j backward (j--), keep the i the same
        3. else (if the scanned item items[i] is equal to the pivot) advance the i pointer, to the next element, keep lt, and gt the same
        Invariance to be preserved: Everything before lt is "less than the pivot" and everything between lt and gt "is equal to" the pivot and everything after gt
        is "greater than the pivot. lt and gt pointing to the pivot at its rightful position:
    */

    // while we have not scanned the entire subarray:
    while(i<=gt) {
      // generic comparison between pivot element and the scanned element:
      // case 1: if scanned item is less than the pivot element:
      if(less(items[i], items[lt]))  exch(items, lt++, i++);
      // case 2: if scanned item is equal to the pivot element:
      else if (items[i].compareTo(items[lt])==0)  i++;
      // case 3: if scanned item is greater than the pivot element:
      else exch(items, i, gt--); // push the item greater than the pivot to the end of the subarray
    }

    // piovt is at its rightful position: lt
    // everything before lt is less than the pivot
    // everything greater than the pivot is after gt
    
    // 3. recursive calls:
    dijkstraQuick(items, lo, lt-1);
    // everything between lt-gt is already sorted!
    dijkstraQuick(items, gt+1, hi);

  }
     
  /* LINEAR time (governs by #of comparisons), IN-PLACE partitioning subroutine.
     NOTE: moving elements equal to the pivot element on its one side would result in
     QUADRATIC time Quick Sort if the array of items contains many DUPLICATE keys.
  */

  private static int partition(Comparable [] items, int lo, int hi){
    // pivot initially is placed at the items[lo];  
    
    // two pointers: one on the hi-th item, and one on the item right next to the pivot:
    int i=lo;
    int j=hi+1;

    Comparable pivot=items[lo];

    // move i forward, and j backward
    // use ++i, --j in order to keep advancing pointer even at times that
    // keys are equal to the pivot
    while(true) {
    
      // compare the pivot element to the element at the i-th position
      while(less(items[++i], pivot)) {
        // advance the i pointer: unless getting out of bounds
        if(i>=hi) break;
      }
      // compare the pivot element to the element at the i-th position
      while(less(pivot, items[--j])) {
        // advance the i pointer: unless getting out of bounds
        if(j<=lo) break;
      }

      if(j<=i) break; // the array is partitioned
      // exchange items at i and j, to evenly distributed 
      // items that equal to the pivot, to its both sides:
      exch(items, i, j);
    }    

    // pivot hase to move to its rightful position in the array:
    exch(items, lo, j);

    // return the rightful position of the pivot element:
    return j;
  }  

  private static int partitionQuadratic(Comparable[] items, int lo, int hi) {
     // pivot is at the "lo" positions:
     // compare all other elements with the pivot element:

     // pivot rightfull position:
     int i=lo; 
     Comparable pivot=items[lo];

     for(int j=lo; j<=hi; ++j) {
       // copmare items[j] with the pivot element:
       if(less(items[j],pivot)) {
         // if item[j] is less than pivot: it has to move to its left
         exch(items, j, ++i);
       }
     }
     // put pivot at its rightful position:
     exch(items, lo, i);
     // return the rightful position:
     return i;
  }

  // generic comparison method to generalize "<" ("less than" relation)
  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0; // " v is less than w" then return true
  }

  private static void exch(Comparable [] items, int i, int j) {
    Comparable temp = items[i];
    items[i]=items[j];
    items[j]=temp;
  }
   
   // sort clinet:
  public static void main(String[] args) {

    // Comparable type arrays:
    String [] strs = new String [] {"Shervin", "sheoosh", "shooosh", "yavash", "YUa", "A"};
    Sort.quick(strs);
    System.out.print(Arrays.toString(strs));
    System.out.println();

    strs = new String [] {"Shervin", "sheoosh", "shooosh", "yavash", "YUa", "A"};
    Sort.dijkstraQuick(strs, 0, strs.length-1);
    System.out.println("Dijkstra Dutch National Flag:");
    System.out.print(Arrays.toString(strs));
    System.out.println();

    System.out.println();
    strs = new String [] {"Shervin", "sheoosh", "shooosh", "yavash", "YUa", "A"};
    Arrays.sort(strs);
    System.out.print(Arrays.toString(strs));
    System.out.println();

    System.out.println();
    System.out.println();

    // Object arrays with specified Comparator instance:
    strs = new String [] {"Shervin", "sheoosh", "shooosh", "yavash", "YUa", "A"};
    Sort.quick(strs, String.CASE_INSENSITIVE_ORDER);
    System.out.print(Arrays.toString(strs));
    System.out.println();

    strs = new String [] {"Shervin", "sheoosh", "shooosh", "yavash", "YUa", "A"};
    Arrays.sort(strs, String.CASE_INSENSITIVE_ORDER);
    System.out.print(Arrays.toString(strs));
    System.out.println();

    strs = new String [] {"Shervin", "sheoosh", "shooosh", "yavash", "YUa", "A"};
    Sort.dijkstraQuick(strs, 0, strs.length-1, String.CASE_INSENSITIVE_ORDER);
    System.out.println("Dijkstra Dutch National Flag:");
    System.out.print(Arrays.toString(strs));
    System.out.println();

  }
}
