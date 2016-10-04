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

  // 3. deleteList: delete the entire SList: returns a reference to the head of the modified list
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
    System.out.println(head);
    head=deleteList(head);
    System.out.println(head);
  }

}
