public class LongestBitonicSubsequence {

  public static void main(String[] args) {
    System.out.println(lbs(new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5,
              13, 3, 11, 7, 15}));
    System.out.println(lbs(new int[]{1, 11, 2, 10, 4, 5, 2, 1})); /* Output: 6 (A Longest Bitonic Subsequence of length 6 is 1, 2, 10, 4, 2, 1) */
    System.out.println(lbs(new int[]{12, 11, 40, 5, 3, 1})); /* Output: 5 (A Longest Bitonic Subsequence of length 5 is 12, 11, 5, 3, 1) */
    System.out.println(lbs(new int[]{80, 60, 30, 40, 20, 10})); /* Output: 5 (A Longest Bitonic Subsequence of length 5 is 80, 60, 30, 20, 10) */
  }
  
  /* this method returns the maximal length of longest bitonic subsequence in a given sequence: */ 
  private static int lbs(int[] input) {
    /* reverse of the input array: */
    int[] reverse=reverse(input);
    /* tables of optimal solution to subproblems: */
    int[] lisLeft=new int[input.length]; /* table of optimal lis length from left to right */  
    int[] lisRight=new int[input.length]; /* table of optimal lis length from right to left */  
    
    /* tables of optimal (maximal) lis length for input array and its reverse: */    
    lis(input, lisLeft);
    lis(reverse, lisRight);
    
    /* length of bitonic subsequences ends at position i: 0 to n-1 */
    int[] bitonic=new int[input.length];
    int max=0; /* the length of maximal bitonic subsequence: */
    for(int i=0; i<bitonic.length; i++) {
      bitonic[i]=lisLeft[i]+lisRight[i]-1; /* at the PEAK point (peak node has been counted twice) */ 
      if(bitonic[i]>max) max=bitonic[i];
    }
    return max;
  }

  /* this method compute the length of longest increasing subsequence in a given sequence: */   
  private static void lis(int[] input, int[] lis) {
    /* 1. Initialization step: */
    for(int i=0; i<input.length; i++) {
      lis[i]=1; /* length of longest increasing subsequence starts and ends at position i: */
    }
  
    /* 2. Relaxation step: in topological order of subproblem states: */
    for(int i=1; i<input.length; i++) {
      /* for a subsequence start at 0 and end at index i: */
      for(int j=0; j<i; j++) {
        /* if there exists an implicit edge between j to i */
        if(input[j]<input[i]) {
          /* relax that edge: */
          if(lis[i]<lis[j]+1) {
            lis[i]=lis[j]+1;
          }
        }
      }
    }
  }
  
  /* this method creates a reverse of the given input array: */ 
  private static int[] reverse(int[] input) {
    int[] reverse=new int[input.length];    
    for(int i=0; i<input.length; i++) {
      reverse[i]=input[input.length-i-1];
    }
    return reverse;
  }
}
