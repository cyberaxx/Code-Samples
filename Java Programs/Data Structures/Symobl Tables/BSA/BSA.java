import java.util.NoSuchElementException;

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
    if(value==null) delete(key);
    else {
      // 1. check if instance arrays are full: double the size
      if(size==keys.length)  resize(2*keys.length);
      // 2. find the rightful position of the given key in a sorted array of Comparable keys (preserved the ordering of the keys array):
      int index=rank(key);
      // 3. SEARCH among KEYS in the ST and check if ST already contains such a key associated with any value:
      if(index>=0 && key.equals(keys[index]))
        // if the given key exisit in the ST, then simply rewirte the new value by associating it with the same key:
        values[index]=value;
      else {
        // insert the new key at the given index and shift everything from "index" to size-1 one position to the right (presever the order of keys)
        for(int i=size; i<=index; i--) {
          // shift key value pairs in parallel arrays, one position to the right:
	  keys[i]=keys[i-1];
	  values[i]=values[i-1];
        } // this linear time insertion (cause by shitfing keys around) makes BSA not a really good implementation for ST in presence of many insertions
	
        // insert the new key value pair:
        keys[index]=key;
        values[index]=value;
      }
      // 4. update the size:
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
    if(index>=0 && key.equals(keys[index])) return values[index]; // search hit
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
    for(int i=index; i<size; i++) {
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
  public Iterable<Key> keys(){return null;}

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
    if(k<0 || k>=size) throw new IndexOutOfBoundsException();
    return keys[k];
  }
  
  // find number of keys less than the given key (righful index of the given key in the 0-based index array of SORTED keys):
  public int rank(Key key) {
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
}
