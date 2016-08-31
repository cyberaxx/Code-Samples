import java.util.NoSuchElementException;

import java.util.Deque;
import java.util.ArrayDeque;

/* Binary Search Array Data Structure:
   Implements ST interface using resizable parallel arrays to maintain a collection key-value pairs. */

// This class implements Symbol Table interface: ST<Key,Value> for keys with total order
public class BSA<Key extends Comparable<Key>, Value> {

  // instance variables:
  // parallel resizable arrays to maintain key-value pairs:
  private Key[] keys; // generic array of keys
  private Value[] values; // generic array values
  private int size;

  // Constructor:
  public BSA() {
    // initialize instance variables:
    size=0;
    keys=(Key[]) new Comparable[16]; // UGLY CASTING: java does not allow generic array creation
    values=(Value[]) new Object[16]; // UGLY CASTING: java does not allow generic array creation
  }

  // API: ST operations: put(key,val), get(key), delete(key), contains(key), size(), isEmpty()
  // insert a key-value pair to the ST (associative array abstraction): a[key]=value
  public void put(Key key, Value value) {
    if(key==null) throw new NullPointerException();
    if(value==null) { delete(key); return ;}
    
    // 1. check if instance arrays are full: double the size
    if(size==keys.length)  resize(2*keys.length);
    // 2. find the rightful position of the given key in a sorted array of Comparable keys (preserved the ordering of the keys array):
    int index=rank(key);
    // 3. SEARCH among KEYS in the ST and check if ST already contains such a key associated with any value:
    if(index<size && key.equals(keys[index])) {
      // if the given key exisit in the ST, then simply rewirte the new value by associating it with the same key:
      values[index]=value;
    }
    else {
      // insert the new key at the given index and shift everything from "index" to size-1 one position to the right (presever the order of keys)
      for(int i=size; i>index; i--) {
        // shift key value pairs in parallel arrays, one position to the right:
	keys[i]=keys[i-1];
	values[i]=values[i-1];
      } // this linear time insertion (cause by shitfing keys around) makes BSA not a really good implementation for ST in presence of many insertions
	
      // insert the new key value pair:
      keys[index]=key;
      values[index]=value;

      // update the size:
      size++;
    }
  }// O(n) AMORTIZED

  // Search for a value given a key (return the value associated with a given key): a[key]
  public Value get(Key key){
    if(key==null) throw new NullPointerException();
    // check if ST is empty return null
    if(isEmpty())  return null; // null is a special value (so values cannot be null)
    // Otherwise: the ST is not empty:
    // find the rightful position of the given key in SORTED array of comparable keys
    int index=rank(key);
    // compare the given key with the key at its rightful position in the keys array
    if(index<size && key.equals(keys[index])) return values[index]; // search hit
    else return null;
  } // O(logN)
  public boolean contains(Key key){return get(key)!=null;}

  // Delete a key-value pair (given the key): O(n) AMORTIZED
  public void delete(Key key){
    if(key==null) throw new NullPointerException();
    // if the ST is empty throw exception:
    if(isEmpty()) throw new NoSuchElementException("The symbol table is empty!");
    // check if there exist a key-value pair associated with the given key:
    if(!contains(key)) throw new NoSuchElementException("Symbol table does not contain the given key!");
    // Otherwise:
    // 1. find the index of the given key by searching through the SORTED array of keys:
    int index=rank(key);
    // 2. Delete the key value pair and shift all (key,value) pairs on parallel arrays that located after the given key, one position to the left (preserve the DS invariance (ascending order of keys)
    for(int i=index; i<size-1; i++) {
      keys[i]=keys[i+1];
      values[i]=values[i+1];
    } // Linear time deletion: shifting keys and values to maintain keys SORTED ordered
    // 3. prevent loitering:
    keys[size-1]=null;
    values[size-1]=null;
    // 4. update the size:
    size--;
    // 5. shrink parallel arrays if required:
    if(size==(keys.length)/4 && size>0)  resize(keys.length/2); // shrink parallel arrays to half of their previous size
  }

