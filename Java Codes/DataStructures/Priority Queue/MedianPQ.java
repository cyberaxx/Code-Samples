/*
Dynamic-median finding:
Design a data type that supports insert in logarithmic time, find the median in constant time, and remove the median in logarithmic time.
*/
import java.util.Iterator;
import java.util.Deque;
import java.util.ArrayDeque;

// MedianPQ is a Collection data type which contain generic keys that have total ordering:
// It's type prameter is Key
public class MedianPQ<Key extends Comparable<Key>> implements Iterable<Key> {
  /* Data Structure Invariant:
     Median is alway accessible at the head of priority queue O(1) time. 
     This invariant has to be presevered after any structural changes to priority queue (insertion/deletion)
  */
  // Instance variable:
  private Key[] items; // An array of generic type Key to maintain a collection of items
  private int N; // number of items in an instance of MedianPQ
  private Deque<Key> minStack; // maintain the min (Linear extra space) 
  private Deque<Key> maxStack; // maintain the max (Linear extra space)

  // Constructor:
  public MedianPQ() {
    // initialize:
    N=0;
    items=(Key[]) new Comparable[2]; // UGLY CASTING: Java does NOT allow generic array creation
    minStack=new ArrayDeque<Key>();
    maxStack=new ArrayDeque<Key>();
  }

  public MedianPQ(Key[] inputArray) {}
  // Instance methods:
  // API: insert/delete/isEmpty/size/min/max/toArray

  // Add a key to an instance of MedianPQ:
  public void add(Key key){}

  // delete Median for an instance of a MedianPQ:
  public Key delMedian(){return null;}

  // return the median of an instance of a MedianPQ:
  public Key median(){return null;}

  // return the min of an instance of a MedianPQ:
  public Key min(){return null;}

  // return the max of an instance of a MedianPQ:
  public Key max(){return null;}

  // return the number of elements in an instance of MedianPQ
  public int max(){return N;}

  // check if an instance of a MedianPQ is empty:
  public boolean isEmpty(){return N==0;}

  // return an array representation on of an instance of a MedianPQ
  public Key[] toArray(){return null;}

  // Helper methods:
  private void resize(int capacity){} 
  
  // Sink and swim operations:
  // Sink down a new root to its rightful level of competence:
  private void sink(int k){}
 
  // Swim up the new added node to its rightful level of competence:
  private void swim(int k){}

  // generic comparison and swap method
  private boolean less(int i, int j) {return false;}
  private boolean greater(int i, int j) {return false;}
  private void exchange(int i, int j){}

  // check if an instance of a MedianPQ satisfies Median-Heap-Ordered condintion
  private boolean isMedianPQ(){return false;}


  // Iterator:
  /*
   As a Collection data type, MedianPQ implements java Iterable interface
   by overriding its public abstract method iterator() to enable a client 
   to iterate over items within the Median Priority Queue Collection typ:
  */
   public Iterator<Key> iterator(){return null;} 
}
