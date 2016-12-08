/*
class Node {
   int data;
   Node next;

  Node(int data) {
      this.data = data;
  }
}

public class LinkedList
{
    Node head;
    // Member Methods
}*/

/* This is method only submission.
You only need to complete the below method.*/
class GfG
{
    void compute(LinkedList l)
    {
        Node head=l.head;
        if(head==null || head.next==null) return ;
        for(Node next=l.head.next; next!=null; next=next.next) {
            Node prev=null;
            Node current=null;
            for(current=l.head; current!=next && current!=null ; current=current.next) {
                /* if the node on the right have a higher value: */
                if(current.data<next.data) {
                    /* if current node is the first node: */
                    if(current==l.head) {
                        l.head=current.next;
                    }
                    /* otherwise: if the current node is in the middle: */
                    else {
                        prev.next=current.next;
                    }
                }
                else {
                    /* if the greater condition is not hold: advance the prev pointer: */
                    prev=current;
                }
            }
            /* if the current is null at the end of inner loop: */
            if(current==null) l.head=next;
        }
    }
}
