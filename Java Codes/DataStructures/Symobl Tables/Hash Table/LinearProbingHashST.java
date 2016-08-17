import java.util.Iterator;

public class LinearProbingHashST<Key, Value> implements Iterable<Key> {
  // instance variable:
  private int size; // keeps track of number of items in the the HST
  private int m; // initial number of hash buckets
  // parallel array to maintain <key, value> pairs:
  private Key[] keys; // generic array of generic type Key
  private Value[] values; // generic array of generic type Value

  // Constructor:
  public LinearProbingHashST() {
    // initialize the size:
    m=97; // intial number of buckets
    size=0; // no item in the HST
    // initialize parallel arrays:
    keys = (Key[]) new Object[m]; // UGLY CASTING: Java does NOT allow generic array creation
    values = (Value[]) new Object[m]; // UGLY CASTING: Java does NOT allow generic array creation
  }

  // API: insert, search (get, contains), remove (tricky)
  public void put(Key key, Value val) {}
  public Value get(Key key) {return null;}
  public boolean contains(Key key) {return false;}
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
