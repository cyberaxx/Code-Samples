/*
Dynamic-median finding:
Design a data type that supports insert in logarithmic time, find the median in constant time, and remove the median in logarithmic time.

Solution. Keep the median key in v; 
use a max-oriented heap for keys less than the key of v; 
use a min-oriented heap for keys greater than the key of v. 
To insert, add the new key into the appropriate heap, replace v with the key extracted from that heap.

http://algs4.cs.princeton.edu/24pq/
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
  Implement the MedianPQ data type using binary heap abstraction (a complete binary tree that preserve head order condition).
  However, we modify the heap order condition invariance to address the median rather than the max or min.
*/

public class MedianPQ<Key extends Comparable<Key>> implements Iterable<Key> {
  // instance field of MedianPQ:
  // a resizable array of generic type Key to maintain a collection of keys
  private Key[] pq;
  // number of items in Medain PQ
  private int N;

  // Constructor
  public MedianPQ() {
    // use 1-based index array for heap representation
    pq=(Key[]) new Comparable[16]; // UGLY CASTING: java does not allow generic array creation
    N=0; // no item in the MedainPQ
  } 

  // API: 
  // instance method:
  // insert a new item to the MedainPQ (preserving MedianPQ order condition)
  public void insert(Key key){}
  // remove the head (median) of the MedianPQ (preserving MedianPQ order condition)
  public Key delMedian(){return null;}
  // sneek peek at the head of the MedianPQ (head is the median of items in the PQ):
  public Key median(){
    // if MedianPQ is empty
    if(isEmpty()) throw new NoSuchElementException();
    // otherwise: return the head of the MedianPQ
    return pq[1];
  }
  // check if MedianPQ is empty or not:
  public boolean isEmpty(){return N==0;}
  // number of items stored in MedianPQ
  public int size(){return N;}
  

  /* MedianPQ collection of generic keys implements java Iterable interface by
     overriding its abstract method iterator(): */ 
  @Override
  public Iterator<Key> iterator(){return null;}

  // helper methods:
  private void resize(int capacity){}
  private boolean isMedianPQ(){return false;}
  private void minSink(int k){
    // while node k has a chind in the binary heap (has not demoted to leave level yet!)
    while(2*k<=N) {
      int j=2*k; // k's left child in binary heap
      // check if k has a right child and the right child is the most qualified children:
      if(j+1<=N && compare(j+1, j)<0) j++; // the right child of node k is a candidate to replace it

      // compare the node k with its best of children:
      if(compare(k,j)<0) break; // k is at its rightful position
      // otherwise:
      exch(k,j);
      // move node k one level down:
      k=j;
    }
  }
  private void minSwim(int k){
    // while node k is not the root of binary MinHeap and min-heap-order condition is violated
    while(k>1 && compare(k, k/2)<0) {
      // promote node k to its parent's level
      exch(k,k/2);
      // move up one level:
      k=k/2;
    }
  }
  private void maxSink(int k){
    // while node k has a chind in the binary heap (has not demoted to leave level yet!)
    while(2*k<=N) {
      int j=2*k; // k's left child in binary heap
      // check if k has a right child and the right child is the most qualified children:
      if(j+1<=N && compare(j+1, j)>0) j++; // the right child of node k is a candidate to replace it

      // compare the node k with its best of children:
      if(compare(k,j)>0) break; // k is at its rightful position
      // otherwise:
      exch(k,j);
      // move node k one level down:
      k=j;
    }
  }
  private void maxSwim(int k){
    // while node k is not the root of binary MaxHeap and max-heap-order condition is violated
    while(k>1 && compare(k, k/2)>0) {
      // promote node k to its parent's level
      exch(k,k/2);
      // move up one level:
      k=k/2;
    }
  }
  // generic comparison of two keys in MedianPq
  private int compare(int i, int j) {return pq[i].compareTo(pq[j]);}
  // swap:
  private void exch(int i, int j) {
    Key temp=pq[i];
    pq[i]=pq[j];
    pq[j]=temp;
  }
}
