import java.util.Arrays;

public class Search {
  
  /* Ordered operation in a sorted array
  1. Search for a Comparable key in a SORTED array of Comparable keys (implement contains method) O(longN)
  2. Return the index of a given Comparable key if found, not return -1 (not necessarily the first occurence of the given key) O(longN)
  3. Select operation: return the item at the given index O(1)
  4. Rank operation: return how many keys are less than a given key in the array (if found index+1) if not found the index of key to be inserted at 
  5. Predecessor(floor) and Successor(ceiling) operations: return the next before and after the given item O(1)
  */

  public static boolean contains(Comparable[] items, Comparable key) {
    // extreme test cases:
    if (key==null || items.length==0) return false;
    return recursiveContains(items, 0, items.length-1, key);
  }
  
  // Divide and Conquer search subroutine: 1. Divde 2. Conquer 3. Combine
  // T(N) = T(N/2) + O(1) => MM case 2: T(n)=O(lgN)
  private static boolean recursiveContains(Comparable[] items, int lo, int hi, Comparable key) {
    // 1. Base case: where to stop division: trivial problem size: 1 or less
    if(hi<lo)  return false; // subproblems of size 0
    if(hi==lo) return key.compareTo(items[lo])==0; // subproblem of size 1

    // 2. recurrence:
    // 2a. Divide the problem into two subproblems:
    int mid=(lo+hi)/2;

    // 2b. Conquer: solve subproblem in order to solve the problem of the bigger size:
    // case 1: if item at the mid "is equal to" (using a generic comparison implemented by compareTo method) the key:
    if(items[mid].compareTo(key)==0) return true;
    else if(key.compareTo(items[mid])<0) return recursiveContains(items, lo, mid-1, key);
    else // key.compareTo(items[mid])>0
      return recursiveContains(items, mid+1, hi, key);
  }

  public static int indexOf(){return -1;}
  public static int select(){return -1;}
  public static int bitonicMax(){return -1;}
  public static void bitonicSort(){}
  public static void bitonicSearch(){}


  // BS client code:
  public static void main(String [] args) {
    Integer [] items = new Integer[]{1,34, 5, 23, 26, 12, 5, 6, 5, 5, 8, 4, 3, 2, 34, 21, 16, 89};
    // Sort the array:
    Arrays.sort(items); // sort array of Comparable types in-place, using Dual-Pivot Quick Sort E(RT)=NlogN
    System.out.println(contains(items, 13));
    System.out.println(contains(items, 4));
    System.out.println(contains(items, 26));

    System.out.println(Arrays.binarySearch(items, 13)>=0);
    System.out.println(Arrays.binarySearch(items, 4)>=0);
    System.out.println(Arrays.binarySearch(items, 26)>=0);
    
  }
}
