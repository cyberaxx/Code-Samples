import java.util.Iterator;
import java.util.NoSuchElementException;

// MinPQ is a Collection data type which contain generic keys that have total ordering:
// It's type prameter is Key
public class MinPQ<Key extends Comparable<Key>> implements Iterable<Key> {
  /* Data Structure Invariant:
     Min elemnt is alway accessible at the head of priority queue in O(1) time. 
     This invariant has to be presevered after any structural changes to priority queue (insertion/deletion)
  */
  // Instance variable:
  private Key[] items; // An array of generic type Key to maintain a collection of items
  private int N; // number of items in an instance of MinPQ
  private Key max; // the Max element in MinPQ collection of Comparable items (items with a total order -> Natural ordering due to their corresponding compareTo() method)

  // Constructor:
  public MinPQ() {
    // initialize:
    N=0;
    max=null;
    items=(Key[]) new Comparable[2]; // UGLY CASTING: Java does NOT allow generic array creation
  }

  // Constructor:
  // takes an array of Comparable items as input, and create a MinPQ using binary heap datastructure (Complete binary tree with min-heap-ordered condition)
  public MinPQ(Key[] input) {
    // items array is 1-based index:
    items=(Key[]) new Comparable[input.length+1];
    N=0; // initialize the number of element in the heap
    max=input[0]; // initialize max
    for(int i=0; i<input.length; i++) {
      items[i+1]=input[i];
      N++; // add to the number of element in the heap
      // update the max:
      if(greater(input[i], max)) max=input[i];
    }

    // heapify the items array: 1-based array -> root is at index 1:
    for(int i=N/2; i>=1; i--) {
      // sink node i to its rightful level of competence (after this operation the subtree rooted at i would satisfy the heap-order condition)
      sink(i);
      // check if subarry items[i N] is a valid representation of a MinPQ (a complete binary tree with min-heap-order condition)
      assert isMinPQ(i);
    }
    assert isMinPQ(); // check if the item array is a valid representation of a binary min heap (a complete binary tree with min-heap-order condition)
  }

  // Instance methods:
  // API: insert/delete/isEmpty/size/min/max/toArray

  // Add a key to an instance of MinPQ:
  public void add(Key key) {
    // OVERFLOW: compare the array size with the number of element already inside the array:
    // if array is full (consider one extra array entry for 1-based indexing), double its size:
    if(N==items.length-1)  resize(2*(items.length));

    // update the max:
    // case 1: check if MinPQ is already empty:
    if(isEmpty())  max=key;// the new key is min and max!
    else if(greater(key, max))  max=key;

    // add the new key to the end of the binary heap (1-based index array representation)
    items[++N]=key;

    // swim it up to its rightful position:
    swim(N);

    assert isMinPQ(); // after sink operation check if the items array represent a binaray min-heap
  }

  // delete Min for an instance of a MinPQ:
  public Key delMin(){
    // UNDERFLOW:
    if(isEmpty()) throw new NoSuchElementException("Failed to perfom delMin() operation because a MinPQ is Empty!");
    // delete the MinPQ head item:
    Key item=items[1];
    // Pay the piper to maintain the DS invariance: items[1] always contain the min of elements in the MinPQ collection instance:
    exch(1, N); // exchange the last node of the binary heap (the item in the array with the root elements)
    N--; // decrease the number of elements in the MinPQ before sinking;
    // sink the new root node to its rightful level of competenece in a new binary min heap with N-1 nodes:
    sink(1);

    assert isMinPQ(); // after sink operation check if the items array represent a binaray min-heap

    // loitering avoidance:
    items[N+1]=null;

    // Check if shrinking required for memory efficiency: if array is quarter full: shrink it to 1/2 size:
    if(N==( (items.length)/4) - 1)  resize(items.length/2);

    // return the min from the MinPQ instance:
    return item;
  }

