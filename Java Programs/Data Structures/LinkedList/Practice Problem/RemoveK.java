/* Link list Node
class Node
{
	Node next;
	int data;
	Node(int d)
	{
		data = d;
		next = null;
	}
}*/
class GfG
{
    /*You are required to complete this method*/
    Node delete(Node head, int k)
    {
    	// Your code here
    	/* trivial cases: */
    	if(head==null || k==0) return head;
    	int i=1; /* counter: */
    	int listLength=1; /* length of the list */
    	Node current=head; /* running pointer on the list */
    	
    	/* if the last node to remove happened to be the last node of the list: */
    	boolean lastNode=false;

    	while(current!=null) {
    	    if(i%k==0) {
    	        /* remove the ith node: */
    	        if(current.next==null) {
    	            lastNode=true;
    	            break;
    	        }
    	        else {
    	            /* remove the node in-place: */ 
        	        current.data=current.next.data;
        	        current.next=current.next.next;
        	        i++;
    	        }
    	    }
    	    else {
    	        /* advance on the list one node */
        	    current=current.next;
        	    i++;
        	    listLength++;
    	    }
    	}
    	
    	/* if last node is not rquired to be deleted: */
    	if(!lastNode) return head;
    	
    	/* Otherwise, delete the last node: */
    	else {
    	    Node temp=head;
    	    for(int j=1; j<listLength-1; j++) temp=temp.next;
    	    temp.next=null;
    	    return head;
    	}
    }
}
