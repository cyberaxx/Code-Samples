/*
  Objective function: Minimze the average search time of N nodes in the BST (binary tree with nodes are in symmetric order wrt to their associated keys)
  	Search time:	
		T: 1 + depth(node in the BST)
	Frequency of search:	
		F: A nonegative (>=0) real valued number associated to each node of the bst (F1, ...., FN)
 	Average search time:
		C: sum(F1.T1 + .... + FNTN)

  Would like to find out optimal BST formation (preserve the symmetric order of nodes in BST) and also minimizes the the Average Search Time for keys in the bst
*/
import java.util.Deque;
import java.util.ArrayDeque;

public class OptimalBST {

  /*
      DP approach:
	1. Identifying subproblem:
		Among all VALID formations of N nodes we want to find a ROOT and break a bst into LEFT BST subtree, ROOT, and RIGHT BST subtree
		for all possible nodes.
		There would be N possible choices to break the N nodes into two groups (say break nodes at node r, then 1...r-1, r, r+1....N would be 3 partitions of nodes)
		and each group of nodes would constitute a suproblem of smaller size that has to be solved.

		In order to be able to characterize an optimal substructure (optimal subproblem of a given problem), the subproblems of the original problem, are NOT
		neither the prefix of input sequence (1...N nodes) nor its suffix. 

		Each optimal substructure rather, is the SUBSTRING of a sequence of nodes (1....N), hence the number of subproblem for each choice of root is at most N^2

	2. Choices:
		For each substring of a sequence of nodes, we would have N options/choice to examine for a possible root of the BST subtree

	3. Recurrence:
		What we want to accomplish is to build a BST using a sequence N nodes from 1...N such that the average search time of the resulting bst is minimal:
		
		for any chioce of i and j such that 1 <= i <= j <= N we would like to solve and memoize (cache) the following recursion:
		memo[i][j]=min{memo[i][r-1]+memo[r+1][j]+sum(p, i, j)} for all choices of r within [i,j] range

  */

  public static double minSearchTime(double[] frequencies, Deque<Integer> path) {
    // assuming nodes labeled 1....N are in sorted order of their associated keys (such that 1<2<3<.....<N)
    int N=frequencies.length;

    // memo table of that cache the minimal average search time for bst constructed by node i...j
    double [][] st=new double[N][N]; 

    // BASE CASE: 1 node bst
    // bst's that are constructed by considering node i in a seqence of nodes 0.....N-1
    for(int i=0; i<N; i++) st[i][i]=frequencies[i];

    // NOTE: the depth of each node implictly characterized by the number of time sum(p, i, j) getting calculated
    // consider all subproblems in increasing size of the subproblem:
    // 1. one node: each node from 1....N can be a root of bst with only one node
    // 2. two nodes: for each choices 2 nodes from nodes 1...N each of them can be a root and other one a child (memoize the one that minimizes bst's average search time)
    // 3. three nodes and .... N would follow the same structure


    for(int len=2; len<=N; len++) { // this loop increase the size of the subproblem (considering substrings [i:i+len-1] of node sequence 0....N-1
      for(int i=0; i+len<=N; i++) { // node indexes in the sequence of N nodes from 0....N-1
	// the average search time for bst constructed from nodes i....i+n-1: 
	// 0. for any root node within [i:i+len-1]:
	//   1. optimal search time for its left subtree (if exists such a subtree)
	//   2. optimal search time for its right subtree (if exists such a subtree)
	//   3. cost of choice of the root
	//   4. pick the formation among all possible formation that makes the total cost minimal

	int j=i+len-1;
    
        // for all choice of the root: memoize the search time
        double [] choices=new double[len];

        for(int r=0; r<=j-i; r++) {
	  // check if both left and right subtrees exist:
	  if(r-1>=0 && r+1<=j-i)
	    choices[r]=st[i][i+r-1]+st[i+r+1][j];
	  else if(r-1<0) // left subtree does not exist
	    choices[r]=st[i+r+1][j];
	  else if(r+1>j-i) // right subtree does not exist
	    choices[r]=st[i][i+r-1];
	  else 
	    choices[r]=0;
	}

        st[i][j]=min(choices)+sum(frequencies, i, j);

      }
    }

    return st[0][N-1];
  }

  // helper method to compute the sum of frquencies of a given sequences of nodes in bst
  private static double sum(double[]frequencies, int lo, int hi) {
    double sum=0;
    for(int i=lo; i<=hi; i++) sum+=frequencies[i];
    return sum;
  }
  private static double min(double[]choices) {
    double min=choices[0];
    for(int i=0; i<choices.length; i++) 
      if(choices[i]<min) min=choices[i];
    return min;
  }
  public static void main(String [] args) {
    double [] freqArray = new double[]{0.05, 0.4, 0.08,0.04,0.1,0.1,0.23};
    Deque<Integer> path=new ArrayDeque<Integer>(); // empty stack of integers
    System.out.println(minSearchTime(freqArray, path));
    System.out.println(path);
  }
}
