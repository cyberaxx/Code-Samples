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

  public static void main(String[] args){
    Node<Integer> x1=new Node<Integer>(1);
    Node<Integer> x2=new Node<Integer>(2);
    Node<Integer> x3=new Node<Integer>(3);
    Node<Integer> x4=new Node<Integer>(4);
    Node<Integer> x5=new Node<Integer>(5);
    x1.next=x2;
    x2.next=x3;
    x3.next=x4;
    x4.next=x5;
    Node<Integer> head=x1;

    System.out.println("Before:");
    System.out.println(getNth(head, 0).item());
    System.out.println(getNth(head, 1).item());
    System.out.println(getNth(head, 2).item());
    System.out.println(getNth(head, 3).item());
    System.out.println(getNth(head, 4).item());

    head=insertNth(head, 0, 0);
    head=insertNth(head, 4, 5);
    head=insertNth(head, 5, 5);

    System.out.println("After:");
    System.out.println(getNth(head, 0).item());
    System.out.println(getNth(head, 1).item());
    System.out.println(getNth(head, 2).item());
    System.out.println(getNth(head, 3).item());
    System.out.println(getNth(head, 4).item());
    System.out.println(getNth(head, 5).item());
    System.out.println(getNth(head, 6).item());
    System.out.println(getNth(head, 7).item());

    head=deleteList(head);
    System.out.println(head);
  }

}
