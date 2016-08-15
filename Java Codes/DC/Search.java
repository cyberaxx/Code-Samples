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
//    return recursiveContains(items, 0, items.length-1, key);
    return contains(items, 0, items.length-1, key);
  }
  
  // Assumption: Input array is SORTED (ordered)
  private static boolean contains(Comparable [] items, int lo, int hi, Comparable key) {
    while (hi>=lo) {
      // middle of subarray (for an ordered array this is the MEDIAN)
      int mid=(hi+lo)/2;

      if(key.compareTo(items[mid]) < 0) hi=mid-1;
      else if(key.compareTo(items[mid])>0) lo=mid+1;
      else // if key.compareTo(items[mid])==0 equal:
        return true;
    }
    return false;
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

  public static int indexOf(Comparable [] items, Comparable key){
    // extreme test case:
    if(!contains(items, key)) return -1; // items array does not contain the key
//    return recursiveIndexOf(items, 0, items.length-1, key);   
    return indexOf(items, 0, items.length-1, key);
    
  }

  // Assumption input is in ASCENDING order:
  private static int indexOf(Comparable[] items, int lo, int hi, Comparable key) {
    if(!contains(items, key)) return -1; // if items array does not contain the key return -1
    while(hi>=lo) {
      int mid=(hi+lo)/2;
      if(key.compareTo(items[mid])<0) hi=mid-1;
      else if(key.compareTo(items[mid])>0) lo=mid+1;
      else // key.compareTo(items[mid])==0 equal:
        return mid;
    }
    return -1;
  }
  // recursive solution: 1. Base case 2. Recurrence
  private static int recursiveIndexOf(Comparable[] items, int lo, int hi, Comparable key) {
    // 1. Base case: stop dividing -> for subproblem (subarray) of size 1 or less
    if(hi<lo) return -1; // 0-size subarray
    // 1-size subarray:
    if(hi==lo) {
      if(key.compareTo(items[lo])==0) return lo;
      else return -1;
    }

    // find the middle element: helps to divide the prolem into two subproblem of half size N->N/2
    int mid=(hi+lo)/2;
    
    // 2. recursive calls
    if(key.compareTo(items[mid])<0) return recursiveIndexOf(items, lo, mid-1, key); // T(N/2)
    else if(key.compareTo(items[mid])>0) return recursiveIndexOf(items, mid+1, hi, key); // T(N/2)
    else // if key is equal to the items[mid]:
      return mid;
  }

  // O(1) is ordered array
  public static Comparable select(Comparable[] items, int k) {
    int index=k-1;
    if(index<0 || index>=items.length) return null;
    return items[index];
  }

  // Given a Comparable key, what is the rank of the key (#of keys less than the given key) in an ORDERED array: both search hit and search miss can
  // represent the rank
  public static int rank(Comparable[] items, Comparable key) {
    // extreme cases:
    if(key==null || items.length==0) return -1;
//    return recursiveRank(items, 0, items.length-1, key);
    return rank(items, 0, items.length-1, key); 
  }
  
  // recusive method: 1. Base cas, 2. Recurrence (Divide, Conquer, and Combine)
  private static int recursiveRank(Comparable[] items, int lo, int hi, Comparable key) {
    // Base case: where to stop division (subproblems (subarrays) of size 1 or less)
    // search hit/miss (unsuccessful search) : return the position that the given "key" belongs to
    if(hi<=lo) return lo;

    // fid the middle element in the ASCENDING ordered array:
    int mid=(hi+lo);
    if(key.compareTo(items[mid])<0) return recursiveRank(items, lo, mid-1, key);
    else if(key.compareTo(items[mid])>0) return recursiveRank(items, mid+1, hi, key);
    else // search hit: key is to equal the  middle element:
      return mid;
  }

  private static int rank(Comparable[] items, int lo, int hi, Comparable key) {
    // search for the key within ORDERED subarray [lo hi]: using binarySearch
    while(hi>=lo) {
      int mid=(hi+lo)/2;
      if(key.compareTo(items[mid])<0) hi=mid-1;
      else if (key.compareTo(items[mid])>0) lo=mid+1;
      else // equal to the middle elemet: SEARCH HIT:
        return mid;
    }
    // Search miss (subproblem of size one hi=lo=>mid=hi or lo): hi<lo (case 1: lo get updated to hi+1 (lo+1) , case 2: hi get update to lo-1):
    return lo;
  }

  // find a max in a bitonic oredered array DISTINCT items
  public static Comparable bitonicMax(Comparable[] items) {
    if(items==null) return null;
    if(items.length==0) return null;
//    return items[recursiveBitonicMax(items, 0, items.length-1)];
//    return items[bitonicMax(items, 0, items.length-1)];
     return items[(recBitonicMax(items, 0, items.length-1))];
  }

  private static int bitonicMax(Comparable [] items, int lo, int hi) {
    while(hi>lo) {
      int mid=(hi+lo)/2;
      int cmp=items[mid].compareTo(items[mid+1]);
      if(cmp>0) hi=mid;
      else if(cmp<0) lo=mid+1;
      else return mid;
    }
    return lo;
  }

  // ASSUMPTION: items is a bitonic array
  private static int recBitonicMax(Comparable[] items, int lo, int hi) {
    // BASE CASE: subarrys of size 1 and less
    if(hi<=lo)  return lo;
    // RECURRENCE:
    // find the middle element in the subarray of size 2 or more:
    int mid=(hi+lo)/2;
    // compare the middle element with the element on its right in the array
    int cmp=items[mid].compareTo(items[mid+1]);

    // Divide and Conquer:
    // Case1: if the middle item is less than the item on its right: search for max on its right (exclusive):
    if(cmp<0)  return recBitonicMax(items, mid+1, hi);
    // Case2: if the middle item is greater than the item on its right: search for max on its left (inclusive):
    else if(cmp>0)  return recBitonicMax(items, lo, mid);
    // case3: if equal:
    else return mid;
  }

  // recursive solution: 1. Base case 2. Recurrence (Divide and Conquer)
  private static int recursiveBitonicMax(Comparable[] items, int lo, int hi) {
    // BASE CASE:
    if(hi<=lo) return lo; // subarrays of size 1 or less, the lo is the index of the max element
    if(hi-lo==1) { // subproblems of size 2
      if(items[lo].compareTo(items[hi])>0) return lo;
      else return hi;
    }

    // RECURRENCE: For subproblems of size 3 
    // find the middle of the subarray:
    int mid=(hi+lo)/2;

    // compare the middle element with its right and left neighbours:
    int cmpRight = items[mid].compareTo(items[mid+1]);
    int cmpLeft = items[mid].compareTo(items[mid-1]);

    // RECURSIVE CALLS:
    // 1. if the mid is greater than or equal to the left and less than or equal to the right we are in the increasing half we must recurse on right half
    if(cmpLeft>0 && cmpRight<0)  return recursiveBitonicMax(items, mid+1, hi);
    // 2. if the mid is less than or equal to the left and greater than or equal to the right we are in the decreasing half we must recurse on left half
    else if(cmpLeft<0 && cmpRight>0) return recursiveBitonicMax(items, lo, mid-1);
    else // if cmpLeft>0 and cmpRight>0 : MAX
      return mid;
  }	

  public static Comparable bitonicMin(Comparable[] items) {
    if(items==null) return null;
    if(items.length==0) return null;
    return items[bitonicMinIndex(items, 0, items.length-1)];
  }

  private static int bitonicMinIndex(Comparable[] items, int lo, int hi) {
    if(hi<lo) return -1;
    if(hi==lo) return lo;

    if(items[lo].compareTo(items[hi])<0) return lo;
    else return hi;
  }

  public static int bitonicSearch(Comparable[] items, Comparable key) {
    if(items==null) return -1;
    if(key==null || items.length==0) return -1;
//    return recursiveBitonicSearch(items, 0, items.length-1, key);
  }

  // recursive method: 1. Base case 2. Recurrence
  private static int recursiveBitonicSearch(Comparable [] items, int lo, int hi, Comparable key) {
    // BASE CASE:
    if(hi<lo) return -1; // 0-size subarray
    if(hi==lo) { // 1-size subarray
      if(key.compareTo(items[lo])==0) return lo;
      else return -1;
    }
    if(hi-lo==1) { // 2-size subarray
      if(key.compareTo(items[lo])==0)  return lo;
      else if(key.compareTo(items[hi])==0) return hi;
      else return -1; // failed search
    }

    // Divide and Conquer: For subarrays of size >1 (>=2)
    int mid=(hi+lo)/2; // find middle of the subarray
    // compare it to its left and right element to identify which part of the arry the mid element belongs to:
    int cmpLeft=items[mid].compareTo(items[mid-1]);
    int cmpRight=items[mid].compareTo(items[mid+1]);
    int cmpKey = key.compareTo(items[mid]);

    // 1. if mid is at the increasing portion of the array:
    if(cmpLeft>0 && cmpRight<0) {
      // if key is less than the middle
      if(cmpKey<0) return recursiveBitonicSearch(items, lo, mid-1, key);
      else if(cmpKey>0) return recursiveBitonicSearch(items, mid+1, hi, key);
      else return mid;
    }
    // mid is at the decreasing side:
    else if (cmpLeft<0 && cmpRight>0) {
      // key is less than th middle:
      if(cmpKey<0)  return recursiveBitonicSearch(items, mid+1, hi, key);
      else if(cmpKey>0)  return recursiveBitonicSearch(items, lo, mid-1, key);
      else return mid;
    }
    // split case where mid is a Max element
    else {
      // if key is less than Max recurse on its right
      if(cmpKey<0)  return recursiveBitonicSearch(items, mid+1, hi, key);
      else if(cmpKey>0)  return recursiveBitonicSearch(items, lo, mid-1, key);
      else return mid;
    }
  }

  public static void bitonicSort() {}

  // BS client code:
  public static void main(String [] args) {
    Integer [] items = new Integer[]{1,34, 5, 23, 26, 12, 5, 6, 5, 5, 8, 4, 3, 2, 34, 21, 16, 89};
    // Sort the array:
    Arrays.sort(items); // sort array of Comparable types in-place, using Dual-Pivot Quick Sort E(RT)=NlogN
    System.out.println(contains(items, 13));
    System.out.println(contains(items, 4));
    System.out.println(contains(items, 26));

    System.out.println();
    System.out.println();

    System.out.println(indexOf(items, 13));
    System.out.println(indexOf(items, 4));
    System.out.println(indexOf(items, 26));

    System.out.println(Arrays.binarySearch(items, 13));
    System.out.println(Arrays.binarySearch(items, 4));
    System.out.println(Arrays.binarySearch(items, 26));

    System.out.println();
    System.out.println();

    System.out.println(Arrays.binarySearch(items, 13)>=0);
    System.out.println(Arrays.binarySearch(items, 4)>=0);
    System.out.println(Arrays.binarySearch(items, 26)>=0);

    System.out.println();
    System.out.println();

    System.out.println(select(items, 4));
    System.out.println(select(items, 5));
    System.out.println(select(items, 8));
    System.out.println(items[3]);
    System.out.println(items[4]);
    System.out.println(items[7]);

    System.out.println();
    System.out.println();

    System.out.println(Arrays.toString(items));
    System.out.println();
    System.out.println("10's ranks is:" + rank(items, 10));
    System.out.println(items[rank(items, 10)]);
    System.out.println();
    System.out.println("20's ranks is:" + rank(items, 20));
    System.out.println(items[rank(items, 20)]);
    System.out.println();
    System.out.println("40's ranks is:" + rank(items, 40));
    System.out.println(items[rank(items, 40)]);
    System.out.println();
 
    Integer[] bitonic = new Integer[]{8, 9, 10, 13, 15, 16, 22, 23, 15, 14, 13, 12, 9, 8, 7, 6, 5};
    System.out.println("Bitonic Max is: " + bitonicMax(bitonic));
    System.out.println("Bitonic Min is: " + bitonicMin(bitonic));
    System.out.println("Bitonic array: ");
    System.out.println(Arrays.toString(bitonic));
    System.out.println("Bitonic Search: " + bitonicSearch(bitonic, 23));
    System.out.println("Bitonic Search: " + bitonicSearch(bitonic, 12));
    System.out.println("Bitonic Search: " + bitonicSearch(bitonic, 8));
    System.out.println();

    Integer[] bitonicUp = new Integer[]{8, 9, 10, 13, 15, 16, 22, 23, 122, 145, 188};
    System.out.println("BitonicUp Max is: " + bitonicMax(bitonicUp));
    System.out.println("BitonicUp Min is: " + bitonicMin(bitonicUp));
    System.out.println("BitonicUp array: ");
    System.out.println(Arrays.toString(bitonicUp));
    System.out.println("Bitonic Search: " + bitonicSearch(bitonicUp, 23));
    System.out.println("Bitonic Search: " + bitonicSearch(bitonicUp, 12));
    System.out.println("Bitonic Search: " + bitonicSearch(bitonicUp, 8));
    System.out.println();
    System.out.println();

    Integer[] bitonicDown = new Integer[]{23, 15, 14, 13, 12, 9, 8, 7, 6, 5};
    System.out.println("BitonicDown Max is: " + bitonicMax(bitonicDown));
    System.out.println("BitonicDown Min is: " + bitonicMin(bitonicDown));
    System.out.println("Bitonic array: ");
    System.out.println(Arrays.toString(bitonicDown));
    System.out.println("Bitonic Search: " + bitonicSearch(bitonicDown, 23));
    System.out.println("Bitonic Search: " + bitonicSearch(bitonicDown, 12));
    System.out.println("Bitonic Search: " + bitonicSearch(bitonicDown, 8));
    System.out.println();
    System.out.println();
  }
}
