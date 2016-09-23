/*
  The longest common subsequence (LCS) problem is the problem of finding the longest subsequence common
  to all sequences in a set of sequences (often just two sequences).

  Related problem:
  The longest common *substring* problem is to find the longest string (or strings) that is a 
  substring (or are substrings) of two or more strings.
*/
public class LCS {
  /* Similar to edit distance problem:
     1. Find the optimal alignment (alignment with min number of gap insertions)
     2. Count the number of non-gap characters in the alginment
     3. Difference compare to the alignment problem, LCS does NOT allow mismatch (insert gap into x or y in case of mismatch) 
  */

  // return the length of lcs and reconstruct the lcs as well
  public static int lcs(String x, String y) {

    return 0;
  }
}
