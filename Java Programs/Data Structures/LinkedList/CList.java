/*
  Circular doubly linked list:
  Instead of setting the .next field of the last node to NULL,
  set it to point back around to the first node.
  Instead of needing a fixed head end, any pointer into the list will do.
*/

import java.util.*;

public class CList<Item> {
  // instance field: a reference to a sentinel node:
  private Node<Item> sentinel;
  private int size;

  // Constructor:
  public CList() {
     sentinel=new Node<Item>();
     sentinel.next=sentinel.prev;
     sentinel.prev=sentinel.next;
     size=0;
  }

  // API: behaviours (addFirst, addLast, removeFirst, removeLast, contains, remove, size, isEmpty) 
  public int size(){return size;}
  public boolean isEmpty(){return sentinel.next==sentinel.prev;}
  public void addFirst(Item item){}
  public void addLast(Item item){}
  public void removeFirst(Item item){}
  public void removeLast(Item item){}

  public boolean contains(Item item){return false;}
  public void remove(Item item){}

  // Node of CList:
  private static class Node<Item> {
    // instance fields: Object Structure
    private Item item; // data
    private Node<Item> prev, next; // reference to prev and next Node

    // Constructor:
    public Node(){} // to be able to create a sentinel node
    public Node(Item item) {
      // set the item field to the given item
      this.item=item;
    }
  }
}

