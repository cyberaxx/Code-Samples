/* This class implements the singly liked list 
data structure: with only one sentinel node
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SList<Item> implements Iterable<Item> {
  // Instance Fields:
  private Node first; 
  private int size;

  // Constructor
  public SList(){
    first = null;
    size = 0;
  }
  
  // Inner class Node:
  private class Node {
    // Fields:
    // only can have instance variable (no static memeber), because Node is associated with 
    // instances of the SList class (Which is a Collection type)
    private Item data; // the data part of the Node of generic type Item
    private Node next; // a reference to the Next Node
   
    // initialize a node by setting it's value and next reference
    public Node(Item item) {
      this.data = item;
      this.next=null;
    } 
  }

  // API: Instance methods:

  public void addFront(Item item) {// O(1) constant time operation 
    first = addFront(first, item);
  }

  public void addBack(Item item){ //O(n): Linear time operation
    // if SList is empty addFront:
    if(isEmpty()) addFront(item); // no reason to call getLastNode() and iterate over the list
    else {
      Node last = getLastNode();
      last.next = new Node(item);  
      size++;
    }
  }

  public void addAt(int index, Item item){// O(n) Linear time operation
    // check if index is in legal range:
    if(index<0 || index>size) throw new IndexOutOfBoundsException("Faild to insert into " + index +" index, because it was way out of bounds!");
    else if(index==0) addFront(item);
    else if(index==size-1) addBack(item);
    else {
      Node temp = first;
      for(int i=0; i<index-1; i++) temp=temp.next; // goto the the node at index i-1
      // add the new node at the front of the node at index i, after node i-1:
      Node oldTemp = temp;
      temp = new Node(item);
      temp.next = oldTemp.next;
      oldTemp.next = temp;
      size++;
    } 
  }

  public Item removeFront(){// O(1) constant time operation
    // Check if SList is empty already or not:
    if(isEmpty()) throw new NoSuchElementException("SList is already empty!");
    Item item = first.data;
    first = first.next;
    size--;
    return item;
  }

  // remove a given item from the SList
  public boolean remove(Item item) {
    // first check if the item is in the SList:
    int index = indexOf(item);
    if(index<0) return false; // the item does NOT exist in the SList (so the structure does not change)
    if(index==0) {
      removeFront();
      return true;
    }
    if(index==size-1) {
      removeBack();
      return true;
    }
    // get the previous item at index-1:
    Node prev = nodeAt(first, index-1);
    Node current = nodeAt(first, index);
    // delete the current:
    prev.next = current.next; // no pointer to current (ready for garbage collection)
    size--; // update the size
    return true;
  }

  public boolean contains(Item item) {// O(n) Linear time operation
    // if list is empty:
    if(isEmpty()) return false;
    for(Node temp=first; temp!=null; temp=temp.next)
      if(temp.data.equals(item)) return true;
    return false;
  }

  public int indexOf(Item item) {// O(n) Linear time operation
    return indexOf(first, item);
  }
 
  public Item get(int index) {// O(n) Linear time operation
    // check if index is not out of bounds:
    if(index<0 || index>=size) throw new IndexOutOfBoundsException("Failed to perform getAt, because the "+index+" index, is out of [0-"+(size-1)+"] range!");
    if(index==0) return getFront();
    if(index==size-1) return getBack();
    Node temp = nodeAt(first, index); // advance to the node at the given index
    return temp.data;
  }  

  public Item getFront(){ return first.data; } // O(1) constant time operation
  public Item getBack() { return getLastNode().data; } // O(n) Linear time operation

  public boolean isEmpty(){ return first==null;}
  public int size(){return size;}
 
  // helper methods
  private Node addFront(Node temp, Item item) {// O(1) constant time operation 
    Node oldFirst = temp;
    temp = new Node(item);
    temp.next = oldFirst;
    size++; // update the size
    return temp;
  }

  private Node getLastNode() {// O(n) Linear time operation
    Node temp;
    for(temp=first; temp.next!=null; temp=temp.next); // advance the pointer to the next Node
    return temp;
  }

  private Node nodeAt(Node temp, int index) {// O(n) Linear time operation
    // check if index is not out of bounds:
    if(index<0 || index>=size) throw new IndexOutOfBoundsException("Failed to perform nodeAt, because the "+index+" index, is out of [0-"+(size-1)+"] range!");
    if(index==0) return first;
    for(int i=0; i<index; i++) // adavnce the temp reference one Node at a time
      temp = temp.next; 
    return temp;
  }

  private int indexOf(Node temp, Item item) {// O(n) Linear time operation
    if(!contains(item)) return -1; // if the SList does not contain the item
    int index=0;
    while(temp != null) {
      if(temp.data.equals(item)) return index;
      temp = temp.next;
      index++;
    }
    return -1;
  }

  public Item removeBack(){return null;}// impossible with SList to remove from back

  // SList implements java Iterable<Item> interface by 
  // overriding its abstract iterator() method:
  @Override
  public Iterator<Item> iterator() {return new ListIterator();}
  // Inner class ListIterator extends the java Iterator public interface
  // for the gerenric type <Item> by overridinf its abstract methods, next()
  // and hasNext()
  private class ListIterator implements Iterator<Item> {
    // Instance fields:
    private Node current;
    public ListIterator() {current=first;}
    @Override
    public boolean hasNext() {return current!=null;}
    @Override
    public Item next() {
      if(!hasNext()) throw new NoSuchElementException("List is empty already!");
      Item item = current.data;
      // advafnce the current pointer:
      current = current.next;
      return item;
    }
  }
}
