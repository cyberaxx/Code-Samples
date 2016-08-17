public class Heap {
  // sort an array of Comparable type items using MaxPQ
  public static void sort(Comparable[] items) {
    // extreme test cases: null array, empty array and array of size 1
    if(items==null || items.length<2) return ;

    // NOTE: unlike MaxPQ, the input array is 0-base indexed:

    // 1. treat the input array as representation of Binary Heap:
    int i=items.length/2; // ignore the last level nodes (leaves)
    // Heapify the Binary Heap
    while(i>=1) {
      sink(items, i, items.length); // sink the node i within the Binary Heap rooted at i
      i--;
    }

    // 2. delMax from MaxPQ iteratively and preserve MaxPQ INVARIANCE
    heapSort(items); // in-place
  }

  // Assumption: input array of items is a Binaray Heap representation of a MaxPQ
  private static void heapSort(Comparable[] items) {
    // MaxPQ size:
    int N=items.length;
    // delMax from MaxPQ iteratively and preserve MaxPQ INVARIANCE (N times)
    for(int i=0; i<N; i++) {
      // exchange the root with the last item in the heap:
      exch(items, 1, N--); // reduce the size of the heap
      sink(items, 1, N); // sink the new root to its proper level of competence {O(logN)}
    }
  } // O(NlogN)

  // Demote a new root to its level of competence
  private static void sink(Comparable [] items, int i, int N) {
    
  }

  // NOTE: 0-base Array vs. 1-base MAxPQ

  // generic comparison to compare to Comparable instances:
  private static boolean less(Comparable [] items, int i, int j) {
    return false;
  }

  // exchange the position of two items in a given array of Comparable items
  private static void exch (Comparable [] items, int i, int j) {
    Comparable temp=items[i];
    items[i]=items[j];
    items[j]=temp;
  }
}
