/*
Palindrome checker. 
Write a program that reads in a sequence of strings and checks whether it constitutes a palindrome. 
Ignore punctuation and spaces and case. (A MAN, A PLAN, A CANAL - PANAMA). Use one stack and one queue.
*/

import java.util.Deque;
import java.util.ArrayDeque;

public class PalindromeChecker {
  public static boolean palindrome(String input) {
    Deque<Character> stack=new ArrayDeque<Character>();
    Deque<Character> queue=new ArrayDeque<Character>();

    // convert to lowercase:
    String string=st.trim().toLowerCase();

    // Read string from input:
    for(Character ch:string) {
      // ignore punctuation and space
      if(ch==' ' || ch==',' || ch=='.' || ch==';' || ch=='-' || ch=='!' || ch=='?') continue;
        stack.push(ch);
	queue.offer(ch);
    }

    while(!stack.isEmpty() && !queueu.isEmpty){
    
    }
    
  }

  public static void main(String[] args) {
    System.out.println(palindrome("A MAN, A PLAN, A CANAL - PANAMA"));
  }

}
