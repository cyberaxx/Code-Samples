import java.util.Deque;
import java.util.ArrayDeque;

public class LIS {
    public static int lis(int[] nums, Deque<Integer> path) {
        if(nums==null||nums.length==0) return 0;
        
        // value array
        int[]lis=new int[nums.length];
        // parent pointer array
        int[]edgeTo=new int[nums.length];
        
        // Base case: 1-element solution:
        for(int i=0; i<lis.length; i++) {
            lis[i]=1;
            edgeTo[i]=i;
        }
        
        // Recurrence BU:
        for(int i=1; i<nums.length; i++) {
            // all possible predecessors:
            for(int j=0; j<i; j++) {
                // if there exist an edge:
                if(nums[j]<nums[i]) {
                    // relax:
                    if(lis[j]+1>lis[i]) {
                        lis[i]=lis[j]+1;
                        edgeTo[i]=j;
                    }
                }
            }
        }
        
        int maxIndex=max(lis);
        int x=maxIndex;
        while(edgeTo[x]!=x) {
          path.push(nums[x]);
          x=edgeTo[x];
        }
        path.push(nums[x]);
        
        return lis[maxIndex];
        
    }
    
    private static int max(int[] input) {
        int max=0;
        for(int i=1; i<input.length; i++)
            if(input[i]>input[max])
                max=i;
        return max;
    }
  
  public static void main(String [] args) {
	  Deque<Integer> path=new ArrayDeque<Integer>();
    System.out.println(lis(new int[]{5,2,8,6,3,6,9}, path));
    System.out.println(path);
    
    System.out.println();
    
    path=new ArrayDeque<Integer>();
    System.out.println(lis(new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15}, path));
    System.out.println(path);
  }
}
