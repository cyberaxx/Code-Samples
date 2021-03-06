
public class longestPalindromicSubSeq {

  public longestPalindromicSubSeq() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    char[] char_array= new char []{'a','b','c','d','c','a'};
    System.out.println(longestPalindromicSeq ("AABCDEBAZ".toCharArray()));
    System.out.println("\n\n");
    System.out.println(longestPalindromicSeq (char_array));

  }

  public static int longestPalindromicSeq (char[] seq) {
    // DP keep track of the length of the longest palindrome subseq in
    // the input string:
    int [][] DP = new int [seq.length][seq.length];

    // for palindrome of size 2 and bigger:
    // plen keeps track of the size of the palindrome.
    for (int plen=1; plen <= seq.length; plen++ ) {
      for (int i=0; i <= seq.length - plen ; i++ ) {

        int j = i + plen - 1;
	
	// for palindrome of size 1:
	if (i==j) DP[i][j] = 1;

        // palindrome of size 2
        else if (plen==2 && seq[i]==seq[j]) DP[i][j] = 2;

        // palindrome of size > 2
        else if (seq[i]==seq[j])
          DP[i][j] = 2+DP[i+1][j-1];
        else
          DP[i][j] = Math.max(DP[i][j-1],DP[i+1][j]);
      }
    }

    matrixRenderer(DP);
    return DP[0][seq.length - 1];
  }
  
    public static void matrixRenderer(int [][] matrix){
      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix.length; j++) {
        System.out.print(matrix[i][j] + "  ");
      }
        System.out.print("\n");
    }
      System.out.print("\n");
    }
}

