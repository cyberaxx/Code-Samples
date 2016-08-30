/* Binary Search Array Data Structure - Implements ST interface using resizable parallel arrays to maintain a collection key-value pairs. */

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
  public void put(Key key, Value value) {}
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
