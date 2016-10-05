/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode curr=head;
        ListNode tail=head;
 
        // advance the tail pointer n times, remain curr pointer the same
        while(n>0) {
            tail=tail.next;
            n--;
        }
        // remove the node at nth to the end, and return a reference to it back to the head
        head=remove(curr, tail);

        return head;
    }

    // advance both pointers, one node at a time, until tail reaches the end of the list:    
    private ListNode remove(ListNode x, ListNode tail) {
        // termination condition: reaching the end of the linked list
        if(tail==null) {
            // delete the node x is pointing to:
	    // 1. x is the only node in the list
            if(x.next==null)
                x=x.next;

            // 2. in place deletion:
            else {
                // now x is pointing to the nth node from the last node
                x.val=x.next.val;
                x.next=x.next.next;
            }
	  // return a reference to the head of the modified list
          return x;
        }
        else 
          // advance both pointers and modified the sublist headed at the x.next
          x.next=remove(x.next, tail.next);

	// return a reference to the head of the modified list
        return x;
    }
}
