/*

  In computer science, the longest increasing subsequence problem is to find a subsequence of a given sequence
  in which the subsequence's elements are in sorted order, lowest to highest, and in which the subsequence is as long as possible. 

  This subsequence is NOT necessarily contiguous, or UNIQUE. 

  The longest increasing subsequence problem is solvable in time O(n log n), where n denotes the length of the input sequence.

  Quadratic implementations:
    1. Reduction to LCS
    2. Reduction to Longest Path in a DAG

  ****Efficient NlogN implementation (clever approach using binary search)

  Related problems:
    1. Longest Decreasing Subsequence (similar solution to LIS only negate the numbers in the sequence)
    2. Patience Sort
*/

public class LISS {

  public static int longestIncreasinSS(int [] seq) {
    // number of item in the sequence:
    int N=seq.length;
    return N;
  }

  public static void main(String [] args) {
    System.out.println(longestIncreasinSS(new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15}));
  }

}
