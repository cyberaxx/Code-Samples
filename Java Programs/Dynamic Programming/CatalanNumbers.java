/*

  In combinatorial mathematics, the Catalan numbers form a sequence of 
  natural numbers that occur in various counting problems, often involving 
  recursively-defined objects.

  The Catalan numbers satisfy the recurrence relation:
  c[0]=1
  c[n+1]=SUM(c[i]*c[n-i]) (For i=0 to n) for n>=0


  Catalan Number Applications:
  1. C[n] counts the number of expressions containing n pairs of parentheses which are correctly matched.
  2. C[n] is the number of different ways n + 1 factors can be completely parenthesized (or the number of ways of associating n applications of a binary operator).
  3. C[n] is the number of rooted binary trees with n internal nodes (n + 1 leaves or external nodes).

*/

public class CatalanNumbers {

  // DP implementation of Catalan recurrence (Similar to Fibonacci)
  private static int catalan(int n) {
    int[] c=new int[n+1];

    // base case: c[0]=1;
    c[0]=1;
    c[1]=1;

    // iterate over all possible n's strating from n+1
    for(int j=2; j<=n; j++) {
      // sum formulations
      for(int i=0; i<=j-1; i++) {
	c[j]=c[j]+c[i]*c[j-i-1];
      }
    }
    return c[n];
  }

  public static void main(String[] args) {

  }


}
