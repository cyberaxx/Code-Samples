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
    public boolean hasCycle(ListNode head) {
      ListNode fast=head; // fast pointer, moves two nodes at a time
      ListNode slow=head; // slow pointer, moves one node at a time
      ListNode x=loop(fast, slow); // if slow and fast meet each other before fast hit the null link (linked list is cyclic)
      
      // if there exist a loop, find its length:
      if(x!=null) {
	int n=1;
      	slow=x.next; // move the slow poniter and count number of links until it meets fast again 
      	while(slow!=x) {
      	  slow=slow.next;
	  n++;
      	}
      }

      return x!=null;
    }

    // Floyd algorithm to find a cycle in a linkedlist
    private ListNode loop(ListNode fast, ListNode slow) {
        // termination condition:
        if(fast==null) return null; // no loop (fast pointer reached the end of the list)
        
        // Recurrence:
        // advance fast pointer once
        fast=fast.next;
        if(fast==null) return null; // no loop (fast pointer reached the end of the list)
        // advance fast pointer (the second time)
        fast=fast.next;
        // advance slow pointer once
        slow=slow.next;
        // check if they meet?
        if(fast==slow) return fast; // loop!
        return loop(fast, slow);
    }

    // finding middle of a singly linked list
    private ListNode mid(ListNode head, ListNode tail) {
        // take two pointer both starting at the head of SList
        ListNode mid=head;
        ListNode last=head;
        
        // move last twice faster than mid, once, last reaches the end of the list, mid would be in the middle
        while(last!=tail) {
            last=last.next;
            if(last==tail) return mid;
        
            last=last.next;
            mid=mid.next;
        }
        return mid;
    }
}
