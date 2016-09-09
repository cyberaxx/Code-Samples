import java.util.Deque;
import java.util.ArrayDeque;

public class PalindromeChecker {

  public static boolean isPalindrome(String s) {
    // trivial cases: empty string and a single character string:
    if(s==null) return true;
    if(s.length()<2) return true;

    Deque<Character> stack=new ArrayDeque<Character>(); // empty stack of characters
    Deque<Character> q=new ArrayDeque<Character>(); // empty queue of characters

    for(int i=0; i<s.length(); i++) {
      stack.push(s.charAt(i)); // push the current character to the stack
      q.offer(s.charAt(i)); // enqueue the current character to the queue
    }

    // compare characters:
    while(!q.isEmpty() && !stack.isEmpty()) {
      // remove the head of queue and top of the stack and compare them
      // with each other:
      if(q.poll()!=stack.pop())
	return false;
    }
    return true;
  }

  // test client:
  public static void main(String[] args) {
    System.out.println(isPalindrome("absmcdcmsba"));
    System.out.println(isPalindrome("aaaaaaaaaaaaaaaaaa"));
    System.out.println(isPalindrome("a"));
    System.out.println(isPalindrome(""));
  }

}
