import java.util.*

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

  public static void main(String[] args){

  }

}
