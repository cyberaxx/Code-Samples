/*
Given a sorted linked list, delete all duplicates such that each element appear only once.

For example,
Given 1->1->2, return 1->2.
Given 1->1->2->3->3, return 1->2->3.
*/


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class SortedListDuplicates {
    public ListNode deleteDuplicates(ListNode head) {
        /*
        Trivial cases:
        1. empty list: null 
        2. one item list: null
        */
        if(head==null||head.next==null) return head; 
        
        int prevVal=head.val;
        ListNode prevNode=head;
        ListNode currentNode;
        // scan the list from left to right
        // starting form head.next:
        currentNode=head.next;
        while(currentNode!=null) {
            // if keys were equal
            if(currentNode.val==prevNode.val) {
                // 1. bypas the node with equal key:
                prevNode.next=currentNode.next;
                // 2. advance the scanner pointer
                currentNode=currentNode.next;
                // 3. do NOT advance the prevNode unless for inequal keys
            }
            else {
                // advance both prev and current pointer
                prevNode=currentNode;
                currentNode=currentNode.next;
            }
        }
        
        return head;
        
    }
}
