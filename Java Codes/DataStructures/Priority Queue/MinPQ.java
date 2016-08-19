import java.util.Iterator;
import java.util.NoSuchElementException;

// MinPQ is a Collection data type which contain generic keys that have total ordering:
// It's type prameter is Key
public class MinPQ<Key extends Comparable<Key>> implements Iterable<Key> {
  /* Data Structure Invariant:
     Min is alway accessible at the head of priority queue O(1) time. 
     This invariant has to be presevered after any structural changes to priority queue (insertion/deletion)
  */
  // Instance variable:
  private Key[] items; // An array of generic type Key to maintain a collection of items
  private int N; // number of items in an instance of MinPQ
  private Key max;

  // Constructor:
  public MinPQ() {
    // initialize:
    N=0;
    max=null;
    items=(Key[]) new Comparable[2]; // UGLY CASTING: Java does NOT allow generic array creation
  }

  public MinPQ(Key[] inputArray) {}
  // Instance methods:
  // API: insert/delete/isEmpty/size/min/max/toArray

  // Add a key to an instance of MinPQ:
  public void add(Key key){}

  // delete Min for an instance of a MinPQ:
  public Key delMin(){return null;}

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
  private void sink(int k) {
    // while we have not hit the lowest level in the binary heap: (still exists item in 2*k)
    while(2*k<=N) {
      int j=2*k; // left child of the current node
      int small=j;
      int big=j;
      // check if the it has a right child as well
      if(j+1<=N) {
        if(less(j, j+1)) big=j+1;
	else small=j+1;
      }

      
    }
  }
 
  // Swim up the new added node to its rightful level of competence:
  private void swim(int k){}

  // generic comparison and swap method
  private boolean less(int i, int j) {
    return items[i].compareTo(items[j])<0;
  }
  private boolean greater(int i, int j) {
    return items[i].compareTo(items[j])>0;
  }
  private void exchange(int i, int j) {
    Key temp=items[i];
    items[i]=items[j];
    items[j]=temp;
  }

  // check if an instance of a MinPQ satisfies Min-Heap-Ordered condintion
  private boolean isMinPQ(){return false;}


  // Iterator:
  /*
   As a Collection data type, MinPQ implements java Iterable interface
   by overriding its public abstract method iterator() to enable a client 
   to iterate over items within the Min Priority Queue Collection typ:
  */
   public Iterator<Key> iterator(){return null;} 
}
