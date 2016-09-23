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
    // 1st input sequence:
    int N=x.length();
    // 2nd input sequence:
    int M=y.length();

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
 
    */
  }
}
