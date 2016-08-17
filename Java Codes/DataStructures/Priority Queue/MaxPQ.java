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
    items = (Key[]) new Object[4]; // UGLY CASTING: Java does NOT allow generci array creation
  }

  // instance methods:
  // API: insert(Key key), delMAx(), size(), isEmpty(), max()
  public void add(Key key) {
    /* OVERFLOW:
       before addition check if the array is full:
       double the size of the array
    */
    if(N==items.length-1) resize(2*items.length);   
    // add the new key to the end of the items array:
    // start inserting from index 1:
    items[++N]=key; // and update the N (number of element in the MaxPQ)
    // swim up the key to its rightful positon in the MaxPQ instance:
    swim(N); // O(logN)
  }

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
    sink(1); // O(logN) AMORTIZED
    // prevent loitering:
    items[N+1]=null;

    // RESIZING: if N==(items.length/4) - 1 shrink to half
    if(N==(items.length/4)-1) resize(items.length/2);

    // return the MAX:
    return max;
  }

  // number of elements in MaxPQ instance:
  public int size(){return N;}
  public Key max(){return items[1];}
  public boolean isEmpty() {return N==0;}

  // Helper instance methods:

  /* PROMOTION: after inserting a new node to a MaxPQ instance
     if a child's key is greater than is parent, it has to SWIM up to its rightful position in the MaxPQ
  */
  private void swim(int i) {
    // while we have not hit the root node, and i's parent's key is less than its own key, SWIM up
    while(i>1 && less(i/2, i)) {
      exch(i, i/2); // exchange i's key with its parent (promot it)
      i=i/2; // swim up on level in the binary heap
    }
  }

// node demotion after deletion
  private void sink(int i){} 


  // array resizing: make insertion O(logN) AMORTIZED for N insertion
  private void resize(int capacity) {
    // create a new array with new size:
    Key[] temp=(Key[]) new Object[capacity]; // UGLY CASTIN: NO GENERIC ARRAY CREATION!!!
    // copy items over from the original array to the new array:
    for(int i=1; i<items.length; i++)
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
  public Iterator<Key> iterator() {return new ListIterator();}

  /* ListIterator class implements Java Iterator interface by
     overriding its abstract methods next() and hasNext():
     NOTE: The iterator does not return the elements in any particular order!
  */ 
  private class ListIterator implements Iterator<Key> {
    private int current;
    public ListIterator() {current=1;}
    @Override
    public boolean hasNext(){return items[current]==null;}
    @Override
    public Key next(){
      // check if it hasNext():
      if(!hasNext()) throw new NoSuchElementException("Failed to perform next() because MaxPQ does not have next!");
      Key next=items[current++];
      return next;
    }
  }
}
