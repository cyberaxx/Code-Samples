import java.io.File;
import java.util.Arrays;

public class Inversion {

  // number of inversion, meaning number of elements in the input array
  // that are not in their rightful position,
  // in other words, number of exchanges that has to be made to sort the array sorted
  public static int count(Comparable [] items) {
    // Since in our computation for counting number of inversions in the
    // arrary we are piggybacking on merge routine from the mergesort, we are 
    // basically sorting the input array while counting the inversion in NlogN

    // Therefore we need to copy the content of the input array
    // and after we got done with our computation put it back there
    // as it was before (meaning unsorted:
    
    // Some extra book-keeping: 
    // we do NOT want to change the order of the given array
    // so we should take a shallow copy of it to work with 
    // without manipulating the original array:
    Comparable [] temp = items.clone();

    // we need an aux array for the merge:
    Comparable [] aux = new Comparable [items.length];

    // count the number of inversion and sort the temp array:
    int counter = count(items, temp, aux, 0, items.length-1);

    // return the number of inversion
    return counter;
  }

  // This divide and conquer method follows the follwoing recurrence relation:
  // T(n) = 2T(n/2) + O(n) 
  // 2*T(n/2) for two subproblems of half of the size of the original problem
  // O(n) for the count combiner
  // similar to the merge subroutine, inversion counter takes O(NlogN) on arrays with N items.
  private static int count (Comparable [] original, Comparable [] items, Comparable [] aux, int lo, int hi) {
    // Divide and conqure
    // 1. base case: where to stop division:
    if (hi<=lo) return 0; // subarray of size one has 0 inversion (already sorted)

    // 2. Break the prolem into subproblems of smaller size:
    int mid = (hi+lo)/2; // find the middle of array
    // 2a. Find the number of inversions in the left half [lo, mid]
    int leftCount = count(original, items, aux, lo, mid);
    // 2b. Find the number of inversions in the right half [mid+1, hi]
    int rightCount = count(original, items, aux, mid+1, hi);
    // 2c. Find the number of split inversions [lo-hi]
    int splitCount = count(items, aux, lo, mid, hi);

    // 3. Combine: add them up   
    return  leftCount + rightCount + splitCount;
  }

  // method merge two sorted subarrays into one array and count number of 
  // split inversions:
  private static int count(Comparable [] items, Comparable [] aux, int lo, int mid, int hi) {
    // indetical to merge routine in merge sort:
    // lo and mid characterize the left-half and mid+1 and hi characterize the right half

    // copy form partially sorted array lo-hi
    // (sorted from lo-mid and mid+1_hi) to the aux array:
    System.arraycopy(items, lo, aux, lo, hi-lo+1);

    int i=lo; // pointer of the left half on aux array
    int j=mid+1; // pointer on the right half aux array
    int splitCount = 0;

    // scan from left to right : lo to hi
    for(int k=lo; k<=hi; k++) {
      // if the SORTED left half is exhuasted (no more possible split inversion)
      if (i>mid) items[k] = aux[j++]; // copy over from SORTED right half and advance its pointer
 
      // Similarly if the SORTED right half is exhuasted (no more possible split inversion) 
      else if (j>hi) items[k] = aux[i++]; // copy over from the left SORTED half and advance its pointer

      //  THIS IS THE INTERSTING CASE:
      // if the current item at the SORTED right half is LESS than the current item at the SORTED left half:
      // 1. we have increase the number of split inversions by the number of elements REMAINS in the left half
      // 2. copy over from the SORTED right half and advance its pointer
      else if(less(aux[j], aux[i])) {
	items[k] = aux[j++];
	// number of items remain in the SORTED LEFT HALF (left half current index range [i-mid] => # = mid - i + 1
        splitCount += mid - i + 1;
      }
      // if the curren item on the SORTED left half is less than or equal to the item on the SORTED right half:
      // copy over from the SORTED left half and advance its pointer:
      else items[k] = aux[i++];
    }
    return splitCount;
  }  

  // lets right a test for our inversion counter method:
  // test actually uses the Insertion sort subroutine and counts 
  // the number of exchanges that it requires to do in order to sort the array:
  private static int exchCount(Comparable [] items) {
    int count = 0;
    // scan the input array from left to right
    for(int i=0; i<items.length; i++) {
      // elements on the left side of the current element under the consideration must
      // be in a sorted order (Insertion sort Invariance)
      for(int j=i; j>0; j--) {
        // check if the invariant does NOT hold, exchange the current item with
        // the item that break the invariant:
        if(less(items[j], items[j-1])) {
	  exch(items, j, j-1);
	  count++;
        }
	else break; // not to loop any further for efficiency
      }
    }
    return count;
  }

  private static void exch (Comparable [] items, int i, int j){
    Comparable temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }
  
  // comparison based on implementation of compareTo method:
  private static boolean less (Comparable v, Comparable w) {
    return v.compareTo(w) < 0; // TRUE if v is LESS than w according to compareTo total order relation
  }

  public static void main (String [] args) {
    File directory = new File (args[0]);
    File [] files = directory.listFiles();

    System.out.println("Number of inversions:");
    System.out.println(Inversion.count(files));
    for(int i=0; i<files.length;i++) System.out.println(files[i].getName());
    System.out.println();
    System.out.println(Inversion.exchCount(files));
    for(int i=0; i<files.length;i++) System.out.println(files[i].getName());

    System.out.println();
    System.out.println();

    Integer [] ints = {1,3,5,2,4,6};
    System.out.println("Number of inversions:");
    System.out.println(Inversion.count(ints));
    for(int i=0; i<ints.length;i++) System.out.print(ints[i] + " ");
    System.out.println();
    System.out.println(Inversion.exchCount(ints));
    for(int i=0; i<ints.length;i++) System.out.print(ints[i] + " ");
    System.out.println();
  }
}
