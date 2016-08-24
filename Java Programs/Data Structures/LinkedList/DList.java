/* this class provide an implementation
* for doubly-linked list with two sentinel nodes
* java implementation is Class LinkedList<E> that
* List, Collection, Iterable interfaces.
*/

import java.util.NoSuchElementException;
import java.util.Iterator;

public class DList<Item> implements Iterable<Item> {
  private Node first;
  private Node last;
  private int size;
  
  public DList() {
    first = new Node ();
    last = new Node ();
    first.prev=null;
    first.next=last;
    last.prev=first;
    last.next=null;
    size = 0;
  }
  
  // Node is a private inner class of DList
  private class Node {
    private Node prev;
    private Node next;
    private Item data;
  }

  // API:
  // insertion methods:
  // O(1) Operation: to add an item to the DList
  public void addFirst(Item item) {
    Node newNode = new Node();
    newNode.data = item;
    // if the DList was already empty:
    if(isEmpty()) {
      // set the next pointer of newNode to last
      newNode.next=last;
      last.prev = newNode;
      newNode.prev=first;
      first.next = newNode;
    }
    // no need to touch the last sentinel node, unless the list was already empty:
    else {
      // copy the old actual node that frist sentinel was point to
      Node oldFirst = first.next;
      first.next = newNode;
      newNode.next = oldFirst;
      newNode.prev=first;
      oldFirst.prev = newNode;
    }
    // update size
    size++;
  }

  // O(1) Operation: to add an item to the DList
  public void addLast(Item item) {
     if(isEmpty()) addFirst(item); // if DList is empty, call addFront(Item item)
     else {
       // create a new Node: it's next pointer points to the last sentinel node
       Node newNode = new Node();
       newNode.data = item;
       newNode.next = last;
       // copy the node that last was pointing to previously
       Node oldLast = last.prev;
       newNode.prev = oldLast;
       oldLast.next = newNode;
       // take care of the last sentinel node (no need to take of first):
       last.prev = newNode; 			
       // update size:
       size++;
     }
  }

  // O(n)
  public void addAt(Item item, int index) {
    // check if index is within a valid range [0,size]
    if(index<0 || index>size) throw new IndexOutOfBoundsException("Failed to addAt " + index + " index because it is way out of bounds!");
    if(index==0) addFirst(item);
    else if(index==size) addLast(item);

    else {
      Node newNode=new Node();
      newNode.data=item;      

      Node temp = getNodeAt(index);
      Node prevNode = temp.prev;

      newNode.next = temp;
      temp.prev = newNode;
      newNode.prev = prevNode;
      prevNode.next = newNode;
      size++;
    }
  }

  // deletion methods:
  // O(1) Operation: to remove an item from the DList
  public Item removeFirst() {
    //check if the DList is empty or not:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform removeFirst() bacause the DList instance is empty!");
    Node oldFirst = first.next;
    Item item = oldFirst.data; 

    // pointer re-wiring:
    Node newFirst = oldFirst.next; 
    first.next = newFirst;
    newFirst.prev = first;
    oldFirst.next = null;
    oldFirst.prev = null;

    // check if last pointer has to be updated or not (paying the peiper):
    if(isEmpty()) {
      last.prev = first; // update the last sentinel node to point to the first sentinel node
    }

    // update the size:    
    size--;

    return item;
  }

  // O(1) Operation: to remove an item from the DList
  public Item removeLast() {
    // check if the DList is already empty or not:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform removeLast() because the DList instance is empty!");
    if(size==1) return removeFirst(); // if there is only one element in the DList instance, call removeFirst

    // O.W.
    Node oldLast = last.prev;
    Item item = oldLast.data;
 
    // pointer re-wrining:
    Node newLast = oldLast.prev;
    last.prev = newLast;
    newLast.next = last;
    oldLast.next = null;
    oldLast.prev = null;    

    // update size feild:
    size--;
    return item;
  }
  
  // O(n) Linear time operation to remove an item from a specific index
  public Item removeAt(int index) {
    // check if DList is empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform removeAt operation because the DList instance is empty!");
    // check if index is within the valid range
    if (index<0 || index>size-1) throw new IndexOutOfBoundsException("Failed to removeAt because "+index+" index is out of bounds!");
    if(index==0) return removeFirst();
    if(index==size-1) return removeLast();

    Node current = getNodeAt(index);
    Item item = current.data;

    Node prevNode = current.prev;
    Node nextNode = current.next;

    prevNode.next = nextNode;
    nextNode.prev = prevNode;
    current.next = null;
    current.prev = null;
    
    size--;
    return item;
  }

