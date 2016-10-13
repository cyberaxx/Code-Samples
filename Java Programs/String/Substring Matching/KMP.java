public class KMP {
  // instance variables:
  private int R; // radix of the alphabet text and pattern are defined on
  private String text; // text string
  
  // Constructor:
  public KMP(String text, int R) {
    this.text=text;
    this.R=R;
  }

  // API: search behaviour:
  public int indexOf(String pattern) {
    // 1. create a dfa that accepts pattern string among all string based on the R-alphabet:
    int[][]dfa=dfa(pattern, R); // takes O(RM) time and space to create the dfa

    int n=text.length(); // text length
    int m=pattern.length(); // pattern length

    // 2. simulate the text string on the dfa constructed from the pattern (variant of brute-force algorithm without backing up on the text string)
    int i; // pointer on the text string
    int j; // pointer on the j state of the dfa

    // scan over the text string, one pass with no back up, simulate the dfa of pattern string
    for(i=0, j=0; i<n && j<m; i++) {
      // only advance the text pointer: pattern's dfa would decide how j pointer has to get updated after each char scan from the text
      j=dfa[j][text.charAt(i)];
    }

    // check if the for loop terminated because the dfa hits the match state j==m:
    if(j==m) return i-m;

    // otherwise: not found
    return n;
  }

  // Construct dfa (its corresponding state-transition table) such that among all strings defined on R-alphbet, only accepts the pattern string
  private int[][] dfa(String pattern, int R) {
    // number of the dfa state is the number of chars in the pattern string:
    int m=pattern.length();

    // each dfa has a start and finish state, and at each state there must be a transition defined for all symbols of the alphabet
    // the state-transition matrix is a representation of such a dfa
    int[][] dfa=new int[m][R]; // a dfa with m state (the accpetance state m+1 is actually implicit), and R chars

    // initialize the first state, moving from state 0, empty string, and match the first character from the pattern:
    dfa[0][pattern.charAt(0)]=1; // matching state -> note that j represents the number of matched chars to the pattern so far

    /*
      There are two types of transitions: 
      1. match transitions -> moving forward (advancing in the dfa toward the acceptance state)
      2. mismatch transitions -> backing up on the dfa
      3. there is no backing up on the text, because dfa capture up to m last char that has been appeared on the text (dfa remembers)
    */

     int x; // the back up state: in case of mismatches, x is the state such that if we back up on dfa and simulate pattern[1...j-1], dfa will end up at state x
     int j; // pointer on the j state (j matched chars)

     for(x=0, j=1; j<m; j++) {
       // 1. Mismatch transition
       // for mismatch chars (among all chars of the R-alphabet)
       for(int c=0; c<R; c++) {
         // copy over the transition from the x state (re-simulating pattern [1...j-1] and transition dfa[x][c]
         dfa[j][c]=dfa[x][c];
       }
       // 2. Match transition: move one state forward
       dfa[j][pattern.charAt(j)]=j+1; // Note: pattern.charAt(j) is the j+1 char of the pattern string (after matching j chars, if next char matched, then j+1 char matched)

       // 3. updating the x transition by j char of the pattern
       x=dfa[x][pattern.charAt(j)];
    }

    // return the transition matrix:
    return dfa;
  }

  public static void main(String[] args) {
    String text="abacadabrabracabracadabrabrabracad";
    String pattern="abracadabra";
    KMP kmp=new KMP(text, 256); // on extended ascii alphabet

    System.out.println(kmp.indexOf(pattern));
    System.out.println(text.indexOf(pattern));
  }
}
