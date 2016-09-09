import java.util.Deque;
import java.util.ArrayDeque;

public class LPSS {

  public static int longestPalindrome(String s) {
    if(s==null) throw new NullPointerException();
    s=s.toLowerCase();
    int N=s.length();
    // memo table to keep track of the length of the longest palindromic subseqence:
    // first dimension keeps track of the starting index of each substring
    // second dimension keeps track of the end index of each substring
    int [][]memo=new int[N][N];

    // all possible of choices for the length of palindrome is 1.....N of the input string
    // we consider all substrings of size 1....N

    // BASE CASE: palindrome of size 1
    // each character is a palindromic subsequence of the input:
    for(int i=0; i<N; i++) memo[i][i]=1;

    for(int len=2; len<=N; len++) {
      for(int i=0; i<=N-len; i++) {
        int j=i+len-1; // the end index:
	if(i>j) memo[i][j]=0;
	else {
	  // if characters matched:
          if(s.charAt(i)==s.charAt(j))
	    memo[i][j]=memo[i+1][j-1]+2;
          else memo[i][j]=Math.max(memo[i+1][j], memo[i][j-1]);
	}
      }
    }
   
    return memo[0][N-1];
  }

  public static void main(String [] args) {
    System.out.println(longestPalindrome("turboventilator"));
  }

}
