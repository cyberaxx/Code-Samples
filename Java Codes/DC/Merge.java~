import java.util.Comparator;
import java.io.File;

public class Merge {
  // merge sort with comparator object as input
  private static void sort (Object[] src, Object[] dst, int lo, int hi, Comparator comparator) {
    // Base case: where to stop dividing problems to subproblem:
    // if subproblems are a size of 1, stop recurrsion:
    if (hi <= lo) return ; // subarray of size one hase been reached
    
    // Otherwise, find the index of the middle of the array:
    int mid = (hi+lo)/2;
    // divide the array to two subarrays from lo to mid and mid+1 to hi
    // and recursively solve subproblems by calling sort method:

    // 1. First Sort the LEFT-half subarray recursively
    sort(dst, src, lo, mid, comparator);
    assert isSorted(dst, lo, mid, comparator);
    // Now that the LEFT-half subarray is sort, then:

    // 2. Sort the RIGHT-half subarray recursively:
    sort(dst, src, mid+1, hi, comparator); // subarray of half size of the original array
    assert isSorted(dst, mid+1, hi, comparator);
    // Now that both left and right halves of the array are sorted, then:
    // merge the left half and the right half using the dst extra linear space
    // to have sorted array from lo-hi:  
    
    // check if there is no need to merge by checking:
    // TO PRESERVE STABILITY, KEEP IN MIND THAT EVERYTHING IN THE left half
    // THAT IS smaller or equal TO ANYTHING ON THE right half, MUST STAY AT
    // ITS POSITION ON THE left half, with no change
    
    // if the SMALLEST element in the SORTED RIGHT half (src[mid+1])
    // is NOT LESS (so it's is larger or EQUAL) than  the LARGEST element in SORTED LEFT half (src[mid])
    // then there is no need to merge, cause the array is already sorted from lo-hi:
    if( !(less(src[mid+1], src[mid], comparator)) ) {
      System.arraycopy(src, lo, dst, lo, hi-lo+1); // copy form lo-hi inclusive, it has to copy over hi-lo+1
      return ;
    }
    // otherwise, preform the merge subroutine:

    // 3. Combine step of D&C approach where we combine solutions of subproblems
    // together to solve a problem for a bigger subproblem:
    // Merge two sorted subproblems (subarrays) into one arry of double size:
    merge(src, dst, lo, mid, hi, comparator);
    assert isSorted(src, lo, hi, comparator);


    // NOTE: T(n) = 2T(n/2) + O(n), T(1) = 1; 
    // {Base case: T(1) = 1 // Base Case: one element is already sorted (Stop dividing n to subproblems of size n/2)};
    // Recursive calls: 2T(n/2) // Recusrive Divide: Two recursions, one the left subarray of size n/2, and one on right subarray of size n/2 (left half, right half)
    // Combine: O(n), linear work done by the merge subroutine to combine solution of subproblems, to solve the problem of size 2*size of the subproblems
    // Master Method Case 2:
    // Force of Evil: Branching factor a=2
    // Force of Good: Division Factor b^d = 2
    // a = b^d => T(n) = O(nlogn)
  }
 
  private static void merge (Object[] src, Object[] dst, int lo, int mid, int hi, Comparator comparator) {
    // For an array of size N, each call to the merge subroutine, costs:
    // N COMPARES:
    // One compare inside the for loop

    // 6N ARRAY ACCESSES:
    // 2 array accesses in side the for loop for copying, and 4 array accesses inside the for loop for merging 

    // The merge subroutine is going to be called logN times during sort operation, so the TOTAL cost of Merge sort is:
    // NlogN compares, and 6NlogN array accesses 

    // we must have to sorted subarrays:
    assert isSorted(src, lo, mid, comparator); // LEFT
    assert isSorted(src, mid+1, hi, comparator);  // RIGHT

    // one index pointer on the first subarray
    int i = lo;
    // one index pointer on the second subarray
    int j = mid+1;

    // We want to produce a sorted array from index lo to index hi:
    for(int k=lo; k<=hi; k++) { 
      // if the first subarray is exhuasted: i > mid read from the second subarray:
      if(i>mid) dst[k] = src[j++]; // advance the index pointer at the second subarray:
      // if the second subarray gets exhuasted: j > hi read from the first subarray
      else if (j>hi) dst[k] = src[i++]; // advance the index pointer at the first subarray
      // if the element at the j-th index of the second subaaray is less than
      // the item at the i-th index of the first subarray:
      else if (less(src[j], src[i], comparator)) dst[k] = src[j++]; // read from the second subarray and advance the j index pointer
      // otherwise read from the first subarray: dst[i] < dst[j]
      else dst[k] = src[i++]; // advance the index pointer at the second subarray.
    }
    assert isSorted(dst, lo, hi, comparator);
  }

  public static void sort (Object [] src, Comparator comparator) {
    // set aside extra space, and copy all the elements of the original array there:
    // creates and returns a copy of src array (which itself is an array of Objects), similar to System.arraycopy(src, ..., dst, ...)
    // clone() does NOT clone the reference type within the array, if src array is an array of Objects, clone copy the reference to those Object array
    // in dst array:
    Object[] dst = src.clone();
    sort(dst, src, 0, src.length-1, comparator); 
    assert isSorted(src, comparator);
  }

