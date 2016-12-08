/*
class Node {
   int data;
   Node next;

  Node(int data) {
      this.data = data;
  }
}
This is method only submission.
You only need to complete the below method.
*/
class gfg
{

   // Should rearrange given linked list such that all even
   // positioned Nodes are before odd positioned.
   // Returns new head of linked List.
    Node rearrange(Node head)
    {
          //  The task is to complete this method
          if(head==null||head.next==null) return head;
          
          /* Pointers on odd list, even list and two running pointers: */
          Node currOdd=head;
          Node prevOdd=null;
          Node currEven=head.next;
          Node evenHead=head.next;
          
          /* while currOdd is not null: */
          while(currEven!=null) {
              currOdd.next=currEven.next;
              /* copy the currOdd pointer: */
              prevOdd=currOdd;
              /* advance the currOd pointer: */
              currOdd=currOdd.next;
              if(currOdd!=null) {
                  currEven.next=currOdd.next;
                  /* advance currEven: */
                  currEven=currEven.next;
              }
              else {
                  /* use the last not null odd pointer: */
                  currOdd=prevOdd;
                  break;
              }
          }
          
          currOdd.next=evenHead;
          return head;
     }
}
