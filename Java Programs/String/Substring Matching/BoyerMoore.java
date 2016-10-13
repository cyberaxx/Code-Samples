public class BoyerMoore {

  // Match the pattern to the string by sliding pattern on the string 
  // and compare the pattern with the strin g from right to left
  public static int indexOf(String text, String pattern, int R) {
    int n=text.length();
    int m=pattern.length();
    // compute the right most index on each symbol of alphabet in the pattern string, -1 otherwise
    int[] right=right(pattern, R); // takes O(R) extra stapce, and O(R+M) time
    int skip; // how many char to skip from the text string in case of mismatch

    // Scan one pass over the text string with no back up
    for(int i=0; i<=n-m; i+=skip) {
      skip=0; // reset the skip to 0
      for(int j=m-1; j>=0; j--) {
        // if the pattern char does not matched the text char:
        if(pattern.charAt(j)!=text.charAt(i+j)) {
          // skip on the text string at least by one char
          skip=Math.max(1, j-right[text.charAt(i+j)]);
        }
      }

      // check out if the inner for loop terminated because the pattern got matched:
      if(skip==0) return i;
    }
    
    return n;    
  }

  // Compute the right most index of each symbol of R-alphabet in pattern string, -1 otherwise:
  private static int[] right(String pattern, int R) {
    int[] right=new int[R];

    // initialize the right array:
    for(int c=0; c<R; c++) right[c]=-1;
  
    // find the right most index of each char in pattern string
    for(int j=0; j<pattern.length(); j++) right[pattern.charAt(j)]=j;

    return right;
  }

  public static void main(String[] args) {
    String text="abacadabrabracabracadabrabrabracad";
    String pattern="abracadabra";
    System.out.println(indexOf(text, pattern, 256));
    System.out.println(text.indexOf(pattern));
  }
}
