public class BruteForce {
  public static void main(String[] args) {
    String text="abacadabrabracabracadabrabrabracad";
    String pattern="abracadabra";
    System.out.println(indexOf(text, pattern));
    System.out.println(search(text, pattern));
    System.out.println(text.indexOf(pattern));
  }

  // 1. sliding the substring on the text string
  public static int indexOf(String text, String pattern) {
    // text and pattern length
    int n=text.length();
    int m=pattern.length();

    int i; // text pointer
    int j; // pattern pointer
    
    // sliding the parttern string over the text string
    for(i=0; i<=n-m; i++) {
      for(j=0; j<m; j++) {
        // advance the pattern pointer on the pattern as log as pattern.charAt(j)==text.charAt(i+j)
        // otherwise: mismatch
        if(text.charAt(i+j)!=pattern.charAt(j))
	  // advance the text pointer by one and restart the matching:
          break;
      }
      // check if the for loop on the pattern terminate because of matching all pattern chars:
      if(j==m) return i; // return the pointer to the beginning of the pattern in the text
    }

    // Otherwise: the pattern has not been found in the text
    return n;
  } // Running time: O(mn), no extra space required. Backs up on text string, and too many duplicates in text and pattern degenerate the performance

  // 2. moving text pointer and pattern pointer on text and pattern at the same time, back up on the text in case of mismatch
  public static int search(String text, String pattern) {
    int n=text.length();
    int m=pattern.length();

    // text pointer and pattern pointer:
    int i,j;

    // text pointer advances one char at the time, pattern pointer advances in case of matching chars, otherwise:
    // text pointer would back up and pattern pointer reset to 0:
    
    // initially both text and pattern pointers are set to be on the first character of the text and pattern:
    for(i=0, j=0; i<n && j<m; i++) {
      // if the current text char and current pattern char matched, advance the j pointer on the pattern and i pointer on the text
      if(text.charAt(i)==pattern.charAt(j)) j++;
      // otherwise: 1. back up on the text j steps back 2. reset j pointer on the text to 0
      else {
	// back up on the text, move i pointer j chars back on the text string:
        i=i-j;
        // reset the pattern pointer j to 0 on the pattern
        j=0;
      }
    }

    // check if the loop terminated becuase all chars of pattern have been matched:
    if(j==m) return i-m; // the beginning of the matched patterns, is i back up by M chars (the length of the substring)

    // if the loop terminated because the text has been scanned entirely without finding a match for the pattern:
    return n;
  } // Running time: O(mn), no extra space required. Backs up on text string, and too many duplicates in text and pattern degenerate the performance

}
