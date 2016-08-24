// Goal. Given currency denominations: 1, 5, 10, 25, 100, devise a method
// to pay amount to customer using fewest number of coins.

/*
Coin values can be modeled by a set of n distinct positive integer values (whole numbers), arranged in increasing order as w1 = 1 through wn. The problem is: given an amount W, also a positive integer, to find a set of non-negative (positive or zero) integers {x1, x2, ..., xn}, with each xj representing how often the coin with value wj is used, which minimize the total number of coins
*/
import java.util.HashMap;
import java.util.Arrays;

public class Test {

  public static void main(String [] args) {
    int [] currency = {10,5,100,20,50, 1};
    int cost = 3421;

    // Assuming elements in the currency array are distinct:
    // Sort the currency in the ascending order:
    Arrays.sort(currency);

    // feed the sorted currency array to chaining subroutines:
    HashMap<Integer, Integer> payment = coinChain(currency, cost);
    int [] paymentArray = coinChainArray(currency, cost);
    for(Integer key : payment.keySet()) {
      System.out.print(key + "$ -> " + payment.get(key));
      System.out.println();
    }

    System.out.println();

    for(int i=0; i<paymentArray.length; i++) {
      if (paymentArray[i] > 0) {
	System.out.print(currency[i] + "$ -> " + paymentArray[i]);
        System.out.println();
      }
    }
  }

  public static HashMap<Integer, Integer> coinChain(int [] currency, int cost) {
    // Greedy approach that uses O(nlong) for search and O(n) for payment 
    HashMap<Integer, Integer> payment = new HashMap<Integer,Integer>();
    for(int j=currency.length-1; j>=0 && cost > 0; j--) {
      if (cost/currency[j] > 0) {
        // add it to the payment:
        payment.put(currency[j], cost/currency[j]);
        // update cost:
        cost = cost % currency[j];
      }
    }
    return payment;
  }

  public static int [] coinChainArray(int [] currency, int cost) {
    // Array to keep track of payments:
    int [] payment = new int [currency.length];
    for(int i=0; i<payment.length; i++) payment[i]=0; // initialize the payment array
    for(int i=currency.length-1; i>=0 && cost > 0; i--) {
      if (cost/currency[i] > 0) {
	payment[i] = cost/currency[i];
        cost = cost % currency[i];
      }
    }
    return payment;
  }
}
