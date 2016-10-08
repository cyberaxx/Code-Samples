import java.util.Iterator;

public class SeparateChainingHashST<Key, Value> implements Iterable<Key> {
  // Instance variables:
  private int m; // number of buckets in the table
  private Node[] table; // data structure to maintain ST items 
  private int size; // number of items currently in the SeparateChainingHashST data type

  public SeparateChainingHashST() {
    // set table size m initially to a prime number like 97:
    m=97;
    size=0; // keeps track of number of keys added to the table
    // instantiate an array of linked list:
    // array represent m buckets and each bucket is a reference to a linked list:
    table=new Node[m];
    for(int i=0; i<m; i++) {
      // each hash buck is a reference to a Linked List:
      table[i]=new Node();
    }
  }

  // Linked List data definition:
  private static class Node {
    private Object key;
    private Object value;
    private Node next;
  
    // Constructor:
    public Node(){}
    public Node(Object key, Object value){
      this.key=key;
      this.value=value;
    }
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
    /* Under Uniform Hashing assumption (where keys are distribute uniformly random), the expected length of each chain (LL) associated to each 
       bucket is E(L)=N/M (where N denotes the number of keys and M denotes the number buckets): 1/M is the probablity to get hashed to each of M buckets
       RT of search, and insert consequently governs by the length of the chain, and so to have a average constant time insert and search we need to ke the 
       chains short, by choosing M proportional N such that N=O(M) -> O(L) = O(1)
    */

    // EXPANSION:
    // check if the number buckets (m) is with constant factor of number keys (size):
    // other wise double the size of the table
    if(size>= 5*m) resize(2*m);


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
    table[hashIndex]=addFirst(table[hashIndex], key, val);
    size++; // update the number of element in the table
  }

  // search HashST for a given key (based on get method)
  public boolean contains(Key key){return get(key)!=null;}

  // search HashST for a given key, return its value
  public Value get(Key key) {
    // 1. compute its hash value: its corresponding index in the HST:
    int hashIndex=hash(key);
    // 2. go to the bucket with index "hashIndex", and traverse its corresponding chain:
    for(Node x=table[hashIndex]; x!=null; x=x.next) {
      // case 1: if we foud a node with a key equal to the key in the query:
      if(x.key.equals(key)) {
        // return its corresponding value
        return (Value) x.value; // UGLY CASTING (because of NODE definition
      }
    }
    // search miss: there were no items with key equal to the key in the query in the chain associated to the hashIndex bucket in the HST
    return null;
  }

  // remove an item from HashST given the key
  public Value remove(Key key) {return null;}

  private void resize(int capacity) {}
  private Node addFirst(Node head, Key key, Value value) {
    // create a new node:
    Node newNode=new Node(key, value);
    // add the new node to the head of the list
    newNode.next=head;
    head.next=newNode;
    return head;
  }

  /* in order to implement java iterable interface, we have override
     its abstract method: iterator:
  */
   @Override
   public Iterator<Key> iterator() {return null;}
}
