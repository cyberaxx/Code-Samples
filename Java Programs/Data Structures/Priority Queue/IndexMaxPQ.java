/*
Index priority-queue implementation. Implement IndexMaxPQ.java by modifying MaxPQ.java as follows: Change pq[] to hold indices, add an array keys[] to hold the key values, and add an array qp[] that is the inverse of pq[] — qp[i] gives the position of i in pq[] (the index j such that pq[j] is i). Then modify the code to maintain these data structures. Use the convention that qp[i] is -1 if i is not on the queue, and include a method contains() that tests this condition. You need to modify the helper methods exch() and less() but not sink() or swim().
*/
import java.util.NoSuchElementException;
import java.util.Iterator;

public class IndexMaxPQ<Key extends Comparable<Key>> implements Iterable<Key>{
  // instance variables:
  private Key[] keys; // Key[] of key to maintain collection of java Comparable type keys
  private int[] pq; // an array that maintains the external index for any given heap position in 1-based array that represent MaxPQ
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
  public void insert(int index, Key key) {
    // Index out of bounds:
    // check if the given index is within the key array index range: [0 maxN-1]
    if(index<0 || index>=maxN) throw new IndexOutOfBoundsException();
    // Contains:    
    // check if the given index is not already associated with any keys:
    if(contains(index)) throw new IllegalArgumentException();

    // increase the number of elements in the IndexMaxPQ:
    N++;
    // update the keys array:
    keys[index]=key;
    // update the qp array: for the new key associated with the given index, assign the least priority (N):
    qp[index]=N;
    // update the pq array: for the new key associated the least level of priority (N) with the given index:
    pq[N]=index;

    // swim up the new item to its level of competence:
    swim(N); // logN
  }

  // delete the key associate with the given index from the Max oriented priority queue instance (preserve Max oriented priority queue HEAP-ORDERED condition)
  public void delete(int index) {
    // Index out of bounds:
    // check if the given index is within the key array index range: [0 maxN-1]
    if(index<0 || index>=maxN) throw new IndexOutOfBoundsException();

    // UNDERFLOW: 
    // check if the IndexMaxPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to delete maximum item in the IndexMaxPQ instance because it was empty!");

    // Contains:
    // check if the given index is not associated with any key:
    if(!contains(index)) throw new NoSuchElementException("Failed to perform delete(index) because there is no key assoicated with given index "+index+" index!");

    // exchange the tail of the heap with the key with the given index:
    int i=qp[index];
    exch(i, N);
    // decrease the number of elements in the priority queue instance:
    N--;
    // swim: promote it if possible
    swim(i);
    // sink: demote it if applicabe
    sink(i);
    
    // prevent loitering:
    keys[index]=null;
    // pq[N+1]=-1;
    qp[index]=-1;
  }

  // exctract the max Key from the Max oriented priority queue instance (preserve Max oriented priority queue HEAP-ORDERED condition)
  public Key delMax(){
    // UNDERFLOW: 
    // check if the IndexMaxPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to delete maximum item in the IndexMaxPQ instance because it was empty!");
    
    // a. take a copy of the maximum item:
    Key max=max();
    int maxIndex=maxIndex();

    /* b. remove the current max from max oriented priority:
       1. exchange it with the last node in the heap:
       2. decrease the N by one: N--
       3. sink down the new root to its rightful level of comptence
    */
    
    // 1. exchange it with the last node in the heap: exchange heap positions for a given pair of indeces
    exch(1, N);
    // 2. decrease heap size N by one: 
    N--;
    // 3. sink down the new root to its rightful level of comptence
    sink(1);

    // c. update all parallel arrays
    keys[maxIndex]=null; // prevent loitering 
    // pq[N+1]=-1;
    qp[maxIndex]=-1; // the heap position associate with the max external index to -1;

    return max;
  }

  // change the key that has been associated with the given external index "index" (preserve Max oriented priority queue HEAP-ORDERED condition)
  public void change(int index, Key key) {
    // UNDERFLOW? 
    // check if the IndexMaxPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to delete maximum item in the IndexMaxPQ instance because it was empty!");
    // Contains? 
    // check if the IndexMaxPQ instance has any key associate with the given index:
    if(!contains(index)) throw new NoSuchElementException("Failed to perform change(index,key) because IndexMaxPQ instance does not contain such a key!");

    /* 1. delete the intem at the given index from the IndexMaxPQ instace:
    delete(index);
       2. insert the new key asociated with the given index to the IndexMaxPQ instance:
    insert(index,key);
    */

    keys[index]=key;
    swim(qp[index]);  // swim up if it's possible
    sink(qp[index]);  // sink down if it's applicable
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
  public boolean contains(int index){
    // UNDERFLOW: 
    // check if the IndexMaxPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform contains(indeX) because the IndexMaxPQ instance is empty!");

    // Index out of bounds:
    // check if the given index is within the key array index range: [0 maxN-1]
    if(index<0 || index>=maxN) throw new IndexOutOfBoundsException();

    return qp[index]>0;
  }

  // check if the Max oriented priority queue instance is empty:
  public boolean isEmpty(){return N==0;}

  // returns number of elements in the Max oriented priority queue instance:
  public int size(){return N;}

  // Helper methods: sink, swim, generic comparison, exchange
  private void sink(int k){
    while(2*k<=N) {

      int j=2*k;
      // pick the best subordinate to replace the new boss
      if(j+1<=N && less(j, j+1)) j++;

      // compare the best subordinate with the new boss:
      if(!less(k, j))  break;

      // demote the new boss to level of the best suboridnate
      exch(k, j);
      k=j;
    }
  }

  // promote the new member to its rightful level of competence:
  private void swim(int k){
    while(k>1 && less(k/2, k)) {
      exch(k, k/2);
      k=k/2;
    }
  }

  // generic comparison:
  private boolean less(int i, int j) {return keys[pq[i]].compareTo(keys[pq[j]])<0;}

  // exchange method: exchange heap positions for the given pair of indeces:
  private void exch(int i, int j) {
    // exchange corresponding external indeces first for heap positions i , j :
    int temp=pq[i];
    pq[i]=pq[j];
    pq[j]=temp;

    // exchange priorities for exteranl indeces pq[i], pq[j] as well:
    qp[pq[j]]=i;
    qp[pq[i]]=j; 
  }

  // Iterable: override iterator method from java Iterable interface:
  @Override
  public Iterator<Key> iterator(){return new MaxHeapIterator();}

  // inner class that provide implementation for java Iterator interface:
  private class MaxHeapIterator implements Iterator<Key> {
    // instance variable:
    private IndexMaxPQ<Key> copy;
    // Constructor:
    public MaxHeapIterator() {
      for(int i=1; i<=N; i++)
        copy.insert(pq[i], keys[pq[i]]);
    }
    @Override
    public boolean hasNext(){return !copy.isEmpty();}
    @Override
    public Key next(){
      if(!hasNext()) throw new NoSuchElementException("Failed to perform next() operation because there has been no next element in IndexMaxPQ instance!");
      return copy.delMax();
    }
  }
}
