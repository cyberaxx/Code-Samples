public class ChainMatrix {

  public static int optimalChain(int[] dimensions) {
    // number of matrices to consider:
    int N=dimensions.length/2;
    // memo table to keep track of solutions of optimal substructure:
    int[][] mc=new int[N][N];

    // 1. Subproblem: [i:j] substring of a sequence of N matrices: O(N^2)
    // 2. Choices: for each subproblem [i:j], where to break the multiplication into two: (O(j-i))=> O(N)
    int[] choices=new int[N]; // keep track of value of the optimal substructure associated with each choice of breaking point
   
    // for increasing size of optimal substructures
    for(int len=1; len<=N; len++) {
      // for each starting matrix in the input sequence
      for(int i=0; i<=N-len; i++) {
	// specify the other end of the substring
	int j=i+len-1;
	
	// BASE CASE:
	// 1. single matrix
	if(i==j) mc[i][j]=0; // 0 cost for multiplication
	// 2. two matrices
	else if(j==i+1) mc[i][j]=cost(dimensions, i, i, j); // cost of multiplication

	// RECURRENCE:
	else {
	  // for each choice of breaking point r:
	  for(int r=0; r<=j-i; r++) {
	    if(r>0 && r<j-i) choices[r]=mc[i][r]+mc[r+1][j]+ cost(dimensions, i,r,j);
	    // check if r is the left most point: no matrix is on its left
	    else if(r==0) choices[r]=mc[r+1][j]+cost(dimensions, r,r,j);
	    // check if r is the left most point: no matrix is on its left
	    else // if(r==j-i) 
	      choices[r]=mc[i][r-1]+cost(dimensions, i,j,r);
	  }
	  mc[i][j]=min(choices, 0, j-i);
	}
      }
    }
 
    return mc[0][N-1];
  }

  public static int cost(int[] dimensions, int i, int k, int j) {
    return dimensions[2*i]*dimensions[2*k+1]*dimensions[2*j+1];
  }

  public static int min(int[] choices, int lo, int hi) {
    int min=choices[lo];
    for(int i=lo;i<=hi;i++)
      if(choices[i]<min)
	min=choices[i];
    return min;
  }

  public static void main(String[] args) {
    int [] dimensions=new int[]{50, 20, 20, 1, 1, 10, 10, 100};
    System.out.println("The optimal cost of chaining is: "+optimalChain(dimensions));
  }
}
