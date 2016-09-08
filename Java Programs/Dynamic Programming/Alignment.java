/*
  In bioinformatics, a sequence alignment is a way of arranging the sequences of DNA, RNA, or protein to identify regions
  of similarity that may be a consequence of functional, structural, or evolutionary relationships between the sequences.

  Aligned sequences of nucleotide or amino acid residues are typically represented as rows within a matrix. 
  Gaps are inserted between the residues so that identical or similar characters are aligned in successive columns.

  Sequence alignments are also used for non-biological sequences, such as calculating the edit distance cost between
  strings in a natural language or in financial data.
*/

import java.util.List;
import java.util.ArrayList;

public class Alignment {

  // penalty types: 1. insert gap to x (mismatch), 2. insert gap to y (mismatch), 3. match
  public static double optimalAlignment(String x, String y, double[] penalties, List<Character> alignX, List<Character> alignY) {
    // two sequences of chars with length M and N:
    int M=x.length();
    int N=y.length();

    // memo table to cache the optimal aligment for subsequences:
    double[][]memo=new double[M+1][N+1];

    /* considerind sequence of chars from 1...M and 1...N for each subseuqence (prefix of the input sequence) find the optimal
       optimal alignment, the solution for the actual problem would be at memo[M][N] where all possible aligment of x and y has been examined:
       Choices: For rach subsequence there are 3 possible choice for alignement:
       in case of mismatch:
	 1. add a gap to seq x and paying the penalty associated with it
	 2. add a gap to seq y and paying the penalty associated with it
       match:
	 3. find the optimal alignment for subsequences of x and y with one less char
    */

    // Base cases: 
    // 1. aligning subsequences of x with empty y sequence
    for(int i=0; i<=M; i++) memo[i][0]=i*penalties[1]; // insert a gap in y sequence
    // 2. aligning subsequences of y with empty x sequence
    for(int j=0; j<=N; j++) memo[0][j]=j*penalties[0]; // insert a gap in x sequence

    // Recurrence:
    for(int i=1; i<=M; i++) {
      for(int j=1; j<=N; j++) {
	 memo[i][j]=Math.min(Math.min(memo[i-1][j-1]+penalties[2], memo[i][j-1]+penalties[0]), memo[i-1][j]+penalties[1]); 
      }
    }

    // path reconstruction:
    int r=M; int c=N;
    while(r>0 && c>0) {
      if(memo[r][c]==memo[r-1][c-1]+penalties[2]) {alignX.add(x.charAt(r-1)); alignY.add(y.charAt(c-1)); r--; c--;}
      else if(memo[r][c]==memo[r][c-1]+penalties[2]) {alignX.add('_'); alignY.add(y.charAt(c-1)); c--;}
      else {alignX.add(x.charAt(r-1)); alignY.add('_'); r--;}
    }
  
    return memo[M][N];
  }

  public static void main(String[] args) {
    String x="CTGATCCTTGCT";
    String y="AGAGCTC";
    double[] penalties={0.12, 0.134, 0.1122};   

    List<Character> alignX=new ArrayList<Character>();// empty list of Character
    List<Character> alignY=new ArrayList<Character>();// empty list of Character

    System.out.println("Min penalty to align x and y is: "+ optimalAlignment(x,y,penalties, alignX, alignY));
    System.out.println("alignX is: "+ alignX);
    System.out.println("alignY is: "+ alignY);

  }

}