  // O(n)
  public boolean remove(Item item) {
    // check if DList is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform remove operation because the DList instance is empty!");
    // check if the DList instance contains the given item:
    int index = indexOf(item);
    if(index<0) return false;
    if(removeAt(index) == null) throw new RuntimeException("Failed to removeAt because of null value!");
    return true;
  }

  // get methods:
  // O(1)
  public Item getFirst() {
    if(isEmpty()) throw new NoSuchElementException("Failed to perform getFirst because the DList instance is empty!");
    return first.next.data;
  }

  // O(1)
  public Item getLast() {
    // check if the DList instance is empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform getLast because the DList instance is empty!");
    return last.prev.data;
  }

  // O(n)
  public Item getAt(int index) {
    // check if the index is within valid range of [0,size):
    if(index<0 || index>=size) throw new IndexOutOfBoundsException("Failed to perform getAt method because "+index+" is way out of bounds!");
    if(index==0) return getFirst();
    if(index==size-1) return getLast();
    // get to the Node at the index "index":
    Node current = getNodeAt(index);
    return current.data;
  }

  // set method: update the value of the
  // item at the given index:
  // O(1) operation to update the value of the first node in the list:
  public Item setFront(Item item) {
    // check if list is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to setFront, because DList is empty!");
    // update the data of the first actual node (first itself is a sentinel node)
    Item oldValue = first.next.data;
    first.next.data = item;
    return oldValue;
  }

  // O(1) operation to update the value of the last node in the list:
  public Item setBack (Item item) {
    if(isEmpty()) throw new NoSuchElementException("Failed to setBack, because DList is empty!") ;
    // update the value of the last actual node (the last node itself is a sentinel node)
    Item oldValue = last.prev.data; 
    last.prev.data = item;
    return oldValue;
  }

  // O(n)
  public Item setAt (Item item, int index) {
    // check if the index fits in the range:
    if(index<0 || index>size-1) throw new NoSuchElementException("Failed to setBack, because DList is empty!");
    if(index==0) return setFront(item);
    if(index==size-1) return setBack(item);
    // get the node at the index
    Node current = getNodeAt(index);
    Item oldValue = current.data; 
    current.data = item;
    return oldValue;
  }

  // search methods: Both linear time O(n)
  // O(n)
  public boolean contains(Item item){
    if(isEmpty()) throw new NoSuchElementException("Failed to perfrom contains because the DList instance is empty!");
    for(Node temp = first.next; temp.next!=null; temp=temp.next)
      if(temp.data.equals(item)) return true;
    return false;  
  }

  // O(n)
  public int indexOf(Item item) {
    // check if the DList instance contains the element:
    if(!contains(item)) return -1;
    int index;
    Node temp = first; // take a copy of first sentinel node:
    for(index=0; index<size; index++) {
      // if the data part of the given node is equals to the query value:
      if(temp.next.data.equals(item)) return index;
      temp = temp.next; // advance the pointer on the DList
    }
    return -1;
  }

  // size method:
  public int size(){return size;}
  public boolean isEmpty(){return first.next == last;}

  // helper methods:

  // O(n) Linear time operation to get to the Node in the middle of the list (NO random access)
  private Node getNodeAt(int index){
    // first check if index is within valid index range [0,size]:
    if(index<0 || index>size) 
      throw new IndexOutOfBoundsException("Faild to perfomr getNode() because the "+ index +" index that you have specified is way out of bounds!");
    Node temp = first;
    while(index>=0) {
      temp = temp.next; // advance the reference to the next node in the list
      index--;
    }
    return temp;
  }

  // interface implementation:

  /* DList<Item> implements java iterable interface by
  * overrriding its abstract method iterator():
  */
  @Override
  public Iterator<Item> iterator(){return new ListIterator();}

  private class ListIterator implements Iterator<Item> {
    private Node current; // iterate over element of the DList
    public ListIterator() {current=first.next;}
    @Override
    public boolean hasNext(){return current!=last;}
    @Override
    public Item next() {
      // check if hasnext() is true:
      if(!hasNext()) throw new NoSuchElementException("Failed to perform next because hasNext returned false!");
      Item item = current.data;
      current = current.next;
      return item;
    }
  }
}
