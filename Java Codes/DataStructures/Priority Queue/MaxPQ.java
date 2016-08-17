import java.util.NoSuchElementException;
import java.util.Iterator;

/* Priority Queue is a Collection data type that maintains a collection of Comparable items of generic type Key
   Key data type has implemented a compareTo method to mimic total ordering for its instances:
*/
public class MaxPQ<Key extends Comparable<Key>> implements Iterable<Key>{
  /*
    implementation note: in order to implement PQ:
    1. implement MaxPQ by implicit binary tree with array (complete binary tree which is perfectly balanced except the last level)
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
    items[++N]=key; // and update the N (number of element in the MaxPQ)
    // swim up the key to its rightful positon in the MaxPQ instance:
    swim(N); // O(logN)
  }

  public Key delMax() {return null;}

  // number of elements in MaxPQ instance:
  public int size(){return N;}
  public Key max(){return items[1];}
  public boolean isEmpty() {return N==0;}

  // Helper instance methods:
  private void sink(int i){} // node demotion after deletion

  // node promotion after insertion
  private void swim(int i){}

  // array resizing: make insertion O(logN) AMORTIZED for N insertion
  private void resize(int capacity) {}

  // generic comparison method based on compareTo method on instances of class Key 
  private boolean less(int i, int j) {return false;}
  // exch method
  private void exch(int i, int j) {}

  /*
    MaxPQ implements the java Iterable interface for Comparable type Key by
    overrding its abstract method iterator():
  */
  @Override
  public Iterator<Key> iterator() {return null;}
}
