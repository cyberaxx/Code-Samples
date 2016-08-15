/*
Implement an algorithm to delete a node in the middle of a single linked list, given
only access to that node.
EXAMPLE
Input: the node ‘c’ from the linked list a->b->c->d->e
Result: nothing is returned, but the new linked list looks like a->b->d->e
*/

public class LLCCode23 {
  public static boolean deleteMiddle(LinkedListNode node) {
    if(node=null || node.next=null) return false; // Can NOT delete the last node in SList! We must have a DList
    // copy over next to current, delete next by pointer rewiring:
    LinkedListNode nextNode = node.next;
    node.data = nextNode.data;
    node.next = nextNode.next;
    return true;
  }

}
