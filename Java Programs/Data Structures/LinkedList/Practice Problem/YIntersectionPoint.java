/* Node of a linked list
 class Node {
   int data;
    Node next;
    Node(int d)  { data = d;  next = null; }
}
 Linked List class
class LinkedList
{
    Node head;  // head of list
}
This is method only submission.  You only need to complete the method. */
class GFG
{
	int intersectPoint(Node headA, Node headB)
	{
         // code here
         HashSet<Node> visited=new HashSet<Node>();
         /* first node: */
         Node currA=headA;
         Node currB=headB;
         
         /* traverse the first list from head to tail and populate the set of visited states: */
         traverse(currA, visited);
         while(currB!=null) {
	        /* visit the new state: */
	        if(currB.next!=null) {
	            /* if this state has been visited before: */
	            if(visited(currB.next, visited)) return currB.next.data;
	        }
            currB=currB.next; /* advance the currB pointer*/
	    }
         return 0;
	}
	
	private static void traverse(Node head, HashSet<Node> visited) {
	    while(head!=null) {
	        /* visit the new state, mark it as visited: */
	        if(head.next!=null) visited.add(head.next);
            head=head.next; /* advance the head pointer*/
	    }
	}
	
	private static boolean visited(Node x, HashSet<Node> visited) {
        if(visited.contains(x)) return true;
        visited.add(x); return false;
    }
}
