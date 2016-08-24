import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Item> implements Iterable<Item> {
  /* Stack collection of items:
  1. Adds to the top (push)
  2. Deletes from the top (pop)
  DELETES THE MOST RECENTLY ADDED ITEM TO THE COLLECTION : LIFO

  impelement stack DT, using linked list as a data structure
  to maintain collection of items
  PROS: LL is a dynamic DS -> Worst Case O(1) {constant time operation} for push and pop
  CONS: pointer arithmatic and memory usage

  Node is another member of the Stack class
  Since Node is only used in the Stack class, nesting Node class inside the 
  Stack class makes sense (logical grouping)

  As an inner class, Node, can access to all instance members (instance variables, instance methods)
  of its eclosing class (Stack), even the ones that have been declared private. 

  Node class, as an inner class, cannot have any static fields/methods, 
  because the inner class is associated with an instance.
  */

  // Stack instance Variables:
  // 1. a sentinel reference (Node) to the top of the Stack
  // 2. number of items in the Stack
  private Node top;
  private int size;

  // Stack constructor:
  // we have use the type parameter <Item> in constuctor declaration:
  public Stack() {
    top = null; // top refenece (Node) intially set to null (Stack is empty)    
    size = 0; // set the size to 0
  }

  // Inner class that defines Linked List node data type:
  private class Node {
    // Linked List is a recursive DS:
    // each node has data, and a reference to the next Node in the linked list: 
    private Item data; // item field of generic type Item
    private Node next; // a reference to the next Node in the linked list

    // Node class constructor:
    public Node(Item item) {
       this.data = item; // assign the item of generic type Item, to the data field of the node
       this.next = null; // set the next reference to null
    }
  }
 
  // API: 
  // Instance methods:
  // push method (for class client)
  // add an item to the top of the Stack
  // update size
  public void push(Item item) {
    // copy where the top reference is refering to
    Node oldTop = top;
    top = new Node(item);
    top.next = oldTop;
    // update the number of item in the stack instance
    size++;
  } // O(1): Only requires a few pointer rewirings

  // pop method (for class client)
  // remove the most recently add item to the Stack
  // remove an item from the top of the Stack
  // and return its corresponding item
  // update size
  public Item pop(){
    // check if the stack instance has any item in it:
    if(isEmpty()) throw new NoSuchElementException("Stack Underflow!");
    // copy the data that we would like to return:
    Item item = top.data;
    // update the top reference to refer to the next item in the stack (if there exists any):
    top = top.next; // if top.next was null, now top is null
    // update the number of items in the stack
    size--;
    return item;
  }  // O(1): Only requires a few pointer rewirings

  // Check if the stack instance is empty
  public boolean isEmpty() { return top==null; }
  // Returns the number of items in the stack instance
  public int size() {return size;}
  // sneek peek the item on the top of the stack:
  public Item peek() {
    if(isEmpty()) throw new NoSuchElementException("Empty Stack!");
    return top.data;
  }
  // To implement the Iterable<Item> interface we
  // have to override its iterable() method
  @Override
  public Iterator<Item> iterator(){ return new ListIterator(); }  

  // Define a concrete class ListIterator<Item> that implements the Iterator<Item> 
  // interface by overriding its corresponding methods (Item next(), boolean hasNext())

  // Since ListIterator<Item>, iterates through elements of stack instance
  // it has to be declared as an inner class (not static nested class)
  // ListIterator require to access to the Stack instance variable: "top"
  // in order to be able to move through the stack instance:
  private class ListIterator implements Iterator<Item> {
    // instance variables:
    // copy of the reference to the top reference of the stack instance:
    private Node current;
    public ListIterator() { current = top; }

    @Override 
    public boolean hasNext() { return current!=null; }

    @Override
    public Item next() {
      // if there no element left to iterate over:
      if(!hasNext()) throw new NoSuchElementException("No More items in the stack!");
      Item item = current.data;
      // update current by moving forward:
      current = current.next;
      return item;
    }
  } 
}
