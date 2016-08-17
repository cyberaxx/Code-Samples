import java.util.Iterator;

public class LinearProbingHashST<Key, Value> implements Iterable<Key> {
  // instance variable:
  private int n; // keeps track of number of items in the the HST
  private int m; // initial number of hash buckets
  // parallel array to maintain <key, value> pairs:
  private Key[] keys; // generic array of generic type Key
  private Value[] values; // generic array of generic type Value

  // Constructor:
  public LinearProbingHashST() {
    m=97; // intial number of buckets
    n=0; // no item in the HST
    // initialize parallel arrays:
    keys = (Key[]) new Object[m]; // UGLY CASTING: Java does NOT allow generic array creation
    values = (Value[]) new Object[m]; // UGLY CASTING: Java does NOT allow generic array creation
  }

  // API: insert, search (get, contains), remove (tricky)
  public void put(Key key, Value val) {
    // check if resizing is required: if n==(1/2)*m then resize
    if(n==m/2)  resize(2*m); // double the capacity to maintain the HST half empty all the time
    
    // 1. compute the hash index of the given key:
    int hashIndex=hash(key);

    /* 2. look up keys[hashIndex]:
          case 1: keys[hashIndex] is not null: compare the key at keys[hashIndex] with the given key
	    1a. if equal: update the value at values[hashIndex] with the new value
	    1b. if not equal: look up the next entry in the array and search through the array in the circular fashion until hitting null entry:
          case 2: if null, put keys[hashIndex]=key and values[hashIndex]=val
    */
    int i;
    for(i=hashIndex; keys[i]!=null; i= (i+1)%m) {
      if(keys[hashIndex].equals(key)) {
        // update the value in the parallel array:
        values[hashIndex]=val;
        return ; // to avoid updatinn n
      }
    }

    // insert the new key value pair to the parallel arrays
    keys[i]=key;
    values[i]=val;
    n++; // update the number of elements in the HST
  }

  public Value get(Key key) {
    int hashIndex=hash(key);
    // Search through the keys array starting from the hashIndex and move forward in a circular fashion unless we hit an entry with equal key or hitting null:
    for(int i=hashIndex; keys[i]!=null; i++) {
      if(keys[hashIndex].equals(key))  return values[i];
    }
    // search miss (we hit the null entry)
    return null;
  }
  public boolean contains(Key key) {return get(key)!=null;}
  public Value remove(Key key){return null;}

  /* helper methods: 
     1. hash(Key key): reduce a given key to a 32 bit int, and use modular fuction (%) to return an integer within the range of Hash Table indeces
     2. resize(int capacity): provides load balancing by expand and shrink hash table (parallel arrays)
        NOTE: after each resizing, since M is going change, we have to rehash, all the keys in the table using the hash method with new M
        that's why insertion is going to be O(1) AMORTIZED
  */

  // use the hashCode() method developed by Key's class, and modular % function, returns a positive interger within [0 m-1] range
  private int hash(Key key) {
    // compute the hashIndex of the given key:
    int hashIndex= ( key.hashCode() & 0x7ffffff ) % m;
    return hashIndex;
  }

  private void resize(int capacity) {}

  /*
    in order to implement java Iterable interface
    we must override its abstract method iterator:
  */
  @Override
  public Iterator<Key> iterator() {return null;}

}