  private static boolean isSorted(Object[] items, Comparator comparator) {
    return isSorted(items, 0, items.length-1, comparator);
  }

  private static boolean isSorted(Object[] src, int lo, int hi, Comparator comparator) {
    // iterate through an array of object from left to right, and check if src are in ascending order 
    // by invoking the less method:
    for(int i=lo+1; i<=hi; i++)
      if (less(src[i], src[i-1], comparator)) return false;
    return true;
  }

  private static boolean less (Object v, Object w, Comparator comparator) {
    return comparator.compare(v,w) < 0;
  }

  private static void exch (Object[] src, int i, int j) {
    Object temp = src[i];
    src[i] = src[j];
    src[j] = temp;
  }

  // Sort subroutine:
  // Recursive method, using an Array of Comparable objects as input, 
  // Since the output is an Array of Comparable objects, we MUST avoid,
  // Creating arrys within recursive calls, rather, pass the entire Array to 
  // each call, and work on different portion of it, using lo and hi indeces:
  private static void sort (Comparable [] src, Comparable [] dst, int lo, int hi) {
    // base case:
    // Dividing phase: array halving
    if(lo>=hi) return; // stop when the lo pointer passes the hi pointer
    int mid = (lo+hi)/2; // find the middle of the array
    
    // Recursive calls:
    // on the left subarray:  
    sort(dst, src, lo, mid);
    // on the right subarray:
    sort(dst, src, mid+1, hi);
    // dst array always maintain isSorted invariance
    // within its boundaries
    
    // Check if the lo-hi array is already sorted, 
    // no need to merge:
    // if the SMALLEST element in the SORTED RIGHT half (src[mid+1])
    // is NOT LESS (so it's is larger or EQUAL) than  the LARGEST element in SORTED LEFT half (src[mid])
    // then there is no need to merge, cause the array is already sorted from lo-hi:
    if (!less(src[mid+1], src[mid])) {
      // faster than for loop, cause its implementation
      // is closer to the java interpreter:
      System.arraycopy(src, lo, dst, lo, hi-lo+1); // copy from src array from lo index, to dst array from its lo index, hi-lo+1 elements
      return ;
    }

    // Combine the results of recursive calls:
    // this step is little mind bending:
    // we call merge on (src, dst, ....) unlike the sort(dst, src, ...)
    // then use src array as an dst array to populate the dst array in sorted oreder 
    // in O(n) time by scanning src array from lo to hi:
    mergeCombine(src, dst, lo, mid, hi);

    // Invariant: The dst array is ALWAYS sorted within left and right boundaries
  }

  // O(n) for combining the result of divisions using Divide and Conquer approach  
  private static void mergeCombine (Comparable [] src, Comparable [] dst, int lo, int mid, int hi) {
    // This method would merge two sorted subarryas:
    // First sorted subarray is from lo-mid
    assert isSorted(src, lo, mid);
    // Second sorted subarray is from mid+1-hi
    assert isSorted(src, mid+1, hi);

    // And merge them into a sorted subarray from lo-hi
    int i=lo; // pointer index on left subarray;
    int j=mid+1; // pointer index on the right subarray
    
    // merger from lo-hi (inclusive)
    for (int k=lo; k<=hi; k++) {
      // Trivial cases: if either of subarrays has been exhauseted:
      // read from the other one:
      // 1. if the left subarray has been exhausted:
      if(i>mid) dst[k] = src[j++]; // read from the right subarray and advance its pointer
      // 2. if the right subarray has been exhausted:
      else if(j>hi) dst[k] = src[i++]; // read from the left subarray and advance its pointer

      // if the item at the j-th position at the right subarray was 
      // less than an item on the i-th position of the left subarray
      // read from the right subarray and advance its pointer
      else if(less(src[j], src[i])) dst[k] = src[j++];
   
      // if none of the conditions above holds, then 
      // read from the left subarray and advance its pointer
      else dst[k] = src[i++];
    }
    assert isSorted(dst, lo, hi);
  }

  public static void sort (Comparable [] src) {
    // Comparable [] dst = new Comparable [src.length];
    // Create a aux array and put a copy of src array in it:
    Comparable [] dst = src.clone(); // clone() method, creates and returns a copy of src Comparable Array object.
    sort (dst, src, 0, src.length-1);
    assert isSorted(src);
  }

  private static boolean less (Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }
  
  private static boolean isSorted (Comparable [] src, int lo, int hi) {
    for(int i=lo+1; i<=hi; i++) 
      if(less(src[i], src[i-1])) return false;
    return true;
  }

  private static boolean isSorted (Comparable [] items) {
    return isSorted(items, 0, items.length-1);
  }

  // Let's write a sort client for the Selection sort:
  public static void main (String [] args) {
    // Sort with Comparable example:
    File directory = new File (args[0]);
    File [] files = directory.listFiles();
    // sort the array of files
    Merge.sort(files);
    for(int i=0; i<files.length;i++) System.out.println(files[i].getName());

    System.out.println();

    // Sort with Comparator example:
    String [] str = {"Shervin", "sas", "Nice", "dUde", "Dude"};
    // sort the array of files
    Merge.sort(str, String.CASE_INSENSITIVE_ORDER);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);

    System.out.println();

    // Sort with Comparable example:
    Merge.sort(str);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);
  }

}
