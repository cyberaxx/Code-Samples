import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
	public static void main (String[] args) {
		//code
		Scanner scanner=new Scanner(System.in);
		int count=scanner.nextInt();
		for(int i=0;i<count;i++) {
		    int num=scanner.nextInt();
		    int[]test=array(scanner, num);
		    System.out.println(maxRect(test));
		}
		scanner.close();
	}
	
	private static int maxRect(int[] hist) {
	    Deque<Integer> stack=new ArrayDeque<Integer>(); /* empty stack of array indexes: */
	    int maxArea=0;
	    int area=0;
	    /* loop over the array on height in the histogram: */
	    int i;
	    for(i=0; i<hist.length; i++) {
	        int current=hist[i];
	        /* while stack is not empty and the current is (strictly) less than the top of the stack: */
	        while(!stack.isEmpty() && current<hist[stack.peek()]) {
	           /* pop from the stack: */
	           int j=stack.pop();
	           /* compute the area: */
	           /* case 1: stack is empty: */
	           if(stack.isEmpty()) {
	               /* caculate the contribution the poped column from to the area: */
                   area=hist[j]*i;
	           }
	           /* case 2: stack is not empty: */
	           else {
	               area=hist[j]*(i-stack.peek()-1);
	           }
	           
    	        /* compare the area with the max area: */
    	        if(maxArea<area) maxArea=area;
	        }
	        
	        /* push the new index to the stack if the current height is greater or equal to the stack */
	        stack.push(i);
	    }
	    
	    /* while stack is not empty: */
	    while(!stack.isEmpty()) {
           /* pop from the stack: */
           int j=stack.pop();
           /* compute the area: */
           /* case 1: stack is empty: */
           if(stack.isEmpty()) {
               /* caculate the contribution the poped column from to the area: */
               area=hist[j]*i;
           }
           /* case 2: stack is not empty: */
           else {
               area=hist[j]*(i-stack.peek()-1);
           }
           
	       /* compare the area with the max area: */
	       if(maxArea<area) maxArea=area;
	    }
	    
	    /* return max area: */
	    return maxArea;
	}
	
	private static int[] array(Scanner scanner, int num) {
	    int[]test=new int[num];
	    for(int i=0; i<num; i++) {
	        test[i]=scanner.nextInt();
	    }
	    return test;
	}
}