  // number of elements in ST: O(1)
  public int size(){return size;}
  // if ST is empty? O(1)
  public boolean isEmpty(){return size==0;}
  // Iterale object of keys
  public Iterable<Key> keys(){
    Deque<Key> queue=new ArrayDeque<Key>();
    for(int i=0; i<size; i++)
      queue.offer(keys[i]);
    return queue; // a Queue of keys in sorted order
  }

  // ORDERED OPERATIONS for Comparable keys: 
  // minimum key: O(1)
  public Key minKey(){
    // check if ST is not empty:
    if(isEmpty()) throw new NoSuchElementException("Symbol Table is empty!");
    // return the first key is the SORTED array of keys:
    return keys[0];
  }
  // maximum key: O(1)
  public Key maxKey(){
    // check if ST is not empty:
    if(isEmpty()) throw new NoSuchElementException("Symbol Table is empty!");
    // return the last key is the SORTED array of keys:
    return keys[size-1];
  }
  // delete the key-value pair associated with the smallest key:
  public void delMin() {
    // check if ST is not empty:
    if(isEmpty()) throw new NoSuchElementException("Symbol Table is empty!");
    delete(minKey());
  }
  // delete the key-value pair associated with the largest key:
  public void delMax() {
    // check if ST is not empty:
    if(isEmpty()) throw new NoSuchElementException("Symbol Table is empty!");
    delete(maxKey());
  }
  // select kth smallest Key (kth order statistics): O(1)
  public Key select(int k) {
    // check if ST is not empty:
    if(isEmpty()) throw new NoSuchElementException("Symbol Table is empty!");
    if(k<0 || k>=size) return null;
    return keys[k];
  }  
  // find number of keys less than the given key (righful index of the given key in the 0-based index array of SORTED keys):
  public int rank(Key key) {
    if(key==null) throw new NullPointerException();
    // sanity check: if the ST instance is empty return 0:
    if(isEmpty()) return 0;
    // if BSA symbol table was not empty
    int lo=0; int hi=size-1;
    while(lo<=hi) {
      // find the middle element in the keys array
      int mid=(lo+hi)/2;
      int cmp=key.compareTo(keys[mid]); // compare the given key with the key at the mid position of sorted array of keys in the symbol table
      // if the given key was greater than the key in the mid position: Search on mid's right subarray
      if(cmp>0) lo=mid+1;
      // if the given key was less than the key in the mid position: Search on mid's left subarray
      else if(cmp<0) hi=mid-1;
      // if equal
      else return mid;
    }
    /* if binary search failed (search miss):
       1. lo pointer passed the hi pointer (last iteration: search right subarray of mid): this means the given key is greater than the key at the "mid" pointer, so it has to place after it (lo+1)
       2. hi pointer pass the lo pointer  (last iteration: search left subarray of mid): this means the given key is less than the key at the "mid" pointer, so it has to place before it (lo-1)
    */
    return lo;
  }
  // find the largest key less than the given key in the Sorted array of keys
  public Key floor(Key key) {
    if(key==null) throw new NullPointerException();
    // empty ST
    if(isEmpty()) throw new NoSuchElementException();
    // fin number of keys less than the given key in the sorted array of keys
    int index=rank(key); // O(logN)

    /* There are only two possible of cases:
       1. the given key is equal to the key in the sorted array of keys at rank(key) position: key.compareTo(keys[rank(key)]) == 0
       2. the given key is greater than the key in the sorted array of keys at rank(key) position: key.compareTo(keys[rank(key)]) > 0
       NOTE:  
          key.compareTo(keys[rank(key)]) < 0:
          the given key being LESS than the key in the sorted array of keys at rank(key) IS NOT THE CASE: 
             because the rank method, returns the numbe of keys LESS than
             the given key in the sorted array of keys and if the key at the rank(key) position
             was less than the given key, the rank method would have counted it in a first place!
    */
    
    // if the given key and the key at the index position are equal
    if(index<size && key.compareTo(keys[index])==0) return keys[index];
   
    // if the rank of the given key is 0 meaning and keys are not equal: 
    // there is no key less than the given key in the sorted array of keys
    if(index==0)
      return null;

    // otherwise: if the key at the rank index is greater than the given key, the largest key less than the given key would be at rank-1 index
    return keys[index-1];
  }
  // find the predecessor of a given key: O(logN)
  public Key predecessor(Key key) {
    if(key==null) throw new NullPointerException(); // given key is null
    if(isEmpty()) throw new NoSuchElementException(); // ST is empty
    // O(logN)
    int index=rank(key); // returns the index of key in ST
    if(index==0) return null; // the first key does not have any predecessor
    return keys[index-1]; // return the key on its left (SORTED)
  }
  // find the smallest key that is greater than the give key (may or may not be in the ST)
  public Key ceiling(Key key) {
    // if the given key is null
    if(key==null) throw new NullPointerException();
    // is ST is empty:
    if(isEmpty()) throw new NoSuchElementException();

    // check the rank of the give key: number of keys in the SORTED key array that are less than the given key:
    int index=rank(key);

    // if the given key is greater than all keys in the sorted array of keys
    if(index==size) return null;
    
    // otherwise: the key at the rank position is greater than or equal to the given key
    return keys[index];
  }
  // find a successor of the key in the SORTED array of keys:
  public Key successor(Key key) {
    // if the given key is null
    if(key==null) throw new NullPointerException();
    // is ST is empty:
    if(isEmpty()) throw new NoSuchElementException();

    int index=rank(key);// find the index of the given key in the array in logN time
    if(index>=size-1) return null; 
    return keys[index+1];
  }
  // count the number of (key-value) pairs where keys are within a particular range [lo hi] inclusive
  public int count(Key lo, Key hi) {
    // extreme test cases:
    if(lo==null || hi==null) throw new NullPointerException();
    // if ST is empty
    if(isEmpty()) throw new NoSuchElementException();
    // if lo is passed hi
    if(lo.compareTo(hi)>0) return 0;
    if (contains(hi))  return rank(hi)-rank(lo)+1; // if BSA contains a key equal to hi key 
    else return rank(hi)-rank(lo); // otherwise the last key (the key at the size position is the last key GREATER than hi, so it has to be excluded
  }
  // returns Iterable collection of keys within an inclusive key range [lo hi] in sorted order:
  public Iterable<Key> keys(Key lo, Key hi) {
    // extreme test cases:
    if(lo==null || hi==null) throw new NullPointerException();
    // if ST is empty
    if(isEmpty()) throw new NoSuchElementException();

    Deque<Key> queue=new ArrayDeque<Key>();
    // if lo key is greater than the hi key return the empty queue
    if(lo.compareTo(hi)>0) return queue;
    
    if(contains(hi)) return keys(rank(lo), rank(hi), queue);
    else return keys(rank(lo), rank(hi)-1, queue); 	
  }
  // helper methods:
  // resize: grow and shrink the Symbol Table as required:
  private void resize(int capacity) {
    // Create a new pair of parallel arrays of new capacity:
    Key[] k=(Key[]) new Comparable[capacity];
    Value[] v=(Value[]) new Object[capacity];
    // copy keys and values over (for hashing this part is completely different and it requires re-hashing of all keys)
    for(int i=0; i<size; i++) {
      k[i]=keys[i];
      v[i]=values[i];
    }
    // update references to keys and values instance arrays
    keys=k;
    values=v;
  }
  private Iterable<Key> keys(int lo, int hi, Deque<Key> queue) {
    for(int i=lo; i<=hi; i++)
      queue.offer(keys[i]);
    return queue;
  }
}
