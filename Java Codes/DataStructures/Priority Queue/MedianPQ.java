/*
Dynamic-median finding:
Design a data type that supports insert in logarithmic time, find the median in constant time, and remove the median in logarithmic time.

http://algs4.cs.princeton.edu/24pq/
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.Deque;
import java.util.ArrayDeque;

import java.util.Arrays;

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
  public void add(Key key) {
    // OVERFLOW: check if array is not already full:
    if(N==items.length-1)  resize(2*items.length);
    
    // Max, Min update:
    if(isEmpty()) {
      maxStack.push(key);
      minStack.push(key);
    }
    //MedianPQ instance is not empty compare the curren key with the top of Min, Max stacks
    else {
      // if key "is less than or equal to the min (minStack.peek() item) push it to the min stack
      if(key.compareTo(minStack.peek())<=0)  minStack.push(key);
      // if key "is greater than or equal to the max (maxStack.peek() item) push it to the max stack
      if(key.compareTo(maxStack.peek())>=0)  maxStack.push(key);
    }

    // add the new item to the end of MedianPQ instance array:
    items[++N]=key;
    // swim it up to its rightful level of competence:
    swim(N);
  }

  // delete Median for an instance of a MedianPQ:
  public Key delMedian(){return null;}

  // return the median of an instance of a MedianPQ:
  public Key median(){
    // if priority queue is empty throw exception:
    if(isEmpty()) throw new NoSuchElementException("Failed to perfom median() operation because a MedianPQ is Empty!");
    // otherwise return the head of the array items[1]
    return items[1];
  }

  // return the min of an instance of a MedianPQ:
  public Key min(){
    // if priority queue is empty throw exception:
    if(isEmpty()) throw new NoSuchElementException("Failed to perfom min() operation because a MedianPQ is Empty!");
    // otherwise return the top of the min stack
    return minStack.peek();
  }

  // return the max of an instance of a MedianPQ:
  public Key max(){
    // if priority queue is empty throw exception:
    if(isEmpty()) throw new NoSuchElementException("Failed to perfom max() operation because a MedianPQ is Empty!");
    // otherwise return the top of the max stack
    return maxStack.peek();
  }

  // return the number of elements in an instance of MedianPQ
  public int size(){return N;}

  // check if an instance of a MedianPQ is empty:
  public boolean isEmpty(){return N==0;}

  // return an array representation on of an instance of a MedianPQ
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
  private void sink(int k){} 

  // Swim up the new added node to its rightful level of competence:
  private void swim(int k) {}

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

  // check if an instance of a MedianPQ satisfies Median-Heap-Ordered condintion
  private boolean isMedianPQ(){return false;}


  // Iterator:
  /*
   As a Collection data type, MedianPQ implements java Iterable interface
   by overriding its public abstract method iterator() to enable a client 
   to iterate over items within the Median Priority Queue Collection typ:
  */
   public Iterator<Key> iterator(){return null;} 


  // test client:
  public static void main(String[] args) {
    MedianPQ<Integer> pq=new MedianPQ<Integer>();
    for(int i=0; i<11; i++) pq.add(i);
    System.out.println("Min: " + pq.min());
    System.out.println("Max: " + pq.max());
    System.out.println("Median: " + pq.median());
    System.out.print(Arrays.toString(pq.items));
    System.out.println();
  }

}
