import java.io.File;
import java.util.Comparator; // define alternative ordering of instances of a class
import java.util.Arrays;

public class MergeBU {

  
  // iterative bottom up merge sort with comparator:
  public static void sort (Object [] items, Comparator comparator) {
    int mid, hi; // to pass to the merge method
    // aux array:
    Object [] aux = new Object [items.length]; // extra linear space required by merge sort (for merge operation)

    // starting from array of size 1 (meaning one object)
    // merge sorted (disjoint) subarrays of size one, to get sorted (disjoint) subarray of size 2,
    // iterate the same process to get sorted (disjoint) subarrays of size 4, 8, 16, 32, ..., N-1

    // call the size of sorted subarray at each iteration sortSize
    // sortSize is in [1, N-1] range
    // double the sortSize after each pass over the entire array (each complete iteration of the inner loop)
    for(int sortSize=1; sortSize<items.length; sortSize=2*sortSize) {// this for loop at MOST excuted logN time (because of its step size) 
       // iteratively sort 2*sorSize portions of the original array
       // which their starting index "lo" is 2*sortSize apart from each other:
       // inside the inner loop we merge two sorted subarrays of size sortSize into
       // one sorted subarray of size 2*sortSize:
       for(int lo=0; lo<items.length-sortSize; lo = lo+2*sortSize) {
         // the index "hi" can NOT be bigger than the last index of the original array (N-1) 
         // hi index is 2*sortSize, because each subarray of size sortSize are already sorted
         // here we would like to merge them together to get a sorted subarray of size 2*sortSize
         // from "lo" index to "lo to lo+sortSize" to "sortSize+1 to lo+2*sortSize-1" 
         hi = Math.min(items.length-1, lo+2*sortSize-1);
         mid = lo+sortSize-1; // important not to use (lo+hi)/2 because of uneven subarray sizes
	 merge(items, aux, lo, mid, hi, comparator);
       }
    }
    assert isSorted(items, 0, items.length-1, comparator);    
  }

  private static boolean less(Object v, Object w, Comparator comparator) {
    // invokes the compare method on the Comparator object (comparator):
    return comparator.compare(v,w) < 0; // it returns TRUE if v IS LESS THAN w
  }

  private static void merge (Object [] items, Object [] aux, int lo, int mid, int hi, Comparator comparator) {
    // the original array is sorted from lo-mid and from mid+1-hi
    assert isSorted(items, lo, mid, comparator);
    assert isSorted(items, mid+1, hi, comparator);

    // copy from partially sorted original array (items) to the aux array from lo-hi
    System.arraycopy(items, lo, aux, lo, hi-lo+1); // +1 because hi is included:
    
    // pointer on the first half:
    int i=lo; // i is in [lo,mid] range

    // pointer on the second half: 
    int j=mid+1; // j is in [mid+1, hi] range

    // sort items in the original array from lo-hi
    // using two sorted half-array form lo-mid, and mid+1-hi
    // and less method (that uses comparator to compare objects)
    for(int k=lo; k<=hi; k++) {
      // the correct implementation of this loop 
      // is crucial for merger sort to be STABLE (and preserve stability):
      // alway copy from LEFT-half, unless the LEFT half is exhuasted or the 
      // element on the right half is LESS than the element on the left half
      // Therefore, if keys are EQUAL always copy from the left half (preserve the relative order of EQUAL keys)

      // if left half is already exhuasted:
      // copy over from right half and advance its pointer:
      if(i>mid) items[k] = aux[j++];

      // right half is exhuasted:
      // copy over from the left half and advance its pointer:
      else if(j>hi) items[k] = aux[i++];

      // if the current item on the right half is LESS (strictly less) than
      // the current item on the left half:
      // then copy over from the right half and advance its pointer 
      else if (less(aux[j], aux[i], comparator)) items[k] = aux[j++];

      // Otherwise, copy over from the left half and advance its pointer:
      else items[k] = aux[i++];
    }
    
    // after merge operation the array must be sorted from lo-hi
    assert isSorted(items, lo, hi, comparator);

  }  

  // assuming array is sorted when it is sorted in ASCENDING order
  private static boolean isSorted(Object [] items, int lo, int hi, Comparator comparator) {
    for(int i=lo+1; i<=hi; i++)
      // compare, using the comparator object through the less method:
      if(less(items[i], items[i-1], comparator)) return false;
    return true;
  } 


  // merge sort would be stable as long as merge subroutine is stable:
  private static void merge (Comparable [] items, Comparable [] aux, int lo, int mid, int hi) {
    int i=lo; int j=mid+1;
    // copy over elements lo-hi form items array to aux array:
    System.arraycopy(items, lo, aux, lo, hi-lo+1); // copy to aux array:

    // cautious about maintaing the stability of the items array while merging:
    for(int k=lo; k<=hi; k++) {
      // if pointer on the SORTED left half is exhusted:
      // copy from the SORTED right half (from aux array)
      // and advance the j pointer:
      if(i>mid) items[k] = aux[j++];
     
      // if the pointer on the SORTED right half is exhuasted:
      // copy over from the SORTED left half (form aux array)
      // and advance the i pointer:
      else if(j>hi) items[k] = aux[i++];
      
      // if neither of pointer to left nor right sorted halves are exhuasted, then
      // compare the element they are point to using less method:
      // NOTE: maintain stability by NOT moving object with EQUAL key from right to left:
      else if(less(aux[j], aux[i])) items[k] = aux[j++];
   
      // Otherwise: aux[i] is less than or equal to aux[j], so, copy over form 
      // left half and advance i.
      // this way stability would be preserved by merge
      else items[k] = aux[i++];
    }
  }

  // bottom up merge sort: Iterative approach: 
  public static void sort (Comparable [] items) {
    int mid, hi;
    Comparable [] aux = new Comparable[items.length];
    // start sorting subarray of size one, then use the sorted subarray of size 1, to sort subarrays of size 2, 
    // and iterate the process for 4, 8, 16, ...., N-1
    // use the same simple merge operation with aux array that copys over item from original array:

    // after each iteration of the outer loop we need to double the sortWindow 
    // so the outer for loop would excute only logN times:
    for(int sortWindow=1; sortWindow<items.length; sortWindow *= 2) { 
      // we have to loop over from lo-hi (inclusive)
      for(int lo=0; lo<items.length-sortWindow; lo += 2*sortWindow) {
	hi = Math.min(items.length-1, lo+2*sortWindow-1);
	mid = lo + sortWindow - 1;
        merge(items, aux, lo, mid, hi);
      }
    }
  }

  // implement comparison subroutine using Comparable interface, by invocing compareTo method on 
  // the given Comparable object
  public static boolean less (Comparable v, Comparable w) {
    return v.compareTo(w) < 0; // if v is less than w in some total order relation implemented by Comparable, then less return true, ow false
  }

  // Let's write a sort client for the Selection sort:
  public static void main (String [] args) {
    // Sort with Comparable example:
    File directory = new File (args[0]);
    File [] files = directory.listFiles();
    // sort the array of files
    MergeBU.sort(files);
    for(int i=0; i<files.length;i++) System.out.println(files[i].getName());

    System.out.println();

    // Sort with Comparator example:
    String [] str = {"Shervin", "sas", "Nice", "dUde", "Dude"};
    MergeBU.sort(str);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);

    System.out.println();

    // Sort with Comparator example:
    MergeBU.sort(str, String.CASE_INSENSITIVE_ORDER);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);

    System.out.println();
    Arrays.sort(str, String.CASE_INSENSITIVE_ORDER);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);

    System.out.println();
    Arrays.sort(str);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);

  }
}
