/*Node class  used in the program
class Node
{
	int data;
	Node next;
	Node bottom;
	
	Node(int d)
	{
		data = d;
		next = null;
		bottom = null;
	}
}
*/
/*  Function which returns the  root of 
    the flattened linked list. */
class GfG
{
    Node flatten(Node root)
    {
    	// Your code here
    	if(root==null|| root.next==null) return root;
    	
    	/* two pointers on the current column and the next column: */
    	Node currCol=root;
    	Node nextCol=root.next;
    	/* while a next column exists: */
    	while(nextCol!=null) {
    	    currCol=merge(currCol, nextCol);
    	    nextCol=nextCol.next;
    	}
    	return currCol;
    }
    
    /* merge list1 and list2 (vertically) and return the pointer to the head of resulting list: */
    Node merge(Node headA, Node headB) {
        
        /* pointers on two vertical lists and the result head: */
        Node head=null;
        Node current=null; /* running pointer */
        Node currA=headA; /* head of the first list */
        Node currB=headB; /* head of the second list */
        
        /* find out the head of the resulting list: */
        if(currA.data<currB.data) {
            head=currA;
            current=head;
            /* advance the pointer on the first list */
            currA=currA.bottom;
        }
        else {
            head=currB;
            current=head;
            /* advance the pointer on the second list */
            currB=currB.bottom; 
        }
        
        /* for all node in two sorted lists: */
        /* merge list1 and list2: */
        while(currA!=null || currB!=null) {
            /* if the first list is exhuasted take from the second list and advance its pointer: */
            if(currA==null) {
                current.bottom=currB;
                current=current.bottom; /* advance on the resulting list */
                currB=currB.bottom; /* advance the pointer on the 2nd list */
            }
            /* if the second list is exhuasted take from the first list and advance its pointer: */
            else if(currB==null) {
                current.bottom=currA;
                current=current.bottom; /* advance on the resulting list */
                currA=currA.bottom; /* advance the pointer on the 1st list */
            }
            /* if the item on the second list has data associated with it less than the item on the 1st list: */ 
            else if(currB.data<currA.data) {
                /* add from the second list to the solution: */
                current.bottom=currB;
                current=current.bottom; /* advance on the resulting list */
                currB=currB.bottom; /* advance the pointer on the second list */
            }
            /* otherwise: choose from teh first list: */
            else {
                current.bottom=currA;
                current=current.bottom; /* advance on the resulting list */
                currA=currA.bottom;
            }
        }
        /* return the head of the list: */
        return head;
    }
}
