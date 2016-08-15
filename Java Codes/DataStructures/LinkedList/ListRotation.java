/*
Given a list, rotate the list to the right by k places, where k is non-negative.

For example:
Given 1->2->3->4->5->NULL and k = 2,
return 4->5->1->2->3->NULL.
*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

public class ListRotation {
    public ListNode rotateRight(ListNode head, int k) {
        // all trivial cases:
        // No rotation required
        if(head==null || head.next==null) return head;
        if(k==0) return head;
        
        ListNode headNode = head;
        ListNode lastNode = head;
        ListNode currentNode = head;
        int n = 1; // including non-null head;
        while(currentNode.next!=null) {
            currentNode=currentNode.next;// advance node to the last node in the list
            n++;
        }
        
        k = k%n;
        
        // last node pointer:
        lastNode=currentNode;
        ListNode oldLast=lastNode;
        // we have to keep removing from first and adding to the last n-k times:
        for(int i=0; i<n-k; i++) {
            // remove from first
            currentNode=new ListNode(headNode.val);
            currentNode.next=null;
            headNode=headNode.next;
            
            // add to the last
            oldLast=lastNode;
            lastNode=currentNode;
            oldLast.next = lastNode;
        }
        
        // new head for the list
        head=headNode;
        
        return head;
    }
}
