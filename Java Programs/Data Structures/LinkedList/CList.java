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

  // Constructor:
  public CList() {
     sentinel=new Node<Item>();
     sentinel.next=sentinel.prev;
     sentinel.prev=sentinel.next;
  }

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

