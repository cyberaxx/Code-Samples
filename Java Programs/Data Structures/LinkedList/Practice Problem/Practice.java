import java.util.*;

public class Practice {
  // static nested class: Slist Nodes with data field of generic type:
  private static class Node<Item> {
    // instance vairables (fiedls): Structure of the node:
    private Item item;
    private Node next;

    // Constructors:
    public Node(){} // void constructor
    public Node(Item item) {this.item=item;}

    // no behaviours except the getter method
    public Item item(){return item;}
  }

  //  1. Count the number of occurrence of a given key in a SLit:
  public static int count(Node<Integer> x, int item) {
    // termination condition:
    if(x.next==null) return 0; // hitting the end of the list

    // for all non-null nodes:
    if(x.item()==item) return 1+count(x.next, item); 
    return count(x.next, item);
  }

  //  2. getNth get the Nth node in the list (assuming n is within the legal rage [0 list.size()-1]
  private static <Item> Node<Item> getNth(Node<Item> x, int n) {
    // termination condition:
    if(n==0) return x;
    return getNth(x.next, --n);
  }
   
  // 3. InsertNth: insert to the Nth place in the list (assuming N is within the legal range): returns a reference to the head of modified List
  private static <Item> Node<Item> insertNth(Node<Item> x, Item item, int n) {
    // termination condition:
    if(n==0) {
      // if list is empty: x==null
      if(x==null) return new Node<Item>(item);

      // Otherwise:
      // create a new node with the given x's item:
      Node<Item> node=new Node<Item>(x.item());
      node.next=x.next;
      x.next=node;
      x.item=item;
      return x;
    }

    // advance x by one node, x.next link would be pointing to a modified list (after insertion)
    x.next=insertNth(x.next, item, --n);
    return x;
  }
  // 4. deleteList: delete the entire SList: returns a reference to the head of the modified list
  private static <Item> Node<Item> deleteList(Node<Item> x) {
    // termination condition: hitting end of the list:
    if(x==null) return x;
    // for all non-null node x:
    x=deleteList(x.next);
    return x;
  }

  // 5. SortedInsert: insert into node at its rightful position in a sorted list: return a reference to the modified list:
  private static Node<Integer> sortedInsert(Node<Integer> x, Node<Integer> node) {
    // termination condition: search miss:
    if(x==null) return node;

    // Otherwise:
    // Compare the new node with the current head of the sublist x:
    if(node.item()>x.item())
      // new has to be inserted to the list headed at x.next (x.next link is going to get modified):
      x.next=sortedInsert(x.next, node);
    else {
      // if the item associated to the new node is less than or equal to the current head node x:
      // add the new node before node x (with no need to have a pointer to the previous node):
      // 1. copy x's value to the new node
      int item=node.item();
      node.item=x.item();
      // insert the new node after node x:
      node.next=x.next;
      x.item=item;
      x.next=node;
    }
    return x;
  }

  /*6. Write an Append() function that takes two lists, 'a' and 'b', appends 'b' onto the end of 'a',
       and then sets 'b' to NULL: return a reference to the head of a:  */
  private static <Item> Node<Item> append(Node<Item> x1, Node<Item> x2) {
    // termination condition:
    if(x1==null) {
      return x2;
    }
    // Otherwise:
    x1.next=append(x1.next, x2);
    return x1;
  }
 
  /*7. FrontBackSplit()
    Given a list, split it into two sublists — one for the front half, and one for the back half. */
  private static <Key> Node<Key> FrontBackSplit(Node<Key> fast, Node<Key> slow) {
    // termination condition: if the fast pointer reaches the end of the list, the slow would be in the middle:
    if(fast==null) return slow;
    // Otherwise:
    // advance the fast pointer by one:
    fast=fast.next;
    // check if fast reaches the end of the list:
    if(fast==null) return slow;
    // otherwise advance then both
    return FrontBackSplit(fast.next, slow.next);
  } 

  /* 11 — MoveNode()
     This is a variant on Push(). 
     Instead of creating a new node and pushing it onto the given
     list, MoveNode() takes two lists, removes the front node from the second list and pushes
     it onto the front of the first. */
  private static <Key> Node<Key> moveNode(Node<Key> x1, Node<Key> x2) {
    // x1 is a reference to the head of the first list
    // x2 is a reference to the head of the second list
    if(x2==null) return x1;// if the second list is empty: do nothing

    // Otherwise: pointer rewiring:
    // Invariant to maintain: x1 and x2 must remain as pointers to the head of first and second lists
    Node<Key> temp=x2; // copy a reference to the head of the second list
    // advance the x2 pointer the the next node:
    x2=x2.next; // now x2 is pointing the second node in the second list (we're done with the second list and second list is NOT modified)

    // set the temp to point to the x1:
    temp.next=x1;
    // set the x1 pointer, now point to temp:
    x1=temp;
    
    // Done!
    return x1;
  }

  /* 12 — AlternatingSplit()
   Write a function AlternatingSplit() that takes one list and divides up its nodes to make
   two smaller lists. The sublists should be made from alternating elements in the original
   list. So if the original list is {a, b, a, b, a}, then one sublist should be {a, a, a} and the
   other should be {b, b}. You may want to use MoveNode() as a helper. The elements in
   the new lists may be in any order
 */
  private static <Key> void alternatingSplit(Node<Key> x) {
    // declare two reference to head of two list that are going to be constructed from x
    Node<Key> x1=null;
    Node<Key> x2=null;
  
    // iterate over the x list from the head to the tail:
    while(x!=null) {
      // add nodes the head of x1 and x2 alternatively without destructing the structure of x
      x1=moveNode(x1, x);
      // advance the pointer on the x list:
      x=x.next;
      // check if x has been reached the tail of the list:
      if(x==null) break;
      // otherwise:
      x2=moveNode(x2,x);
      // advance the x pointer:
      x=x.next;
    }
  }

  public static void main(String[] args){
  }
}
