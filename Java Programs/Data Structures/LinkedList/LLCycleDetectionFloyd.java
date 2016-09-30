/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
      ListNode fast=head;
      ListNode slow=head;
      ListNode x=loop(fast, slow);
      // if cycle has not been found:
      if(x==null) return null;
      
      // otherwise: find the begining of the cycle by:      
      // 1. moving slow to the head of the list, and fast at the detection piont
      slow=head;
      fast=x;
      // 2. moving slow and fast, one node at a time until they meet
      while(slow!=fast) {
          slow=slow.next;
          fast=fast.next;
      }
      // the meeting point is the start of the cycle
      return slow;
    }
    private ListNode loop(ListNode fast, ListNode slow) {
        // termination condition:
        if(fast==null) return null; // no loop (reached the end of the list)
        
        // Recurrence:
        // advance fast pointer
        fast=fast.next;
        if(fast==null) return null; // no loop (reached the end of the list)
        fast=fast.next;
        slow=slow.next;
        if(fast==slow) return fast;
        return loop(fast, slow);
    }
}
