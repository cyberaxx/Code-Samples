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
  

  public static void main(String[] args){
    int item=0;
    Node<Integer> x1=new Node<Integer>(item++);
    Node<Integer> x2=new Node<Integer>(item++);
    Node<Integer> x3=new Node<Integer>(item++);
    Node<Integer> x4=new Node<Integer>(item++);
    Node<Integer> x5=new Node<Integer>(item++);
    x1.next=x2;
    x2.next=x3;
    x3.next=x4;
    x4.next=x5;
    Node<Integer> head=x1;

    System.out.println("Before:");
    for(int i=0; i<item; i++)
      System.out.println(getNth(head, i).item());

    head=insertNth(head, 0, 0); item++;
    head=insertNth(head, 4, 6); item++;
    head=insertNth(head, 5, 7); item++;

    head=sortedInsert(head, new Node<Integer>(12)); item++;
    head=sortedInsert(head, new Node<Integer>(-3)); item++;
    head=sortedInsert(head, new Node<Integer>(8)); item++;
    head=sortedInsert(head, new Node<Integer>(6)); item++;
    head=sortedInsert(head, new Node<Integer>(6)); item++;

    System.out.println("After:");
    for(int i=0; i<item; i++)
      System.out.println(getNth(head, i).item());

    head=deleteList(head);
    System.out.println(head);
  }

}
