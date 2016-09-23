/*
  In computer science, edit distance is a way of quantifying how dissimilar two strings (e.g., words) 
  are to one another by counting the "minimum number of operations required to transform" one string into the other.
*/
import java.util.Deque;
import java.util.ArrayDeque;

public class EditDist {
  // this method compute the minimum number of operation ("gap" insertion) required to
  // transform x string to y
  public static int optimalAlignment(String x, String y) {
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
          c. if x[i] == y[j]: 		+0 

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
        sp[i][j]=min{sp[i][j-1]+1, sp[i-1][j]+1, sp[i-1][j-1]}

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
    int[][] memo=new int[n+1][m+1]; // all characters in x and y + gaps

    // 3a. Initialization:
    memo[0][0]=0; // optimal number of operations to algin gaps in x and y
    // 2. Optimal number of operations to algin any subsequence of x with a gap in y
    for(int i=1; i<=N, i++) memo[i][0]=i;
    // 3. Optimal number of operations to algin any subsequence of y with a gap in x
    for(int j=1; j<=M, i++) memo[0][j]=j;


    // 3b. Recurrence: carefully search all possible alginment for the one with minimal number of operations required
    for(int i=1


  }
}
