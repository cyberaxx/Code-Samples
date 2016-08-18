import java.util.NoSuchElementException;
import java.util.Iterator;

/* Priority Queue is a Collection data type that maintains a collection of Comparable items of generic type Key
   Key data type has implemented a compareTo method to mimic total ordering for its instances:
*/
public class MaxPQ<Key extends Comparable<Key>> implements Iterable<Key>{
  /*
    implementation note: in order to implement PQ:
    1. implement MaxPQ by implicit binary heap with an array:
       (binary heap: complete binary tree which is perfectly balanced except the last level, and satisfies heap condition: Parent's key is GREATER than its both childern's)
    2. Resizable array implementation
    3. 1-base array index rather 0-base: to simply calculate parent/child node index
  */

  // instance variables:
  private int N; // elements in the MaxPQ
  private Key[] items; // maintain collection of keys in resizing array:

  // Constructor:
  public MaxPQ() {
    N=0; // empty MaxPQ
    items = (Key[]) new Comparable[2]; // UGLY CASTING: Java does NOT allow generci array creation
  }

  // Constructor with array input:
  public MaxPQ(Key[] input) {
    // initialize the number of element
    N=0;
    // initialize the items array
    items=(Key[]) new Comparable[input.length+1]; // UGLY CASTING

    // Copy over the input array to the instance array:
    for(int i=0; i<input.length; i++) {
      items[i+1]=input[i]; // instance array starts at index 1, rather than 0
      N++;
    }

    // HEAPIFY the items array: O(n)
    for(int i=N/2; i>=1; i--) {
      sink(i);
    }

    assert isMaxPQ(1);
    
  }
 
  // instance methods:
  // API: insert(Key key), delMAx(), size(), isEmpty(), max()

  // O(logN) AMORTIZED
  public void add(Key key) {
    /* OVERFLOW:
       before addition check if the array is full:
       double the size of the array
    */
    if(N>=items.length-1) resize(2*items.length);   
    // add the new key to the end of the items array:
    // start inserting from index 1:
    items[++N]=key; // and update the N (number of element in the MaxPQ)
    // swim up the key to its rightful positon in the MaxPQ instance:
    swim(N); // O(logN)
    assert isMaxPQ(1);
  }

  // O(logN) AMORTIZED
  public Key delMax() {
    /* UNDERFLOW: 
       check if MaxPQ is not empty already:
    */
    if(isEmpty()) throw new NoSuchElementException("Failded to perform delMax because MaxPQ is empty!");
    // copy the Max element from the index 1 of the items array (Max alway has to be at items[1] (Invarinat)
    Key max=items[1];
    // swap max and the last node in the binary heap (items[N])
    exch(1, N);
    // remove the last node from the heap
    N--;
    // PAY the Piper to maintanin MaxPQ INVARIANCE:
    // sink down the new root to its rightful position in the heap
    sink(1); // O(logN)
    assert isMaxPQ(1);

    // prevent loitering:
    items[N+1]=null;

    // RESIZING: if N==(items.length-1)/4  shrink to half
    if(N>0 && N==(items.length-1)/4) resize(items.length/2);

    // return the MAX:
    return max;
  }

  // number of elements in MaxPQ instance:
  public int size(){return N;}
  // sneek peek at the max:
  public Key max(){
    if(isEmpty()) throw new NoSuchElementException("Failed to perfom max() operation because the MaxPQ instance is empty!");
    return items[1];
  }
  public boolean isEmpty() {return N==0;}
  public Key[] toArray() {
    Key[] pq = (Key[]) new Comparable[N]; // UGLY CASTING
    for(int i=0; i<N; i++) pq[i]=items[i+1]; // 1-base index to 0-base index
    return pq;
  }

  // Helper instance methods:

  // check if subtree rooted at k is a MaxPQ:
  // recursive 1. base case, 2. Recurrence (Divide and Conquer)
  private boolean isMaxPQ(int k) {
    // BASE CASE:
    if(k>N) return true; // if node is outside the MaxPQ bound, new node rooted at itseld is a MaxPQ
    // RECURRENCE:
    int left=2*k;
    int right=2*k+1;
    // if node has a child:
    if(left<=N)   if(less(k, left))   return false;
    if(right<=N)  if(less(k, right))  return false;
    return isMaxPQ(left) && isMaxPQ(right);
  }

  /* PROMOTION: 
     insertion, a new node added to a MaxPQ instance
     if its key was greater than its parent, it has to SWIM up to its rightful position in the MaxPQ
  */
  private void swim(int i) {
    // while we have not hit the root node, and i's parent's key is less than its own key, SWIM up
    while(i>1 && less(i/2, i)) {
      exch(i, i/2); // exchange i's key with its parent (promot it)
      i=i/2; // swim up on level in the binary heap
    }
  }

  /* DEMOTION:
     deletion, a new root is out of place, it has to SINK down to its rightful position until we hit the last level or the node's key is no less than its childern's
     otherwise, swap the node with its child with the largest key and keep sinking!
  */
  private void sink(int i){
    // while the current node still have a child:
    while(2*i<=N) {
      int j=2*i; // left child
      // compare left and right if right exist
      if( j+1<=N && less(j, j+1) ) j++; // pick the right child for promotion, OW the left child
      // compare parent with the bigger of the left and right child (if exists)
      if(!less(i, j)) break; // parent is no less than the bigger of its childern

      // Otherwise:
      exch(i, j); // exchange parent and the bigger child
      i=j; // demote the parent to its child level
    }
  } 

  // array resizing: make insertion O(logN) AMORTIZED for N insertion
  private void resize(int capacity) {
    // create a new array with new size:
    Key[] temp=(Key[]) new Comparable[capacity]; // UGLY CASTIN: NO GENERIC ARRAY CREATION!!!
    // copy items over from the original array to the new array:
    for(int i=1; i<=N; i++)
      temp[i]=items[i];
    items=temp;
  }

  // generic comparison method based on compareTo method on instances of class Key 
  private boolean less(int i, int j) {
    // because Key instnaces are subtype of Comparable type:
    // they must have a TOTAL ORDERTING and a compareTo method to implement that ordering
    return items[i].compareTo(items[j])<0;
  }

  // exch method
  private void exch(int i, int j) {
     Key temp=items[i];
     items[i]=items[j];
     items[j]=temp;
  }

  /*
    MaxPQ implements the java Iterable interface for Comparable type Key by
    overrding its abstract method iterator():
  */
  @Override
  public Iterator<Key> iterator() {return new HeapIterator();}

  /* ListIterator class implements Java Iterator interface by
     overriding its abstract methods next() and hasNext():
     NOTE: The iterator does not return the elements in any particular order!
  */ 
  private class ListIterator implements Iterator<Key> {
    private int current;
    public ListIterator() {current=1;}
    @Override
    public boolean hasNext(){return current<=N;}
    @Override
    public Key next(){
      // check if it hasNext():
      if(!hasNext()) throw new NoSuchElementException("Failed to perform next() because MaxPQ does not have next!");
      Key next=items[current++];
      return next;
    }
  }

  // Concrete class that implements java Iterator interface must always be
  // an Inner class because it is associated with an instance of Collection type
  // in this case MaxPQ:
  private class HeapIterator implements Iterator<Key> {
    private MaxPQ<Key> copyMaxPQ;
    public HeapIterator() {
      copyMaxPQ=new MaxPQ<Key>(MaxPQ.this.toArray());
    }
    @Override
    public boolean hasNext() {return !copyMaxPQ.isEmpty();}
    @Override
    public Key next() {
      Key item = copyMaxPQ.delMax();
      return item;
    }
  }

}
