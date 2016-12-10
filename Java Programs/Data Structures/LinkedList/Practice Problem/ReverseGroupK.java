/*
Return reference of new head of the reverse linked list 
 class Node {
   int value;
 Node next;

  Node(int value) {
   this.value = value;

  }
}
This is method only submission.
You only need to complete the method.
*/
class gfg
{
    Node reverse(Node head, int k)
    {
        //add code here
        /* termination condition: */
        if(head==null || head.next==null) return head;

        Node current=head;
        Node prev=null;
        Node next=null;
        int count=1;
        while(current!=null && count<=k) {
            /*advance next*/
            next=current.next;
            /* rewiring pointers: */
            current.next=prev;
            prev=current;
            current=next;
            count++;
        }

        /* propagate the change: */
        if(next!=null) head.next=reverse(next, k);
        
        return prev;
    }
    
    /* reverse a linkedlist a return a pointer to its head: */
    Node reverse(Node head) {
        /* termination condition: */
        if(head==null || head.next==null) return head;
        
        Node current=head;
        Node prev=null;
        Node next=null;
        while(current!=null) {
            /*advance next*/
            next=current.next;
            /* rewiring pointers: */
            current.next=prev;
            prev=current;
            current=next;
        }
        /* restore the head: */
        head=prev;
        /* return the head: */
        return head;
    }
}
