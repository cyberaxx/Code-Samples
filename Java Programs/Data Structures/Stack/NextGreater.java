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
		    nextGreater(test);
		    printArray(test);
		}
		scanner.close();
	}
	
	private static void nextGreater(int[] input) {
	    /* linear time approach using stack: extra linear space (in-place approach took Quadratic time, constant space) :*/
	    /* extra linear space: */
	    Deque<Integer> stack=new ArrayDeque<Integer>(); /* empty stack that is going to hold the index on array elements */

        /* iterate over array of elements: */
        for(int i=0; i<input.length; i++) {
            /* check if the stack is not empty and the array element is larger than the top of the
                stack, then set the array element to be the item at the stack index, and pop the top
                of the stack: */
            while(!stack.isEmpty() && input[stack.peek()]<input[i]) {
                int j=stack.pop();
                input[j]=input[i];
            }
            
            /* push the index i to the stack: */
            stack.push(i);
        }
	    
	    /* while stack is not empty: for all items remaining in the stack ngre is -1: */
	    while(!stack.isEmpty()) {
	        int j=stack.pop();
	        input[j]=-1;
	    }

	}
	private static int[] array(Scanner scanner, int num) {
	    int[]test=new int[num];
	    for(int i=0; i<num; i++) {
	        test[i]=scanner.nextInt();
	    }
	    return test;
	}

	private static void printArray(int[] input) {
	    for(int i=0; i<input.length; i++) {
	        System.out.print(input[i]+" ");
	    }
	    System.out.println();
	}
}
