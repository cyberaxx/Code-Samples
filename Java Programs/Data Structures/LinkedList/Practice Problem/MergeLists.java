/*
  Merge two linked lists 
  head pointer input could be NULL as well for empty list
  Node is defined as 
  class Node {
     int data;
     Node next;
  }
*/

class gfg
{
    Node MergeLists(Node headA, Node headB) {
         // This is a "method-only" submission. 
         // You only need to complete this method
         /* pointers to the start of first hald and second half: */
         if(headA==null) return headB;
         if(headB==null) return headA;
         
         Node a=headA;
         Node b=headB;

         /* first node of the list: */
         Node current;
         Node head;
         
         /* start of with the head of merged list: */
         if(a.data<b.data)  {
             current=a; 
             head=current;
             a=a.next; 
         }
         else {
             current=b;
             head=current;
             b=b.next;
         }
         
         /* merge list a and b: */
         while(a!=null||b!=null) {
             /* if the first list is exhusted: */
             if(a==null) {
                 /* choose from the second list: */
                 current.next=b;
                 current=current.next;                 
                 b=b.next;
             }
             else if(b==null) {
                 /* choose from the first list: */
                 current.next=a;
                 current=current.next;
                 a=a.next;
             }
             else if(b.data<a.data) {
                 /* choose from the second list: */
                 current.next=b;
                 current=current.next;
                 b=b.next;
             }
             else {
                 /* choose from the first list: */
                 current.next=a;
                 current=current.next;
                 a=a.next;
             }
         }
         return head;         
   } 
}
