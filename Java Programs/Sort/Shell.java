import java.io.File;

public class Shell {

  // NOTE: One of the major downside of the Insertion sort was exchanging out of
  // order elements in the array one position at a time, and that would cause to 
  // quadratic number of exchanges when the input array of items is sorted in descending order
  // To improve the perfomance of the Insertion Sort, Shell Sort, first slip the array to a few h-arrays
  // (array of element h-elements apart from each other) sort h-arrays, decrease the value of h and repeat.
  // The advantages are:
  // 1, Initial Window size (h) is big so the number of element to be sorted is few, Insertion sort is fast
  // 2, When the window sizes reaches to small value like 2, 1, the array is already partially sorted, so the Insertion sort
  // would have liear numebr of compares and #inversions, number of exchanges.

  // The worstcase number of compares in Shell Sort is O(n^(3/2)) using Knuth seq for h: 3x+1
  // [While loop and two nested for loops within the while loop over h]

  public static void sort(Comparable [] items) {
    // interval windo size:
    int h=1;

    // find a proper h value using Knuth formula: 3k+1
    while (h < (items.length)/3) h = h * 3 + 1;

    // Shrinking the window size:
    while (h>=1) {
      // Use insertion sort on h-arrays
      for (int i=h; i<items.length; i++) {
        for (int j=i; j>=h; j-=h) {
          if ( less(items[j], items[j-h]) ) exch (items, j, j-h);
	  else break;
        }
      }
      // all elements from ith to end are h-sorted
      assert isHSorted(items, h);

      // shrink the size of h:
      h = h/3;
    }
    // the array must be sorted
    assert isSorted(items);
  }

  private static boolean less (Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  private static void exch (Comparable [] items, int i, int j) {
    Comparable temp = items[i];
    items[i] = items[j];
    items[j] = items[i];
  }

  private static boolean isHSorted(Comparable [] items, int h) {
    for (int i=h; i<items.length; i++)
      if ( less(items[i], items[i-h]) ) return false;
    return true;
  }

  private static boolean isSorted(Comparable [] items) {
    for (int i=1; i<items.length; i++)
      if ( less(items[i], items[i-1]) ) return false;
    return true;
  }

  public static void main (String [] args) {
    File directory = new File (args[0]);
    File [] files = directory.listFiles();
    // sort the array of files
    // Using Comparable interface and compareTo method
    // to compute the NATURAL ORDERing of instances of a given class
    Selection.sort(files);
    for(int i=0; i<files.length;i++) System.out.println(files[i].getName());
  }

}
