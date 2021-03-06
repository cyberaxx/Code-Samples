
public class optimalBinarySearchTree {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    // frequency array of the BST:
    float [] freqArray = new float[]{0.05f, 0.4f, 0.08f,0.04f,0.1f,0.1f,0.23f};
    System.out.println(optimalBST(freqArray));
//    System.out.println(minValueInArray(freqArray));
//    System.out.println(sumFreq(freqArray, 0,freqArray.length-1));
  }
  
  public static float optimalBST (float [] freq) {
    // DP keep track of the average search time in the BST:
    float [][] DP = new float [freq.length][freq.length];

    // for optimal BST's with 2 and more nodes (iterates over subpromle (optimal substructure) size [2:n] )
    for (int len=1; len <= freq.length; len++ ) {
      // the array to keep track of all our intermidiate choices for each substring [i:j]
      float [] temp = new float [len];
      for (int i=0; i <= freq.length - len ; i++ ) {
	// the root can be i to j all inclusive
        for (int k=0; k<len ; k++) {
	  temp[k] = 0;
	  if(k-1 >= 0)
	    temp[k] += DP[i][i + k-1]; // one level, lower than DP[i:j] in the BST
	  if(k+1 < len)
	    temp[k] += DP[i + k+1][i+ len-1]; // one level, lower than DP[i:j] in the BST
	}
	// number of times we compute sumFreq captures the depth of the nodes in the
	// optimal BST
        DP[i][i + len-1] = minValueInArray(temp, 0, len-1) + sumFreq(freq, i,i+len-1);
      }
    }

    matrixRenderer(DP);
    return DP[0][freq.length-1];
  }

  public static float minValueInArray(float [] subArray, int startIndx, int endIndx) {
    float minValue;  

    if (startIndx <= endIndx)
      minValue = subArray[0];
    else
      return Float.NEGATIVE_INFINITY;;

    for(int i=startIndx; i <= endIndx; i++ )
      if (subArray[i] <= minValue)
        minValue = subArray[i];

    return minValue;

  }

  public static float sumFreq(float [] freqArray, int startIndx, int endIndx) {
    float sumFreq = 0.0f;  
    if (endIndx < startIndx)
      return Float.NEGATIVE_INFINITY;

    for(int i=startIndx; i <= endIndx; i++ )
      sumFreq += freqArray[i]; 

    return sumFreq;

  }

  public static void matrixRenderer(float [][] matrix){
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        System.out.print(matrix[i][j] + " \t ");
      }
      System.out.print("\n");
    }
    System.out.print("\n");
  }

}

