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

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.MaxPQ;

public class MedianPQ<Key extends Comparable<Key>> implements Iterable<Key> {
  // instance field of MedianPQ:
  private MinPQ<Key> minPQ; // collection of keys GREATER than the median
  private MaxPQ<Key> maxPQ; // collection of keys LESS than the median
  private Key median;

  // Constructor
  public MedianPQ() {
    minPQ=new MinPQ<Key>(); // empty min oriented pq
    maxPQ=new MaxPQ<Key>(); // empty max oriented pq
    median=null;
  }

  // API:
  public void insert(Key key){
    // sanity check the key:
    if(key==null) throw new NullPointerException();
    // if MedianPQ is empty
    if(isEmpty()) {
      // set the median:
      median=key;
      // insert the key to the max oriented heap (arbitary):
      maxPQ.insert(key);
      // the extracted max is the LARGEST key in Median PQ which is less than previous median key
    }
    // if MedianPQ is not empty
    else {
      // compare the new key with median:
      int cmp=key.compareTo(median);
      // use a max-oriented heap for keys less than the key of v:
      // MaxPQ would maintain the collection keys LESS than v 
      if(cmp<0) {
	// To insert, add the new key into the appropriate heap:
	maxPQ.insert(key);
	// replace median with the key extracted from that heap:
        // filter out MAX elements from collection of keys LESS than the median key
        maxExch();
      }
      // use a min-oriented heap for keys greater than the key of v: filter out min elements from the 
      // collection of items greater than median
      else {
	// To insert, add the new key into the appropriate heap:
	minPQ.insert(key);
	// replace median with the key extracted from that heap:
        // filter out MIN elements from collection of keys GREATER than the median key
        minExch();
	// the extract min is the SMALLEST key in MedianPQ which is greater than previous median
      }
    }
  }

  // delete the median
  public Key delMedian() {
    if(isEmpty()) throw new NoSuchElementException();
    Key key=median;
    // take the max key from collection of keys less than median to replace it with
    median=maxPQ.delMax();
    return key;
  }

  // return median O(1)
  public Key median() {
    if(isEmpty()) throw new NoSuchElementException();
    return median;
  }
  // the MedianPQ is empty if both its pqs were empty:
  public boolean isEmpty(){return minPQ.isEmpty()&&maxPQ.isEmpty();}
  // number of elements is the sum of number elements in both pqs 
  public int size(){return minPQ.size()+maxPQ.size();}


  // Helper methods:
  private void maxExch() {
    Key temp=maxPQ.delMax();
    maxPQ.insert(median);
    median=temp;
  }

  private void minExch() {
    Key temp=minPQ.delMin();
    minPQ.insert(median);
    median=temp;
  }

  @Override
  public Iterator<Key> iterator(){return null;}
}
