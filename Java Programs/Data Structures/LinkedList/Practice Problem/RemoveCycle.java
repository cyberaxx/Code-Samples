/*Complete The function
Node is as follows:
class Node {
    int data;
    Node next;
    Node(int d) {
        data = d;
        next = null;
    }
}*/
class GfG{
    int removeTheLoop(Node node) {
        //add code here.
        /* trivial cases: */
        if(node==null||node.next==null) return 0; /* no loop! */ 
        Node current=node;
        /* check if the list has a loop: */
        Node loop=headOfLoop(node);
        if(loop==null) return 0; /* no loop! */
        /* Otherwise: */
        while(current.next!=loop)   current=current.next;
        /* remove the loop: */
        current.next=null;
        return 1;
    }
    
    private static Node headOfLoop(Node head) {
        /* tow pointer of the list: */
        Node slow=head;
        Node fast=head.next;
        /* check for the loop: */
        while(fast!=null && slow!=fast) {
            /* advance pointers: */
            slow=slow.next;
            fast=fast.next;
            /*check if fast has not reached end yet: */
            if(fast==null) return null; /* no loop! */
            fast=fast.next; /* advance fast pointer */
        }
        
        /* check if fast reached the end: */
        if(fast==null) return null; /* no loop! */
        /* Otherwise: reset the slow and move them both at the same pace: */
        slow=head;
        while(fast.next!=slow) {
            slow=slow.next;
            fast=fast.next;
        }
        return fast;
    }
}
