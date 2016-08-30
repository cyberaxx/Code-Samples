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
    // 3. check if ST already contains such a key associated with any value
    if(contains(key))
      // simply rewirte the new value at the given index:
      values[index]=value;
    else {
      // insert the new key at the given index and shift everything from "index" to size-1 one position to the right (presever the order of keys)
      for(int i=size; i<=index; i++) {
        // shift key value pairs in parallel arrays, one position to the right:
	keys[i]=keys[i-1];
	values[i]=values[i-1];
      } // this linear time insertion (cause by shitfing keys around) makes BSA not a really good implementation for ST in presence of many insertions
	
      // insert the new key value pair:
      keys[index]=key;
      values[index]=value;
    }
  }

  // Search for a value given a key (return the value associated with a given key): a[key]
  public Value get(Key key){return null;}
  public boolean contains(Key key){return get(key)!=null;}
  // Delete a key-value pair (given the key)
  public void delete(Key key){}

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
    while(lo<hi) {
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
