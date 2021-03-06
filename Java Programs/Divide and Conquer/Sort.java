public class Sort {
  /*
    1. Recursive Merge Sort
    2. Iterative Merge Sort
    3. Inversion count
  */

  public static void sort(Comparable[] items) {
    // linear extra space for merge operation:
    Comparable[] aux=new Comparable[items.length];
    sort(items, aux, 0, items.length-1);
  }

  // iterative merge sort:
  public static void sortBU(Comparable [] items) {
    // aux array of same size:
    Comparable[] aux=new Comparable[items.length];

    int mid, hi;
    // for subproblem of size 1, 2, 4, and higher to N (items.length)
    for(int subSize=1; subSize<items.length; subSize*=2) { // this for loop runs logN times (each time double the size of the subproblem
      for(int lo=0; lo+subSize<items.length ; lo=lo+2*subSize) {
        // merge lo-lo+subSize with lo+subSize -to- lo+2*subSize
        mid=lo+subSize-1;
        hi=Math.min(lo+2*subSize-1, items.length-1); // check if hi is not out of array bounds
        merge(items, aux, lo, mid, hi);
      }
    }
  }

  // given an array of Comparable objects, this method returns how many elements are out of place
  public static int inversionCounter(Comparable[] items) {
    // non-distructive implementation:
    Comparable[] copy=new Comparable[items.length];
    System.arraycopy(items, 0, copy, 0, items.length); // copy the original array
    Comparable[] aux=new Comparable[items.length]; // aux array

    // recursive helper method using the copy array:
    return inversionCounter(copy, aux, 0, items.length-1);
    
  }

  // recursive method: 1. Base case, 2, Recurrence:   
  private static int inversionCounter(Comparable[] items, Comparable [] aux, int lo, int hi) {
    // BASE CASE:
    // for subarrays of size 1 or less -> 0 inversions
    if(hi<=lo) return 0;

    // RECURRENCE (D&C): For subarrays of size 2 and more
    // Divide: find the middle element of the subarray
    int mid=(hi+lo)/2;
    
    // Conquer: count the number of left-half subarray and right-half subarray recursively:
    int leftInversions=inversionCounter(items, aux, lo, mid); // [lo mid]
    int rightInversions=inversionCounter(items, aux, mid+1, hi); // [mid+1 hi]

    // Combine: count the number of split inversion (inversions within subarray [lo hi] after merging [lo mid] with [mid+1 hi]
    int splitInversions=mergeAndCount(items, aux, lo, mid, hi);

    return leftInversions+rightInversions+splitInversions;
  }

  // merge two sorted subarrays and count the number of split inversions (number of out of place items BEFORE merging two subarrays)
  private static int mergeAndCount(Comparable [] items, Comparable [] aux, int lo, int mid, int hi) {
    // copy from original array to the aux array before sort: [lo hi], hi-lo+1 items
    System.arraycopy(items, lo, aux, lo, hi-lo+1);

    int i=lo; // left-half pointer
    int j=mid+1; // right-half pointer
    int counter=0; // inversion counter: initialized to 0
   
    // scann aux array from lo to hi (inclusive) and populate items array and count inversions
    for(int k=lo; k<=hi; k++) {
      // as long as left subarray is used to copy over to the original items we are good, no inversions
      if(i>mid) items[k]=aux[j++];
      else if(j>hi)  items[k]=aux[i++];
      else if(less(aux[j], aux[i])) {
        items[k]=aux[j++];
        counter=counter+(mid-i+1); // add the number of element remaining in the left subarray to the counter
      }
      else items[k]=aux[i++];
    }

    return counter;
  }

  // recursive method on a reference type input: we need to write a helper method that works on different portion of the reference type input:
  // RECURSIVE APPROACH: 1. Base case, 2. Recurrence (Divide and Conquer)
  private static void sort(Comparable[] items, Comparable[] aux, int lo, int hi) {
    //  BASE CASE: 
    // where to stop dividing: subarrays of size 1 or less
    if(hi<=lo)  return ;

    // RECURRENCE: for subarrays of size 2 or more
    // NOTE: merge sort does all its recusive call before putting an item at its rightful position (via the merger operation):
    
    // DIVIDE & CONQUER
    // DIVIDE: N-> N/2
    // find the middle element: division point -> break problem into 2 subproblems of half-size:
    int mid=(hi+lo)/2;
    // CONQUER: Solve subproblems of half size
    // make recursive calls on middle's left and right respectively:
    sort(items, aux, lo, mid);
    // left-half is sorted
    sort(items, aux, mid+1, hi);
    // right-half is sorted

    // OPTIMIZATION: Check if left half and right half are already inplace:
    // check if the right most item of the left half (the biggest element) is smaller than the left most element of the right half (its smallest element)
    if(less(items[mid+1],items[mid])) {
      // COMBINE:
      // pass sorted subarrays (left and right) into the merge subroutine:
      merge(items, aux, lo, mid, hi);
      // the subarray from lo to hi is sorted
    }
    else return;
  
  } // T(N)=2T(N/2)+O(N)-> MERGE => a=2 (branching factor=number of subproblems), b^d=2 => a=b^d => MM case 2: T(N)=O(NlgN)

  /* Merge Subroutine: N compares, 6N arrays accesses (to copy over items from aux array to the original array)
     Given two sorted subarrays: [lo mid] and [mid+1 hi], produce a sorted subarray [lo hi] by merging given two sorted subarrays: 
     a. Linear number of compares (scann lo to hi just once)
     b. Requires Linear extra space (to copy over the original array)
     c. implementation MUST guarantee STABILITY (it must NOT change the relative order of equal keys)
  */

  private static void merge (Comparable[] items, Comparable[] aux, int lo, int mid, int hi) {
    // first copy over from original array to the aux from lo index to hi index:
    System.arraycopy(items, lo, aux, lo, hi-lo+1);

    // we need two index pointer on each sorted subarray: left and right
    int i=lo; // points to the beginning of the sorted left subarray
    int j=mid+1; // points to the start of the sorted right subarray  

    // put items within [lo hi] (inclusive) back into the items array IN ORDER, maintain STABILITY by always copy over from sorted left half unless the current item on 
    // the right half is strictly less than the current item on the left half or left half is exhuasted:
    for(int k=lo; k<=hi; k++) {
      if(i>mid)  items[k]=aux[j++]; // if left is exhuasted, read from right, and advance right index pointer
      else if (j>hi) items[k]=aux[i++]; // if the right half is exhuasted, read from left, and advance its pointer
      else if(less(aux[j], aux[i])) items[k]=aux[j++]; // if the current item on the right is STRICTLY less than the current item on the left, read from right and advance its pointer
      else // otherwise: always copy over over from left to the sorted array
        items[k]=aux[i++];
    }
    // items from lo-hi is sorted, STABLE
  }

  // generic comparsion for Comparable types:
  private static boolean less(Comparable v, Comparable w) {
    // return true v is less than w:
    return v.compareTo(w)<0;
  }

  // exchange method: inpalce exchange
  private static void exch(Comparable [] items, int i, int j) {
    Comparable temp=items[i];
    items[i]=items[j];
    items[j]=temp;
  }
}
