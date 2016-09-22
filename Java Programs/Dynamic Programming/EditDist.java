/*
  In computer science, edit distance is a way of quantifying how dissimilar two strings (e.g., words) 
  are to one another by counting the "minimum number of operations required to transform" one string into the other.
*/
import java.util.Deque;
import java.util.ArrayDeque;

public class EditDist {
  // this method compute the minimum number of operation ("gap" insertion) required to
  // transform x string to y
  public static int optimalAlignment(String x, String y) {
    // 1st input sequence:
    int N=x.length();
    // 2nd input sequence:
    int M=y.length();

    /* Problem formulation:
       What is the alignment of x and y that requires min number of operations?
       I DON'T KNOW!!
       Solution: 
 	 1. Try them all (all possible alignment of x and y) 
	 2a. Cache all possible aligments you have compute so far (do not repeat them cause many of them are overlaping)
	 2b. Cache the minimum required number of operations for each aligments
	 3. Pick the alignment that its corresponding number of operations is minimal
    */

    /*
  }
}