  // return the Min of an instance of a MinPQ:
  public Key min(){
    // if priority queue is empty throw exception:
    if(isEmpty()) throw new NoSuchElementException("Failed to perfom min() operation because a MinPQ is Empty!");
    // otherwise return the head of the array items[1]
    return items[1];
  }

  // return the max of an instance of a MinPQ:
  public Key max(){
    // if priority queue is empty throw exception:
    if(isEmpty()) throw new NoSuchElementException("Failed to perfom max() operation because a MinPQ is Empty!");
    return max;
  }

  // return the number of elements in an instance of MinPQ
  public int size(){return N;}

  // check if an instance of a MinPQ is empty:
  public boolean isEmpty(){return N==0;}

  // return an array representation on of an instance of a MinPQ
  public Key[] toArray(){
    // instantiate a new array of generic type Key (subtype of java Comparable interface)
    Key[] temp=(Key[]) new Comparable[N]; // UGLY CASTING
    for(int i=1; i<=N; i++)  temp[i-1]=items[i]; // handle the 1-based to 0-based conversion
    return temp;
  }

  // Helper methods:
  private void resize(int capacity){
    // instantiate a new array of type Key[] with a new capacity:
    Key[]temp=(Key[]) new Comparable[capacity]; // UGLY CASTIN
    // COPY over from the original array of items, to the new array (NOTE: 1-based array is used to represent binary heap!)
    for(int i=1; i<=N; i++) {
      temp[i]=items[i]; // do Not touch the 0-index item
    }
    items=temp;
  } 

  // Sink and swim operations:
  // Sink down a new root to its rightful level of competence:
  private void sink(int k) {}
 
  // Swim up the new added node to its rightful level of competence:
  private void swim(int k){}

  // generic comparison and swap method
  private boolean less(int i, int j) {
    return items[i].compareTo(items[j])<0;
  }
  private boolean greater(int i, int j) {
    return items[i].compareTo(items[j])>0;
  }
  private void exch(int i, int j) {
    Key temp=items[i];
    items[i]=items[j];
    items[j]=temp;
  }

  // check if an instance of a MinPQ satisfies Min-Heap-Ordered condintion
  private boolean isMinPQ(){return isMinPQ(1); }// heap rooted at root :)
  // check if the subarray items[i N] is a valid representation of MinPQ:
  // A complete binary tree that satisfies min-heap-ordered condition: no parent has a key greater than its childern's
  private boolean isMinPQ(int i){
    //BASE CASE:
    // leaves and empty MinPQ are all heap:
    if(i>=N) return true;

    // RECURRENCE:
    int left=2*i;
    int right=2*i+1;
    if(left<=N && greater(i, left)) return false; // root is greater than the left child -> min-heap order condition
    if(right<=N && greater(i, right)) return false; // root is greater than the right child -> min-heap order condition
    return isMinPQ(left) && isMinPQ(right); // check if subtree rooted at left and right indeces isMinPQ
  }

  // Iterator:
  /*
   As a Collection data type, MinPQ implements java Iterable interface
   by overriding its public abstract method iterator() to enable a client 
   to iterate over items within the Min Priority Queue Collection typ:
  */
   public Iterator<Key> iterator(){return null;} 

  // Heap Sort using MinPQ
  public static void sort(Comparable[] items){}

  private static void heapify(Comparable[] items) {
    // extreme test cases: empty array, and array with one item is heapified already: 
    if(items.length<2) return ;     
    // For arrays with 2 items or more:
    // N is the index of the last node of the biary heap represented by items array
    int n=items.length; 
    // go to N's node parent and check if heap-order condition is violated:
    for(int i=n/2; i>=0; i--) {
      sink(items, i, n);
    }
  }

  public static void exch (Comparable[] items, int i, int j){}
  public static boolean greater (Comparable[] items, int i, int j){return false;}
  private static boolean greater(Comparable v, Comparable w) {return v.compareTo(w)>0;}
  public static void sink (Comparable[] items, int i, int N){}
}
