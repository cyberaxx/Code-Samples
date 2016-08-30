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
    // 1. check if instance arrays are full: double the size
    if(size==keys.length)  resize(2*keys.length);
    // 2. find the rightful position of the given key in a sorted array of Comparable keys (preserved the ordering of the keys array):
    int index=rank(key);
    // 3. SEARCH among KEYS in the ST and check if ST already contains such a key associated with any value:
    if(contains(key))
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

  // Search for a value given a key (return the value associated with a given key): a[key]
  public Value get(Key key){
    // check if ST is empty return null
    if(isEmpty())  return null; // null is a special value (so values cannot be null)
    // Otherwise: the ST is not empty:
    // 1. Search the sorted array of keys to find the given key using BINARY SEARCH method:
    int index=binarySearch(key);
    if(index<0) return null; // search miss
    // Otherwise return the value associated with the given index:
    return values[index];
  }
  public boolean contains(Key key){return get(key)!=null;}

  // Delete a key-value pair (given the key)
  public void delete(Key key){
    // if the ST is empty throw exception:
    if(isEmpty()) throw new NoSuchElementException("The symbol table is empty!");
    // check if there exist a key-value pair associated with the given key:
    if(!contains(key)) throw new NoSuchElementException("Symbol table does not contain the given key!");
    // Otherwise:
    // 1. find the index of the given key by searching through the SORTED array of keys:
    int index=binarySearch(key);
    // 2. Delete the key value pair and shift all (key,value) pairs on parallel arrays that located after the given key, one position to the left (preserve the DS invariance (ascending order of keys)
    for(int i=index; i<size; i++) {
      keys[i]=keys[i+1];
      values[i]=values[i+1];
    }
    // 3. prevent loitering:
    keys[size-1]=null;
    values[size-1]=null;
    // 4. update the size:
    size--;
    // 5. shrink parallel arrays if required:
    if(size==(keys.length)/4 && size>0)  resize(keys.length/2); // shrink parallel arrays to half of their previous size
  }

  // number of elements in ST:
  public int size(){return size;}
  // if ST is empty?
  public boolean isEmpty(){return size==0;}
  // Iterale object of keys
  public Iterable<Key> keys(){return null;}

  // Ordered operation for Comparable keys: min, max, delMin, delMax, floor(key), ceiling(key), successor(key), predeccessor(key), select(k), rank(key), keys(),  
  
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

  // implement iterative binary search: return -1 in case of search miss
  private int binarySearch(Key key) {
    // array boundaries:
    int lo=0; int hi=size-1;
    while(lo<=hi) {
      // find the middle of array:
      int mid=(hi+lo)/2;
      // compare the given key with the key at the mid position in the key array using compareTo method:
      int cmp=key.compareTo(keys[mid]);
      // a. if the given key is GREATER than the key at the middle of the array (assuming array is sorted in ascending order): search on RIGTH SUBARRAY
      if(cmp>0) 
	// modify array boundaries for search:
        lo=mid+1; // hi stays the same: search from mid+1 to hi portion of sorted key array
      // b. if the given key is LESS than the key at the middle of the array (assuming array is sorted in ascending order): search on LEFT SUBARRAY
      else if(cmp<0) 
	// modify array boundaries for search:
        hi=mid-1; // lo stays the same: search from lo to mid-1 portion of sorted key array	
      else // c. if key equal the mid element at the keys array:
	return mid; // return the mid index
    }
    // if the hi pointer passed the lo (hi<lo) pointer and the given key was not found in the array:
    return -1;
  }
}
