/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode insertionSortList(ListNode head) {
        ListNode sorted=null;
        ListNode x=head;
        while(x!=null) {
            sorted=sortedInsert(sorted, x.val);
            x=x.next;
        }
        return sorted;
    }
    private ListNode sortedInsert(ListNode x, int item) {
        // termination condition: if list headed at x hit the null
        if(x==null) return new ListNode(item);
        
        // otherwise:
        // compare the node value with the current head to direct the search:
        // 1. if the new node is greater than the current head:
        // new node must be add to the list headed at x.next
        if(item>x.val) x.next=sortedInsert(x.next, item);
        // 2. if the new node is less than or equal to x, node has to be inserted before x
        else {
            ListNode node=new ListNode(x.val);
            node.next=x.next;
            x.next=node;
            x.val=item;
        }       
        return x;
    }
}
