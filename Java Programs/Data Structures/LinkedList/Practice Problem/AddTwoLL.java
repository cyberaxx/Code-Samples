class GfG
{
	Node addTwoLists(Node first, Node second)
	{
	   // Your code here
	   return add(first, second, 0);
	}
	
	Node add(Node first, Node second, int carry) {
	   /* termination condition: */
        if(first==null&&second==null)  {
            if(carry>0) return new Node(carry);
            else return null;
        }

        else if(first==null&&second!=null) {
	       int sum=second.data+carry;
	       carry=sum/10;
	       Node current=new Node(sum%10);
	       current.next=add(first, second.next, carry);
	       return current;
	    }
	    else if(first!=null&&second==null) {
	       int sum=first.data+carry;
	       carry=sum/10;
	       Node current=new Node(sum%10);
	       current.next=add(first.next, second, carry);
	       return current;
	    }
	    
	   /* where both are not null: */
	   else {
	       int sum=first.data+second.data+carry;
	       carry=sum/10;
	       Node current=new Node(sum%10);
	       current.next=add(first.next, second.next, carry);
	       return current;
	   }
	}
}
