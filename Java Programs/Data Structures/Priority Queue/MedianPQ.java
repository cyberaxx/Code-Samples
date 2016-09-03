/*
Dynamic-median finding:
Design a data type that supports insert in logarithmic time, find the median in constant time, and remove the median in logarithmic time.

http://algs4.cs.princeton.edu/24pq/
*/

/*
  MedianPQ data type is a generalization of collection data type where keys are generic comparable types. It uses the total ordering of keys
  in order to organize keys in the data structure such that the median of the keys can be accessed at constant time O(1) (generalization of Queue data type)
  
  Collection data type to maintain keys of MedianPQ data structure:
  To implement this data structure, instead of implicit array implementation of binary heap (that have been used in MinPQ and MaxPQ) we need a more
  sophistacate collection data type.
  We need to use one min oriented and one max oriented priority queue to maintain keys of MedianPQ:
	1. Maintain a collection of M largest keys LESS than the median key: MinPQ (efficiently filtering out (extract) MIN key repeatedly)
	2. Maintain a collection of M smallest keys GREATER than the medain key: MaxPQ (efficiently filtering out (extract) MAX key repeatedly)
	3. Data Structue invariance:
	     i. Median is always LESS than the head of MinPQ of keys with greater values
             ii. Median is always GREATER than the head of MaxPQ of keys with less values

        The invariance has to preserved after any sequence of insertion/deletion (structural changes to the data structure)
	The head of MinPQ gKeys and MaxPQ lKeys are two candidates to be the next MEDIAN after any insertion and deletion to preseve the invariance of DS
*/

import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.MaxPQ;

public class MedianPQ<Key extends Comparable<Key>> {

  // instance variables:
  private MinPQ<Key> gKeys; // min oriented priority queue of keys GREATER than the median
  private MaxPQ<Key> lKeys; // max oriented priority queue of keys LESS than the median
  private Key median;

  // constructor:
  public MedianPQ() {
    // initialize instance variables:
    gKeys=new MinPQ<Key>(); // empty min oriented priority queue for generic comparable type Key
    lKeys=new MaxPQ<Key>(); // empty collection of key in a form of max oriented priority queue
    median=null;
  }

  // API: instance methods of MedianPQ data type
  // is MedianPQ instance empty
  public boolean isEmpty() {return median==null;}
  // number of keys in MedianPQ instance
  public int size() {
    if(isEmpty()) return 0; // empty MedianPQ
    // sum of number of items in Min and Max pqs and the median key
    return gKeys.size()+lKeys.size()+1;
  }
  // return the median in O(1)
  public Key median(){
    if(isEmpty()) throw new NoSuchElementException();
    return median;
  }

  // Insertion and deletion: 
  // PRESERVE MedianPQ INVARIANCE: median must be within [MaxPQ.head()  MinPQ.head()] range  
  public void insert(Key key) {
    // sanity check the key
    if(key==null) throw new NullPointerException();

    // if the median priority queue is empty:
    if(isEmpty()) {median=key; return ;}// set the first key as the median
    
    // Otherwise:
    // 1. Compare the new key with the current median:
    int cmp=key.compareTo(median);

    /* 2. if the new key is GREATER than the median:
          i. median has to get UPDATED.
          ii. new median has to be GREATER than the previous one

	  to accomplish this:
	a. add the new key to the MinPQ of keys greater than the median
	b. delMin() extract the min key from the collection of keys GREATER than median
	c. replace that key with the old median
	d. add the old median to the MaxPQ of keys less than the median
    */

    Key temp;
    if(cmp>0) {
      // a. insert the new key to the MinPQ of keys GREATER than median
      gKeys.insert(key);
      // b. extract the Min key from the MinPQ of keys GREATER than median
      // c. replace the extracted key with the median
      temp=median;
      median=gKeys.delMin();
      // d. insert the old median to the MaxPQ of keys less than the median
      lKeys.insert(temp);
    }

    /* 3. if the new key is LESS than the median:
          i. median has to get UPDATED.
          ii. new median has to be LESS than the previous one

	  to accomplish this:
	a. add the new key to the collection of keys less than the median
	b. extract the max key from the collection of keys less than the median
	c. replace the median with the extracted key
	d. add the old median to the collection of keys greater than the median
    */
    if(cmp<0){
      // a. add the new key to MaxPQ of keys less than the median
      lKeys.insert(key);
      // b. remve the max key from the MaxPQ of keys less than the median
      // c. replace the median with the extracted key
      temp=median;
      median=lKeys.delMax();
      // d. add the old median to the collection of keys greater than the median
      gKeys.insert(temp); 
    }

    /* 4. if the new key is EQUAL to the median: Do NOT support duplicate keys
          i. median stays the same.
          ii. Do nothing
    */

  }

  // test client:
  public static void main(String[] args) {
    MedianPQ<Integer> pq=new MedianPQ<Integer>();
    for(int i=0; i<5; i++) pq.insert(i);
    System.out.println("Median: " + pq.median());
   System.out.println("Size: " + pq.size());
   System.out.println("Empty? " + pq.isEmpty());
    System.out.println();
  }


}
