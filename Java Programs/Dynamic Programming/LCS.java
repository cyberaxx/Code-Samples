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
    // consider two strings of input as two sequences of characters
    int N=x.length();
    int M=y.length();

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

      Recurrence:

    */

    return 0;
  }

  public static void main(String[] args) {
  }
}
