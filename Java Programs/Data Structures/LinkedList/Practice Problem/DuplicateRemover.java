/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode deleteDuplicates(ListNode head) {
      if(head==null) return null;
      removeDup(head, head.next);
      return head;
    }
    private void removeDup(ListNode current, ListNode next) {
        // termination conditions:
        if(current==null) return ;
        if(next==null) return ;
        
        // for all non-null nodes:
        // compare current and the next:
        if(current.val==next.val) {
            // duplicate is found:
            next=next.next; // advance the next to the next node in the list
            current.next=next; // current jump up the next and moves to the next node
            // the old next would be collected by GC
            removeDup(current, next);
        }
        else 
          // recursively build the sublist:
          removeDup(current.next, next.next);
    }
}
