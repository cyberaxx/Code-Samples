public class Heap {
  // sort an array of Comparable type items using MaxPQ
  public static void sort(Comparable[] items) {
    // extreme test cases: null array, empty array and array of size 1
    if(items==null || items.length<2) return ;
    // if the array is already sorted: O(n) no need to sort
    if(isSorted(items)) return ;

    // NOTE: unlike MaxPQ, the input array is 0-base indexed:

    // 1. treat the input array as representation of Binary Heap:
    int i=items.length/2; // ignore the last level nodes (leaves)
    // Heapify the Binary Heap
    while(i>=1) {
      sink(items, i, items.length); // sink the node i within the Binary Heap rooted at i
      i--;
    }

    // 2. delMax from MaxPQ iteratively and preserve MaxPQ INVARIANCE
    // MaxPQ size:
    int N=items.length;
    // delMax from MaxPQ iteratively and preserve MaxPQ INVARIANCE (N times)
    for(int j=0; j<items.length; j++) {
      // exchange the root with the last item in the heap:
      exch(items, 1, N--); // reduce the size of the heap
      sink(items, 1, N); // sink the new root to its proper level of competence {O(logN)}
    } // O(NlogN)
    assert isSorted(items);
  }

  // Demote a new root to its level of competence
  private static void sink(Comparable [] items, int i, int N) {
     // keep demoting unless: 1. "node i" has no child (2*i>N), 2. no is not less than its children
     while (2*i<=N) {
       int j=2*i; // node i's left child
       // compare left child with the right child (if the right child exists):
       if(j+1<=N && less(items, j, j+1)) j++; // pick the right child if it's the bigger one

       // j is the index of the greates child
       // compare the greates child with the node i:
       if(!less(items, i, j)) break; // parent is no less than its biggest child, sink terminated!

       // exchange parent with the biggest child and keep sinking:
       exch(items, i, j); // exchange values
       i=j; // demote node i to j's position
     }
  }

  // NOTE: 0-base Array vs. 1-base MAxPQ
  // generic comparison to compare to Comparable instances:
  private static boolean less(Comparable [] items, int i, int j) {
    return items[i-1].compareTo(items[j-1])<0;
  }
  // exchange the position of two items in a given array of Comparable items
  private static void exch (Comparable [] items, int i, int j) {
    Comparable temp=items[i-1];
    items[i-1]=items[j-1];
    items[j-1]=temp;
  }


  // check if the array is sorted
  private static boolean isSorted(Comparable [] items) {
    for(int i=1; i<items.length; i++) {
      if(less(items[i], items[i-1])) return false;
    }
    return true;
  }  
  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w)<0;
  }

  public static boolean isMaxPQ (Comparable[] items, int i, int N){return false;}
}
