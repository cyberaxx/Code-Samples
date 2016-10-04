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
 
  /* FrontBackSplit()
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

  public static void main(String[] args){
  /*
    Node<Integer> head1=null;
    Node<Integer> head2=null;
    Random random=new Random();

    // insert 6 random keys to the list:
    int items=6;
    for(int i=0; i<items; i++)
      head1=insertNth(head1, random.nextInt(), 0);

    for(int i=0; i<items; i++)
      head2=insertNth(head2, random.nextInt(), 0);

   System.out.println("head1:");
    for(int i=0; i<items; i++)
      System.out.println(getNth(head1, i).item());
   System.out.println("head2:");
    for(int i=0; i<items; i++)
      System.out.println(getNth(head2, i).item());

   head1=append(head1, head2);
   System.out.println("head1 append heade2:");
    for(int i=0; i<items*2; i++)
      System.out.println(getNth(head1, i).item());

   Node<Integer> slow=head1;
   Node<Integer> fast=head1;
   slow=FrontBackSplit(fast, slow);
   System.out.println("slow:"+slow.item());
   System.out.println("slow.next:"+slow.next.item());
   System.out.println("fast:"+fast.item());
  */
  }
}
