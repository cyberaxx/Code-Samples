// Goal. Given currency denominations: 1, 5, 10, 25, 100, devise a method
// to pay amount to customer using fewest number of coins.

/*
Coin values can be modeled by a set of n distinct positive integer values (whole numbers), arranged in increasing order as w1 = 1 through wn. The problem is: given an amount W, also a positive integer, to find a set of non-negative (positive or zero) integers {x1, x2, ..., xn}, with each xj representing how often the coin with value wj is used, which minimize the total number of coins
*/

import java.util.Collections;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Arrays;

public class coinChaining {
  public static void main (String [] args) {
    int W = 1422; // the bill to pay
    int [] currency_units = new int [] {1, 5, 10, 20, 100};
    Hashtable<Integer, Integer> payment_method = new Hashtable<Integer, Integer>();
    payment_method = optimalPaymentMethod(W, currency_units, payment_method);
    ArrayList<Integer> key_list = new ArrayList<Integer>(payment_method.keySet());
    for(Integer key : key_list) {
      System.out.println("[" + key + " : " + payment_method.get(key) +  "]");
    }
  }

  public static Hashtable<Integer, Integer> optimalPaymentMethod(int W, int [] currency_units, Hashtable<Integer, Integer> payment_method) {
    // sort currency units:
    Arrays.sort(currency_units); // O(nlogn)
    for (int i=currency_units.length-1; i>=0 && W>0; i--) {
      if (W/currency_units[i] > 0) {
	payment_method.put(currency_units[i], W/currency_units[i]);
        W = W%currency_units[i];
      }
    }

    return payment_method;
  }
}
