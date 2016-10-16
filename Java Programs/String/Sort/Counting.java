public class Counting {
  public static void main(String[] args) {
    char[] input="zzzaksndasfdskfjsdlkgfeASSDA".toCharArray();
    countingSort(input,256);
    for(int i=0; i<input.length; i++) {
      System.out.print(input[i]+" ");
    }
    System.out.println();
  }

  /* Key-index counting sort on arrays of symbols of aplphabet of radix R*/
  public static void countingSort(char [] input, int R) {
    // create a count array:
    int[] count=new int[R+1];

    // 1. count the frequency of each index key in the input array
    // each element of the input array is an index in the count array (indexes of count array are implicitly sorted)
    for(int i=0; i<input.length; i++) {
      count[input[i]+1]++; // input[i]+1 to accurately compute number of key-indexes less than key-index "r" in the count array
    }

    // 2. accumulate counts for each key index to find the rightful position of key-index in the input array: r:[0 R-1] all possible key-indexes
    for(int r=0; r<R; r++) {
      count[r+1]=count[r+1]+count[r]; // for each key index, the count[r] represent number of key-indexes in the input array that has to appear before key-index r
    }

    // 3. create an aux array to put all key-indexes in it in sorted order:
    char[] aux=new char[input.length];

    // 4. put key-indexes in the aux array in sorted order and update the count[r] (the index of next appearance of the key-index "r" in the array)
    for(int i=0; i<aux.length; i++) {
      aux[count[input[i]]++]=input[i];
    }

    // 5. copy the sorted array back to the original array
    System.arraycopy(aux, 0, input, 0, aux.length);

  }

}
