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
  /* Similar to edit distance problem:
     1. Find the optimal alignment (alignment with min number of gap insertions)
     2. Count the number of non-gap characters in the alginment
     3. Difference compare to the alignment problem, LCS does NOT allow mismatch (insert gap into x or y in case of mismatch) 
  */

  // return the length of lcs and reconstruct the lcs as well
  public static int lcs(String x, String y, String str) {
    /*
      Subproblems (state space): 
	prefixes of x and y (optimal substructure would be lcs of all possible subsequences of x and y)
      Choices (implicit transitions between states):
      	1. insert a gap into x (+1)
      	2. insert a gap into y (+1)
      	3. match (if possible) (0)

      Initialization (initialize state values (d[v]'s)):
	Optimal value of trivial substructures:
	1. Source state value: 0
	2. Shortest path from [0][0] to [i][0]: i (only one option: inserting gas into x)
	3. Shortest path from [0][0] to [0][j]: j (only one option: inserting gas into y)

      Recurrence (shortest path recurrence from source [0][0] to each possible state[i][j] (edge relaxation)):
	sp[i][j]=min{sp[i-1][j-1], sp[i][j-1]+1, sp[i-1][j]+1}

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
    // Optimal value for states with only one options
    for(int i=1; i<=N; i++)  lcs[i][0]=i; // insert gaps into y (equivalently it means to delete i chars from x)
    for(int j=1; j<=M; j++)  lcs[0][j]=j; // insert gaps into x (equivalently it means to delete j chars from y)

    // 2. Recurrence:
    // 	  Among all possible alignments of subsequences x and y where they both are non-empty:
    // 	  find the min number of gaps required to be inserted into one or another to get them align
    for(int i=1; i<=N; i++) {
      for(int j=1; j<=M; j++) {
	// if x(i) matches y(j)
        if(x.charAt(i)==y.charAt(j))
          // 3 possible choices (implicit transition edges)
          lcs[i][j]=Math.min(Math.min(lcs[i][j-1]+1, lcs[i-1][j]+1), lcs[i-1][j-1]); // pick the minimal OPTIMAL substructure value to assign it to state [i][j]  
	else 
          // 2 possible choices (implicit transition edges)
          lcs[i][j]=1+Math.min(lcs[i][j-1], lcs[i-1][j]); // pick the minimal OPTIMAL substructure value to assign it to state [i][j]
      }
    }

    // path reconstruction
    int r=N; int c=M; int count=0;
    StringBuilder s=new StringBuilder();
    while(r>0 && c>0) { // while both x and y sequences are non-empty
      if(lcs[r-1][c-1]<lcs[r-1][c]+1 && lcs[r-1][c-1]<lcs[r][c-1]+1) {
	count++;
	s.append(x.charAt(r-1));
	r--; c--;
      }
      else if(lcs[r-1][c]+1<lcs[r-1][c-1] && lcs[r-1][c]<lcs[r][c-1])
	r--;
      else
	c--;
    }

    str=new String(s.reverse());
    return count;
  }

  public static void main(String[] args) {
  }
}
