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
        if(head==null) return head;
        
        HashSet<Integer> dups=new HashSet<Integer>(); // black list
        removeDup(head, head.next,dups);
        
        // if the dups set is empty (all list items were distinct:
        if(dups.isEmpty()) return head;
        
        // Otherwise:
        // New list of distinct nodes:
        ListNode distinct=null;
        ListNode last=distinct;
        
        // iterate over the modified list:
        ListNode x=head;
        // add all items which are not blacklisted to the new distinct list:
        while(x!=null) {
            int key=x.val;
            // check the black list
            if(!dups.contains(key))
            {
                // add a new node to the tail of the distinct list
		// disitinct=addLast(disitinct, key);
                if(distinct==null) {
                    last=new ListNode(key);
                    distinct=last;
                }
                else {
                    ListNode oldLast=last;
                    last=new ListNode(key);
                    oldLast.next=last;
                }
            }
                
            x=x.next;
        }
        return distinct;
    }
    private void removeDup(ListNode current, ListNode next, HashSet<Integer> dups) {
        // termination conditions:
        if(current==null) return ;
        if(next==null) return ;
        
        // for all non-null nodes:
        // compare current and the next:
        if(current.val==next.val) {
            // duplicate is found:
            dups.add(current.val);
            next=next.next; // advance the next to the next node in the list
            current.next=next; // current jump up the next and moves to the next node
            // the old next would be collected by GC
            removeDup(current, next, dups);
        }
        else 
          // recursively build the sublist:
          removeDup(current.next, next.next, dups);
    }
}
