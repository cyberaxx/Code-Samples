import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
  /**
   Linked List Implementation of Queue<Item>
   Queue is a Collection data type, that maintains a
   collecrton of items in form of queue.

   In Queue of item the least recently added item, would 
   get removed first.

   So, Queue adds items to its end, and removes items 
   from its beginning (FIFO) similar to real queue

   to implement Queue with singly linked list:
   we need two sentinel references to beginning
   and end of the queue to do add and remove operations
   in constant time.
  */

  private Node first; // reference to the beginning of the Q
  private Node last; // reference to the end of the Q
  private int size; // number of items in the Q
   
  // inner class Node to make linked list
  private class Node {
    // data field:
    private Item data;
    // reference field:
    private Node next;
    // Constructor:
    public Node(Item item) {
      data = item;
      next = null;
    }
  }

  // Constructor
  public Queue(){
     // initialize the Q:
     first = null;
     last = null;
     size = 0;
   }

  //API: instance methods
  public void enque(Item item) {
    // add to the end of theh Queue
    // use the sentinel pointer to the end
    
    // 1. Copy the reference the "CURRENT" end of the Q:
    Node oldLast = last;

    // 2. Create a new Node, with item as its data and assign it to last node
    // now last node is refering to the end of the queue and its last item
    last = new Node(item);

    // ETRA POINTER ARITHMETHIC:
    // check if the Q was already empty:
    // if so, the first pointer, has to point to the where the last
    // pointer is pointing to:
    if(isEmpty()) first = last;
    else oldLast.next = last; // add the new node to the end of the list

    // update the size
    size++;
  }

  public Item deque() { 
    if (isEmpty()) throw new NoSuchElementException("Queue is Empty!");
    Item item = first.data;
    // move the first pointer forward on the list
    first = first.next;
    // EXTRA POINTER ARITHMETICS:
    // check if now the Queue is empty (by checking if first==null)
    // if so, last has to be assigned null
    if(isEmpty()) last = null;

    // update size of the Q
    size--;
    return item;
  }

  public int size() { return size; }
  public boolean isEmpty() { return first==null; }

  // Queue<Item> implements java Iterable<Item> interface by 
  // overriding its iterator() method:
  @Override
  public Iterator<Item> iterator() { return new ListIterator(); }

  /*
   ListIterator is concrete helper class that implements java Iterator<Item>
   by overridinng its abstract mehtods next(), hasNext()
   ListIterator, as an inner class can accees all instance fields
   of its enclosing class (Queue). We require this feature classes to iterate over
   items of Queue, usin Queue.first sentinel reference
  */  
  private class ListIterator implements Iterator<Item> {
    private Node current; // pointer to a current element in Q
    public ListIterator() { current = first; } // first is point to the first Node is there exist any
    @Override
    public boolean hasNext() { return current!=null;}
    @Override
    public Item next() {
      if(!hasNext()) throw new NoSuchElementException("No more elements in the Queue!");
      // take a copy of the data of the Node that the current reference is pointing to:
      Item item = current.data;
      // advance the current reference to the next node:
      current = current.next;
      return item;
    }
  }
   
}
