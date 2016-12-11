import java.util.*;

public class MaxRectBinary {
  public static void main(String[] args) {
  }
	public static void maxArea(int a[][],int m,int n){
        //add code here.
        int maxArea=0;
        int area=0;
        int[]hist=new int[a[0].length]; /* array of columns */
        /* for each row, compute the maxArea hist builds by colum ones incrementally: */
        for(int i=0; i<a.length; i++) {
            /* populate the his array: */
            for(int j=0; j<a[i].length; j++) {
                if(a[i][j]==1)  hist[j]+=a[i][j];
                else hist[j]=0;
            }
            /* compute the max rectangle in the histogram: */
            area=maxAreaHist(hist); /* O(n) time and linear extra space */
            /* compare the area with the maxArea and update the maxArea if required: */
            if(maxArea<area) maxArea=area;
        }
        
        System.out.println(maxArea);
    }
    
    private static int maxAreaHist(int[] hist) {
        int maxArea=0;
        int area=0;
        int len=hist.length;
        Deque<Integer> stack=new ArrayDeque<Integer>(); /* empty stack that keeps track of index of each column */
        
        /* loop over all columns of the histogram: */
        for(int i=0; i<hist.length; i++) {
            int current=hist[i]; /* the height of the current column */
            /* while the height of 
               the current column is (strictly) less than the current height 
               and stack is not empty, keep poping from the stack and compute the area
               contributed by the poped item: */
               while(!stack.isEmpty()&&current<hist[stack.peek()]) {
                   /* pop the column index from the stack: */
                   int j=stack.pop();
                   /* calculate its contribution to the area: */
                   /* case 1: if stack is empty: */
                   if(stack.isEmpty()) area=hist[j]*i; /* the jth column was the shortest column among all visited so far */
                   /* case 2: is stack is not empty: the jth colum contribution depends on far back the top index is: */
                   else area=hist[j]*(i-1-stack.peek());
                   /* compare the area to maxArea: */
                   if(maxArea<area) maxArea=area;
               }
            /* push the column index with height greater or equal to the stack top into the stack: */
            stack.push(i);
        }
        
        /* as long as there are still items in the stack: */
        while(!stack.isEmpty()) {
            /* pop the top of the stack: */
            int j=stack.pop();
            /* compute its contribution to the area: */
            if(stack.isEmpty()) area=hist[j]*len;
            else area=hist[j]*(len-stack.peek()-1);
            /* compare the area to maxArea: */
            if(maxArea<area) maxArea=area;
        }
        return maxArea;
    }
}
