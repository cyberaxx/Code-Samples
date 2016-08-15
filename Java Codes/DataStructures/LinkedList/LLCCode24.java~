import java.util.LinkedList;
import java.util.Arrays;

/*
You have two numbers represented by a linked list, where each node contains a single
digit. The digits are stored in reverse order, such that the 1’s digit is at the head of
the list. Write a function that adds the two numbers and returns the sum as a linked
list.
EXAMPLE
Input: (3 -> 1 -> 5) + (5 -> 9 -> 2)
Output: 8 -> 0 -> 8
______________________
*/

public class LLCCode24 {

  public static LinkedList<Integer> sum (LinkedList<Integer> firstNumber, LinkedList<Integer> secondNumber) {
    if(firstNumber.isEmpty() || secondNumber.isEmpty()) return null;
    LinkedList<Integer> result = new LinkedList<Integer>();
    int sum = 0;
    int carry = 0;
    while (!firstNumber.isEmpty() && !secondNumber.isEmpty()) {
      sum = (firstNumber.pollFirst() + secondNumber.pollFirst() + carry);
      carry = sum/10;
      // add it to the result:
      result.offerLast(sum%10);
    }

    if(firstNumber.isEmpty()) {
      while(!secondNumber.isEmpty()) {
        if(carry>0) {
           sum=carry+secondNumber.pollFirst();
           carry=sum/10;
           result.offerLast(sum%10);
        }
        else result.offerLast(secondNumber.pollFirst());
      }
    }

    if(secondNumber.isEmpty()) {
      while(!firstNumber.isEmpty()) {
        if(carry>0) {
           sum=carry+firstNumber.pollFirst();
           carry=sum/10;
           result.offerLast(sum%10);
        }
        else result.offerLast(firstNumber.pollFirst());
      }
    }

    if(carry>0) result.offerLast(carry);
    return result;
  }

  public static void main(String[] args) {
    LinkedList<Integer> ll1 = new LinkedList<Integer>(Arrays.asList(3,1,5)); // 513
    LinkedList<Integer> ll2 = new LinkedList<Integer>(Arrays.asList(5,9,2)); // 295
    LinkedList<Integer> sum = sum(ll1, ll2); // 808
    System.out.println(sum); 

    ll1 = new LinkedList<Integer>(Arrays.asList(9,9,9)); // 999
    ll2 = new LinkedList<Integer>(Arrays.asList(1)); // 1
    sum = sum(ll1, ll2);
    System.out.println(sum); // 0001

    ll1 = new LinkedList<Integer>(Arrays.asList(5,1,5)); // 515
    ll2 = new LinkedList<Integer>(Arrays.asList(5,9,2)); // 295
    sum = sum(ll1, ll2);
    System.out.println(sum); // 018

    ll1 = new LinkedList<Integer>(Arrays.asList(1, 2, 5,1,5)); // 51521
    ll2 = new LinkedList<Integer>(Arrays.asList(5,9,2)); // 295
    sum = sum(ll1, ll2);
    System.out.println(sum); // 61815
  }
}
