import java.io.File;
import java.util.Comparator;

public class Selection {

  // for the Selection sort is an static method
  // invokes by the Insertion class, and have access only
  // to the static memebers of the Selection class:
  public static void sort (Comparable [] items) {
    // The sort method takes an array of Comparable objects,
    // then based on the Data Type of the objects, it calls back
    // the compareTo method that has been implemented for that 
    // Data Type in its class definition

    // Selection Sort:
    // Start from the index i (initially 0), 
    // find the min from index i+1 to the end of array
    // swap the min element with the element at index ith:
    int min; // the index of the min element
    for (int i=0; i<items.length; i++) {
      // find the min from i to n-1
      // using the linear search approach
      // O(n) number of comparisons
      min = i;
      for (int j=i+1; j<items.length; j++) {
 	// if items[j]<item[min] then update the min:
        if (less(items[j], items[min]))  min = j;
      }
      // min now contains the index of the minimum item from
      // i to n-1.
      // swap the min with the item at the ith index and 
      // advance i (automatically done by the for loop)
      exch (items, i, min);
      // Worst case, only O(n) number of exchanges will happen

      // now make sure (items array is sorted from 0 to ith elements
      assert isSorted(items, 0, i);
    }

    // make sure if the array is sorted
    assert isSorted(items);

   // NOTE:
   // Sorted Arrays: No matter how sorted the input array is
   // Selection sort takes (1/2)N^2 number of compares (N times finding minimum at linear time)
   // and take N number of exchanges.

   // The best case (Sorted array): Does NOT improve the 
   // Selection sort algoritm performance neither in terms of
   // number of compares nor the number of exchanges
   
  } 

  // Implementing sort, using Comparator object:
  public static void sort (Object [] items, Comparator comparator) { 
    // Selection Sort:
    // Start from the index i (initially 0), 
    // find the min from index i+1 to the end of array
    // swap the min element with the element at index ith:

    int min; // the index of the min element
    for (int i=0; i<items.length; i++) {
      // find the min from i to n-1
      // using the linear search approach
      // O(n) number of comparisons
      min = i;
      for (int j=i+1; j<items.length; j++) {
 	// if items[j]<item[min] then update the min:
        if (less(items[j], items[min], comparator))  min = j;
      }
      // min now contains the index of the minimum item from
      // i to n-1.
      // swap the min with the item at the ith index and 
      // advance i (automatically done by the for loop)
      exch (items, i, min);
      // Worst case, only O(n) number of exchanges will happen

      // now make sure (items array is sorted from 0 to ith elements
      assert isSorted(items, 0, i, comparator);
    }

    // make sure if the array is sorted
    assert isSorted(items, comparator);    
  }


  // Comparable less:
  public static boolean less (Comparable v, Comparable w) {
    // invokes the compareTo method on a Comparable instance:
    return v.compareTo(w) < 0; // compareTo returns NEGATIVE integer meaning LESS than
  }

  // Comparator less:
  public static boolean less (Object v, Object w, Comparator comparator) { 
    // for the input Object, their corresponding class, has a field that specified the comparator, 
    // for the instances of that class and explictly implemented the Comparator interface inside the closing class as a nested class
    // so that Comparator can be instatiated from its explict class that implemented it, 
    // also compare method is implemented for instances of that class for a given Comparator
    return comparator.compare(v,w) < 0; 
  }

  // swap the position of two items within the array of Comparable objects
  public static void exch (Comparable [] items, int i, int j) {
    Comparable temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  // swap the position of two items within the array of Comparable objects
  public static void exch (Object [] items, int i, int j) {
    Object temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  // a predicate that checks if the array is sorted or not
  public static boolean isSorted(Comparable [] items, int from, int to) {
    // Start from the first item after "from" index, because, "from" index is the
    // first element and it has nothing on its left, a[from-1] will cause IndexOutOfBoundsException
    for (int i=from+1; i<=to; i++)
      // if an item on the right is smaller than item on its left, then array is not sorted
      if (less(items[i], items[i-1])) return false;
    return true;
  }

  // overload the isSet method to check if the entire array is sorted:
  public static boolean isSorted (Comparable [] items) {
    // it calls the isSorted but set up its parameter to fulfill the need of this
    // version:
    return isSorted(items, 0, items.length-1);
  }

  public static boolean isSorted(Object [] items, int from, int to, Comparator comparator) {
    for (int i=from+1; i<=to; i++)
      if( less(items[i], items[i-1], comparator) ) return false;
    return true;
  }

  public static boolean isSorted (Object [] items, Comparator comparator) {
    return isSorted(items, 0, items.length-1, comparator);
  } 

  // Let's write a sort client for the Selection sort:
  public static void main (String [] args) {
/*    File directory = new File (args[0]);
    File [] files = directory.listFiles();
    // sort the array of files
    Selection.sort(files);
    for(int i=0; i<files.length;i++) System.out.println(files[i].getName());
*/
    String [] str = {"Shervin", "sas", "Nice", "dUde", "Dude"};
    // sort the array of files
    Selection.sort(str, String.CASE_INSENSITIVE_ORDER);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);
  }
}
