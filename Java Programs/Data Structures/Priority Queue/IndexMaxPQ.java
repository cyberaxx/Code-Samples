/*
Index priority-queue implementation. Implement IndexMaxPQ.java by modifying MaxPQ.java as follows: Change pq[] to hold indices, add an array keys[] to hold the key values, and add an array qp[] that is the inverse of pq[] — qp[i] gives the position of i in pq[] (the index j such that pq[j] is i). Then modify the code to maintain these data structures. Use the convention that qp[i] is -1 if i is not on the queue, and include a method contains() that tests this condition. You need to modify the helper methods exch() and less() but not sink() or swim().
*/
import java.util.NoSuchElementException;

public class IndexMaxPQ<Key extends Comparable<Key>> {
  // instance variables:
  private Key[] keys; // Key[] of key to maintain collection of java Comparable type keys
  private int[] pq; // an array that maintains the external index for any given index in 1-based array that represent MaxPQ
  private int[] qp; // an array that maintains the heap position of any given external index

  private int N; // number of items in the MaxPQ instance
  private int maxN; // max number of items to be stored in MaxPQ instance

  // Constructor:
  public IndexMaxPQ(int max) {
    // check if max denotes a valid max size for IndexMaxPQ instance:
    if(max<0) throw new IllegalArgumentException();
    
    // Otherwise: initialize instance variables:
    this.maxN=max;
    N=0;
    keys=(Key[]) new Comparable[maxN];// UGLY CASTING: java does not allow GENERIC array creation
    pq=new int[maxN+1]; // pq[i]: i can be within [1 maxN] => pq.length must be maxN+1
    qp=new int[maxN]; // qp[i]: i can be any within [0 maxN-1]
    // initialize heap indeces for all external indeces to be -1:
    for(int i=0; i<maxN; i++)
      qp[i]=-1; 
  }

  // API:
  // insert an item with external index "index" to a Max oriented priority queue instance
  public void insert(int index, Key key){}

  // change the key that has been associated with the given external index "index" (preserve Max oriented priority queue HEAP-ORDERED condition)
  public void change(int index, Key key){}

  // delete the key associate with the given index from the Max oriented priority queue instance (preserve Max oriented priority queue HEAP-ORDERED condition)
  public void delete(int index){}

  // exctract the max Key from the Max oriented priority queue instance (preserve Max oriented priority queue HEAP-ORDERED condition)
  public Key delMax(){
    // UNDERFLOW: 
    // check if the IndexMaxPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to delete maximum item in the IndexMaxPQ instance because it was empty!");
    
    // a. take a copy of the maximum item:
    Key max=max();
    int maxIndex=maxIndex();
    /* remove it from max oriented priority:
       1. exchange it with the last node:
       2. decrease the N by one
       3. check if IndexMaxPQ is already empty
       4. if not, sink down the new root to its rightful level of comptence
    */
    
    // b. update all 3-parallel arrays:the heap position associate with the max external index to -1;
    keys[maxIndex]=null; // prevent loitering 
    qp[maxIndex]=-1; // the heap position associate with the max external index to -1;

    return max;
  }

  // sneek peek at the Max element in the Max oriented priority queue instance (if exists such a element)
  public Key max() {
    // check if the IndexMaxPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to retrieve the maximum item in the IndexMaxPQ instance because it was empty!");
    return keys[pq[1]];// external index for a key at the heap position 1: root of the MaxPQ
  }

  // returns the external index associated with the max element in the Max oriented priority queue instance (if exists such a element)
  public int maxIndex() {
    // check if the IndexMaxPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to retrieve the index of max item in the IndexMaxPQ instance because it was empty!");
    return pq[1];// external index for a key at the heap position 1: root of the MaxPQ
  }

  // check if there exists any key associated with the given index in the Max oriented priority queue instance
  public boolean contains(int index){return false;}

  // check if the Max oriented priority queue instance is empty:
  public boolean isEmpty(){return N==0;}

  // returns number of elements in the Max oriented priority queue instance:
  public int size(){return N;}

  // Helper methods: sink, swim, generic comparison, exchange

  // iterator:
}
