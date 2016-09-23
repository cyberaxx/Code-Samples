/*
  In computer science, edit distance is a way of quantifying how dissimilar two strings (e.g., words) 
  are to one another by counting the "minimum number of operations required to transform" one string into the other.
*/
import java.util.Deque;
import java.util.ArrayDeque;

public class EditDist {
  // this method compute the minimum number of operation ("gap" insertion) required to
  // transform x string to y
  public static int optimalAlignment(String x, String y, Deque<Character> alignX, Deque<Character> alignY) {
    /* Problem formulation:
       What is the alignment of x and y that requires min number of operations?
       I DON'T KNOW!!
       Solution: 
 	 1. Try them all (all possible alignment of x and y) 
	 2a. Cache all possible aligments you have compute so far (do not repeat them cause many of them are overlaping)
	 2b. Cache the minimum required number of operations for each aligments
	 3. Pick the alignment that its corresponding number of operations is minimal
    */

    /* Problem Characterization:
       1. Subproblems (DAG states):
	  All possible prefix subsequence of input sequences for both x and y
	  a. _ (empty string)(This is the source state), x1,...,xN, ...., x1.....xN (This is the target state)
	  b. _ (empty string)(This is the source state), y1,...,yM, ...., y1.....yM (This is the target state)

       2. Choices at each state (implicit edges into each DAG state)
          a. insert a gap into x: 	+1 (number of operation required) 
          b. insert a gap into y: 	+1 
          c. if x[i] != y[j]: 		+1 (mismatch) 
          d. if x[i] == y[j]: 		+0 (match)

       3a. Initialization (similar to shortest path in a DAG):
	   OPTIMAL solutions for trivial subproblems:
	   i. The source of dependency DAG of optimal subsructure states is:
              sp[0][0]: the number of required operations to align gaps which is 0
	      sp[0][0]:0 OPTIMAL
	   ii: Trivial substructures:
	   a. the optimal cost of aligning any subsequence with a gap is the size of the subsequence
	      sp[i][0]=i 	(two sequence will be transform to one and nother by inserting i gaps into x) OPTIMAL
	      sp[0][j]=j   	(two sequence will be transform to one and nother by inserting j gaps into y) OPTIMAL
 
      3b. Recurrence (edge relaxation relation similar to the shortest path problem in DAG)
	CAVEAT: Make sure all subproblems getting solved in their corresponding topological order of their dependencies DAG
        The shortest path from the start state [0][0] to state [i][j]
        sp[i][j]=min{sp[i][j-1]+1, sp[i-1][j]+1, sp[i-1][j-1] or sp[i-1][j-1]+1 }

      4. Solve the shortest path problem to find the shortest path from [0][0] to [n][m] (systematically fill up the memo table following topological order of OPsubstructure dependencies)
      5. report the value of the shortest path
      6. reconstruct the path
    */

    // 1st input sequence:
    int N=x.length();
    // 2nd input sequence:
    int M=y.length();

    // memo table which caches all states of DAG of optimal substructure and their value.
    // How many state do we need for all possible alignment? N*M
    // Implict edges transitioning from one state to another and constitute a simple path that represent an alginment 
    int[][] memo=new int[N+1][M+1]; // all characters in x and y + gaps

    // 3a. Initialization:
    memo[0][0]=0; // optimal number of operations to algin gaps in x and y
    // Optimal value for states with only one options
    for(int i=1; i<=N; i++)  memo[i][0]=i; // insert gaps into y (equivalently it means to delete i chars from x)
    for(int j=1; j<=M; j++)  memo[0][j]=j; // insert gaps into x (equivalently it means to delete j chars from y)

    // 3b. Recurrence: carefully search all possible alginments of x and y to find the one with minimal number of operations required
    for(int i=1; i<=N; i++) {
      for(int j=1; j<=M; j++) {
        // if x[i]==y[j]: matching is one the possible choice to consider:
        if(x.charAt(i-1)==y.charAt(j-1)) {
          // the shortest path to state [i][j] is min of following paths:
	  // 1. The shortest path from [0][0] to state [i-1][j] + cost of transition to [i][j] (+1 inserting a gap to y)
	  // 2. The shortest path from [0][0] to state [i][j-1] + cost of transition to [i][j] (+1 inserting a gap to x)
	  // 3. The shortest path from [0][0] to state [i-1][j-1] + cost of transition to [i][j] (0: matching x[i] and y[j])
          memo[i][j]=Math.min(Math.min(memo[i][j-1]+1, memo[i-1][j]+1), memo[i-1][j-1]);
        }
        // in case of mismatches we have to find out what is the optimal gap insertion strategy into x and y
        else {
          // the shortest path to state [i][j] is min of following paths:
	  // 1. The shortest path from [0][0] to state [i-1][j] + cost of transition to [i][j] (+1 inserting a gap to y)
	  // 2. The shortest path from [0][0] to state [i][j-1] + cost of transition to [i][j] (+1 inserting a gap to x)
	  // 3. The shortest path from [0][0] to state [i-1][j-1] + cost of transition to [i][j] (1: missmatch x[i] and y[j])
          memo[i][j]=1+Math.min(Math.min(memo[i][j-1], memo[i-1][j]), memo[i-1][j-1]);
        }
      }
    }

    // path reconstruction:
    // look up the memo table and reconstruct the alignments:
    int r=N; int c=M;
    while(r>0 && c>0) {
      if(x.charAt(r-1)==y.charAt(c-1)) {
        if(memo[r-1][c-1]<=memo[r][c-1]+1 && memo[r-1][c-1]<=memo[r-1][c]+1) {
	  alignX.push(x.charAt(r-1));
	  alignY.push(y.charAt(c-1));
	  r--; c--;
        }
        else if(1+memo[r][c-1]<1+memo[r-1][c] && 1+memo[r][c-1]<memo[r-1][c-1]) {
	  alignX.push('_');
	  alignY.push(y.charAt(c-1));
	  c--;
        }
        else {
   	  alignX.push(x.charAt(r-1));
	  alignY.push('_');
	  r--;
        }
      }
      else {
        if(1+memo[r-1][c-1]<1+memo[r][c-1] && 1+memo[r-1][c-1]<1+memo[r-1][c]) {
	  alignX.push(x.charAt(r-1));
	  alignY.push(y.charAt(c-1));
	  r--; c--;
        }
        else if(1+memo[r][c-1]<1+memo[r-1][c] && 1+memo[r][c-1]<1+memo[r-1][c-1]) {
  	  alignX.push('_');
	  alignY.push(y.charAt(c-1));
	  c--;
        }
        else {
	  alignX.push(x.charAt(r-1));
	  alignY.push('_');
	  r--;
        }
      }
    }

   // The value of the shortest path from [0][0] state to the target state [N][M]:
   // the minimum number of operations required to algin x and y is
   return memo[N][M]; 
  }

  public static void main(String[] args) {
    String x="snowy";
    String y="sunny";
    Deque<Character> alignX=new ArrayDeque<Character>();
    Deque<Character> alignY=new ArrayDeque<Character>();
    System.out.println("The minimum number of operations required for alignment is: "+ optimalAlignment(x.toLowerCase(), y.toLowerCase(), alignX, alignY));
    System.out.println("X alignment is: "+ alignX);
    System.out.println("Y alignment is: "+ alignY);

    System.out.println();
    System.out.println();

    x="EXPONENTIAL";
    y="POLYNOMIAL";
    alignX=new ArrayDeque<Character>();
    alignY=new ArrayDeque<Character>();
    System.out.println("The minimum number of operations required for alignment is: "+ optimalAlignment(x.toLowerCase(), y.toLowerCase(), alignX, alignY));
    System.out.println("X alignment is: "+ alignX);
    System.out.println("Y alignment is: "+ alignY);

  }
}
