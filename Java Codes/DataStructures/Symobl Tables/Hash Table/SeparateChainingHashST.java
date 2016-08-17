import java.util.Iterator;

public class SeparateChainingHashST<Key, Value> implements Iterable<Key> {
  // Instance variable:
  private int tableSize;
  private Node[] table;

  public SeparateChainingHashST() {
    // set table size m initially to a prime number like 97:
    tableSize=97;
    // instantiate an array of linked list:
    // array represent m buckets and each bucket is a reference to a linked list:
    table = (Node[]) new Object[tableSize]; // UGLY cast: Java does NOT allow GENERIC array creation
    for(int i=0; i<tableSize; i++) {
      // each hash buck is a reference to a Linked List:
      table[i]=new Node();
    }
  }

  // Linked List data definition:
  private class Node {
    private Object key;
    private Object val;
    private Node next;
  }

  // API
  // insert item to the HashST
  public void put(Key key, Value val){}
  // search HashST for a given key
  public boolean contains(Key key){return false;}
  // search HashST for a given key, return its value
  public Value get(Key key) {return null;}
  // remove an item from HashST given the key
  public Value remove(Key key) {return null;}

  /* in order to implement java iterable interface, we have override
     its abstract method: iterator:
  */
   @Override
   public Iterator<Key> iterator() {return null;}
}
