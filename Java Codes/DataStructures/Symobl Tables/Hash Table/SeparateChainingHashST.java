public class SeparateChainingHashST implements Iteable<Key> {
  // Instance variable:
  private int tableSize;
  private Node[] table;

  public SeparateChainingHashST() {
    // set table size m initially to a prime number like 97:
    tableSize=97;
    // instantiate an array of linked list:
    // array represent m buckets and each bucket is a reference to a linked list:
    table = new Node[tableSize];
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
  public void put(Key key, Value val); // add item to the HashST
  public boolean contains(Key key);
}
