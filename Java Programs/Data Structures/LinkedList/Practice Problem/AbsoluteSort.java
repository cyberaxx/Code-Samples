/* The structure of the node of the Linked List is
class Node
{
   int data;
   Node next;
   Node(int d) {data = d; next = null; }
 }
*/

class GfG
{
	Node sortedList(Node head)
	{
		// Your code here
		Node first=head;
		Node current=head;
		Node prev=null; /* this node is required for deletion and insertion in the middle */
		
		/* move on the list from the head node: */
		while(current!=null) {
		    /* check if the data of the current node is negative: */
		    if(current.data<0) {
		        /* remove the node from the list: */
		        /* 1. if the current is the first node of the list: */
		        if(current==first) {
		            first=current.next; /* first now points to the node next to the current */
    		        /* add the node to the head of the list: */
    		        Node newNode=new Node(current.data);
    		        newNode.next=first;
    		        first=newNode;
    		        /* update the previous pointer: */
    		        prev=first;
		        }
		        /* 2. if the current is a node in the middle of the list: */
		        else {
		            prev.next=current.next; /* current get skipped by the previous */
    		        /* add the node to the head of the list: */
    		        Node newNode=new Node(current.data);
    		        newNode.next=first;
    		        first=newNode;
    		        /* maintain the previous pointer */
		        }
		        
		        /* advance the current pointer: */
		        current=current.next;
		    }
		    
		    /* otherwise: data is non-negative, do nothing, advance both current and prev pointers */
		    else {
		        prev=current;
		        current=current.next;
		    }
		}
		
		return first;
	}
}
