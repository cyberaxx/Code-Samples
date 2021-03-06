import java.io.File;
import java.util.Comparator;
import java.util.Arrays;

public class Insertion {

  public static void sort (Comparable[] items) {
    for(int i=0; i<items.length; i++) {
      for(int j=i; j>0; j++) {
	// compare the element at a[j-1] with a[j], if a[j]
	// was less than a[j-1] (the element on its left) swap:
        if ( less(items[j],items[j-1]) ) exch(items, j, j-1);
	else break; // this is for optimization of number of compares
      }
      // every elements to the left of the ith element (inclusive) must be sorted
      assert isSorted(items, 0, i);
    }
    // the entire array is sorted
    assert isSorted(items);
    // NOTE: If array is sorted Insertion Sort only uses N-1 compares (element 1 to N);
    // and 0 exchange. It's going to be blazingly fast for sorted array. 

    // Also for partially sorted array (number of INVERSIONs in the array being <=c*N)
    // The number of comparison used by the Insertion sort would be LINEAR and the number
    // exchanges would be equal to the number of inversions

    // NOTE: If array is sorted in the descending order, sorting using Insertion sort
    // is VERY SLOW, even SLOWER than Selection sort. Because unlie linear number of exchanges
    // in the selection sort, insertion sort would have quadratic number of exchanges (1/2)N^2
    // while both selection sort and insertion sort perform (1/2)N^2 number of compares
  }

  public static void sort(Object [] items, Comparator comparator) {
    for(int i=0; i<items.length; i++) {
      for(int j=i; j>0; j--) {
        if( less(items[j], items[j-1], comparator) ) exch(items, j, j-1);
	else break; // to optimize the nubmber of comparisons
      }
      // Invariance:
      // All items from 0 to ith (inclusive) are sorted in Ascending order:
      assert isSorted(items, 0, i, comparator);
    }
    // Check if the array is sorted
    assert isSorted(items, comparator);
  }

  
  private static boolean less (Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }
  
  private static boolean less (Object v, Object w, Comparator comparator) {
    return comparator.compare(v, w) < 0;
  }

  private static void exch (Object [] items, int i, int j) {
    Object temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  private static void exch (Comparable [] items, int i, int j) {
    Comparable temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  private static void exch (int [] items, int i, int j) {
    int temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  private static boolean isSorted(Object [] items, int from, int to, Comparator comparator) {
    // we have to start from the second item in the array, because we
    // to compare it with the item on its left, and there is no item on
    // the left side of the first item:
    for (int i=from+1; i<=to; i++)
      if( less(items[i], items[i-1], comparator) ) return false;
    return true;
  }

  private static boolean isSorted(Object [] items, Comparator comparator) {
    return isSorted(items, 0, items.length-1, comparator);
  }

  private static boolean isSorted (Comparable [] items, int from, int to) {
    for(int i=from+1; i<=to; i++)
      if ( less(items[i], items[i-1]) ) return false;
    return true;
  } 

  private static boolean isSorted (Comparable [] items) {
    return isSorted(items, 0, items.length-1);
  }

  // return a permutation that gives the elements in a[] in ascending order
  // do not change the original array a[]
  public static int[] indexSort(Comparable[] items) {
    int [] indeces = new int [items.length]; // array of indeces
    for(int i=0;i<indeces.length;i++) indeces[i] = i; // initialization

    for(int i=0; i<items.length; i++) {
      for(int j=i; j>0; j--) {
 	// check if the array at the given index is less than its previous items
        // NOTE that j and indeces[j] are technically same thing
        if ( less(items[indeces[j]], items[indeces[j-1]]) ) exch(indeces, j, j-1);
	else break;
      }
    }
    return indeces;
  }

  // Sort client
  public static void main (String [] args) {
/*
    File directory = new File (args[0]);
    File [] files = directory.listFiles();
    // sort the array of files
    // Using Comparable interface and compareTo method
    // to compute the NATURAL ORDERing of instances of a given class
    Selection.sort(files);
    for(int i=0; i<files.length;i++) System.out.println(files[i].getName());
*/
    String [] str = {"Shervin", "sas", "Nice", "dUde", "Dude"};
    // sort the array of strings
    // Using the Comparator interface, and compare method
    // using an alternative way of ordering instances of the class that 
    // has implemented Comparator interface, and so has an "instance" of
    // a Comparator implemented by an explicit nested class within the enclosing class
/*
    Selection.sort(str, String.CASE_INSENSITIVE_ORDER);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);
    System.out.println();
    Selection.sort(str);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);
*/
    for(int i=0; i<str.length;i++) System.out.println(str[i]);
    System.out.println();
    for(int i=0; i<str.length;i++) System.out.println(Insertion.indexSort(str)[i]);
    System.out.println();
    Selection.sort(str);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);
  }
}
