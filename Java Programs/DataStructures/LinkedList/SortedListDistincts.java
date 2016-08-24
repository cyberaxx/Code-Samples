/*
Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

For example,
Given 1->2->3->3->4->4->5, return 1->2->5.
Given 1->1->1->2->3, return 2->3.
*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class SortedListDistincts {
    public ListNode deleteDuplicates(ListNode head) {
        // trivial cases:
        // 0 or 1 elements => no duplicates:
        if(head==null || head.next==null) return head;
        
        // NOTE: there is no IN-PLACE implementation using singly linked list (because we cannot deleteLast())
        // We need another LinkedList that contains
        // only the distinct elements from the original lis
        ListNode newListHead=null;
        // add to the last to preserve the order of elements in the original list
        ListNode newListLast=null;
        ListNode oldLast=null;
        
        ListNode prev=head;
        ListNode current=head.next;
        
        // scan the original list from head to tail
        while(current!=null){
            // check if prev pointer val is equal to current pointer val
            if(prev.val==current.val) {
                current=current.next;
                // do NOT move the prev
                // do NOT copy over anything from the original list to the newList
            }
            
            else {
                // 1. prev can be the last occurence of the repeated item
                if(prev.next!=current) {
                    // advance prev pointer to the node right behind the current:
                    while(prev.next!=current) prev=prev.next;
                    
                    // a. current is the last item:
                    if(current.next==null) {
                        // 1. add current to the tail of the newList
                        oldLast=newListLast;
                        newListLast=new ListNode(current.val);
                        newListLast.next=null;
                        
                        // check if the newList was already empty: update the head
                        if(newListHead==null) newListHead=newListLast;
                        else oldLast.next=newListLast;
                        
                        // 2. advance current:
                        current=current.next;
                        // DONE!
                        
                    }
                    // b. if current is not the last item:
                    else {
                        // skipe the prev
                        prev=prev.next;
                        current=current.next;
                    }
                }
                // 2. prev and current are distinct elements of the original list: prev.next==current
                else if (prev.next==current){
                    // a. current could be the last item:
                    if(current.next==null) {
                        // copy over both prev and current to the newList
                        ListNode temp1=new ListNode(prev.val);
                        temp1.next=null;
                        ListNode temp2=new ListNode(current.val);
                        temp2.next=null;
                        // temp1: temp1->temp2
                        temp1.next=temp2;
                        
                        // add prev->current to the tail of the newList:
                        oldLast=newListLast;
                        newListLast=temp1;
                        
                        // check if the newList was already empty: update the head
                        if(newListHead==null) newListHead=newListLast;
                        else oldLast.next=newListLast;
                    }
                    
                    // b. copy over prev
                    else {
                        // 1. add prev to the tail of the newList
                        oldLast=newListLast;
                        newListLast=new ListNode(prev.val);
                        newListLast.next=null;
                        
                        // check if the newList was already empty: update the head
                        if(newListHead==null) newListHead=newListLast;
                        else oldLast.next=newListLast;
                    }
                    
                    // advance both prev and current:
                    prev=prev.next;
                    current=current.next;
                }
            }
        }
        
        return newListHead;
    }
}
