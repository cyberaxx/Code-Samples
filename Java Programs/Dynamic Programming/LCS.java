/*
  The longest common subsequence (LCS) problem is the problem of finding the longest subsequence common
  to all sequences in a set of sequences (often just two sequences).

  Related problem:
  The longest common *substring* problem is to find the longest string (or strings) that is a 
  substring (or are substrings) of two or more strings.
*/

import java.util.Scanner;
import java.util.Deque;
import java.util.ArrayDeque;

public class LCS {
  // return the length of lcs and reconstruct the lcs as well
  public static int lcs(String x, String y, Deque<Character> path) {
    /*
      Subproblems (state space): 
	prefixes of x and y (optimal substructure would be lcs of all possible subsequences of x and y)
      Choices (implicit transitions between states):
      	1. insert a gap into x (0)
      	2. insert a gap into y (0)
      	3. match (if possible) (+1)

      Initialization (initialize state values (d[v]'s)):
	Optimal value of trivial substructures:
	Source state value: 0

      Recurrence (shortest path recurrence from source [0][0] to each possible state[i][j] (edge relaxation)):
	sp[i][j]={sp[i-1][j-1]+1, or max{sp[i][j-1], sp[i-1][j]}

      Solve all subproblems in TOPOLOGICAL order of their corresponding depency (Bottom-up systematic for loop):
	Smaller size subproblem MUST get solved first, and cached the solution in memeo table
        then combine them to get the solution to larger subproblems
    */

    // consider two strings of input as two sequences of characters
    int N=x.length();
    int M=y.length();

    // memo table is 2D because the state space is 2D (each matrix entry represent an state in optimal substructure dependency DAG)
    int[][]lcs=new int[N+1][M+1]; // +1 for adding gap to both input sequences

    // 1. Intialize the source state
    lcs[0][0]=0;

    // 2. Recurrence:
    // 	  Among all possible alignments of subsequences x and y where they both are non-empty:
    // 	  find the min number of gaps required to be inserted into one or another to get them align
    for(int i=1; i<=N; i++) {
      for(int j=1; j<=M; j++) {
	// if x(i) matches y(j)
        if(x.charAt(i-1)==y.charAt(j-1))
          // 3 possible choices (implicit transition edges)
          lcs[i][j]=lcs[i-1][j-1]+1;
	else 
          // 2 possible choices (implicit transition edges)
          lcs[i][j]=Math.max(lcs[i][j-1], lcs[i-1][j]);
      }
    }

    // path reconstruction
    int r=N; int c=M;
    while(r>0 && c>0) { // while both x and y sequences are non-empty
      if(x.charAt(r-1)==y.charAt(c-1)){
	path.push(x.charAt(r-1));
	r--; c--;
      }
      else if(lcs[r-1][c]>lcs[r][c-1])
	r--;
      else
	c--;
    }

    return lcs[N][M];
  }

  public static void main(String[] args) {
    String x="snowy";
    String y="sunny";
    Deque<Character> lcs=new ArrayDeque<Character>();// empty stack of Character
    System.out.println("The minimum number of gap insertions required for alignment is: "+ lcs(x.toLowerCase(), y.toLowerCase(), lcs));
    System.out.println("The LCS is : "+ lcs);

    System.out.println();
    System.out.println();

    x="EXPONENTIAL";
    y="POLYNOMIAL";
    lcs=new ArrayDeque<Character>();// empty stack of Character
    System.out.println("The minimum number of gap insertions required for alignment is: "+ lcs(x.toLowerCase(), y.toLowerCase(), lcs));
    System.out.println("The LCS is : "+ lcs);

  }
}
