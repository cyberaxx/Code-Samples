/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode removeElements(ListNode head, int val) {
        head=remove(head, val);
        return head;
    }
    private ListNode remove(ListNode x, int val) {
        // termination condition:
        if(x==null) return null;

        // for all non-null nodes:
        if(x.val==val) {
            // delete the node x
            x=x.next;
            x=remove(x, val);
        }
        else x.next=remove(x.next, val);
        
        return x;
    }
}
