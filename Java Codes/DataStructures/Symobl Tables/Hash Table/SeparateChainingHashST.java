import java.util.Iterator;

public class SeparateChainingHashST<Key, Value> implements Iterable<Key> {
  // Instance variable:
  private int m;
  private Node[] table;
  private int size;

  public SeparateChainingHashST() {
    // set table size m initially to a prime number like 97:
    m=97;
    size=0; // keeps track of number of keys added to the table
    // instantiate an array of linked list:
    // array represent m buckets and each bucket is a reference to a linked list:
    table = (Node[]) new Object[m]; // UGLY cast: Java does NOT allow GENERIC array creation
    for(int i=0; i<m; i++) {
      // each hash buck is a reference to a Linked List:
      table[i]=new Node();
    }
  }

  // Linked List data definition:
  private class Node {
    private Object key;
    private Object value;
    private Node next;
  }

  // Hash function to reduce key into a 32-bits integer (that can represent -2^31 to 2^31 - 1) value and map that integer into 0 to m-1 (HashST index)
  private int hash(Key key) {
    // it calls hashCode() method (implemented by Key class) on instance of the Key data type -> reduces Key to 32-bits integer:
    // 0 out the sign bit to make the integer always positive
    return (key.hashCode() & 0x7ffffff) % m; // return an interger within [0 m-1] range 
  }

  // API
  // insert item to the HashST
  public void put(Key key, Value val){
    // 1. Compute the corresponding integer index of the given key (of type Key) in the hash table (using the Key hashCode and modular function)
    int hashIndex=hash(key);

    // 2. Go to the buckt with index "hashIndex" and traverse its corresponding LL:
    for(Node x=table[hashIndex]; x!=null; x=x.next) {
      // case 1: if there exist a node in LL with a key equal to the given key, update its value
      if(x.key.equals(key)) {
        x.value=val;
        return ; // to avoid updating size (size++)
      }
    }

    // case 2: if it the key was not in the chain associated with key's hashIndex bucket: add key to the front of its corresponding bucket:
    Node oldFirst=table[hashIndex];
    table[hashIndex]=new Node();
    table[hashIndex].key=key;
    table[hashIndex].value=val;
    table[hashIndex].next=oldFirst;
    size++; // update the number of element in the table
  }

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
