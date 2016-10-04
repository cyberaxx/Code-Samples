/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode swapPairs(ListNode head) {
      head=swap(head);
      return head;
    }
    private ListNode swap(ListNode x) {
        // termination condition:
        if(x==null) return x;
        if(x.next==null) return x; // for odd length lists
        
        // for all non-null nodes:
        // swap x with x.next:
        ListNode next=x.next;
        ListNode temp=x;
        // rewiring pointers:
        x.next=next.next;
        next.next=temp;
        
	// propagate the change throughout the entire list and return a reference to a head
        // modified sublist:
        x.next=swap(x.next);
        
        return next;
    }
}
