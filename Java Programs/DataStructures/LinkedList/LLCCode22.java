import java.util.Collection;
import java.util.List;

import java.util.NoSuchElementException;

import java.util.LinkedList;
import java.util.Collections;
import java.util.Arrays;

public class LLCCode22 {
  // 2.2. Implement an algorithm to find the nth to last element of a singly linked list.
  // use java LinkedList implementation: using the size field
  public static <Item> Item findNToLast(LinkedList<Item> ll, int n) {
    // check if the "n" is within a valid range [0, size]
    if(n>=ll.size() || n<0) throw new NoSuchElementException("Failed to peform the method. "+n+"is not within a valid range of values."); 
    return ll.get(ll.size()-n-1);
  }
  
  // client code:
  public static void main(String[] args){
    LinkedList<Double> ll = new LinkedList<Double>(Arrays.asList(23.4, 12.5, -98.4, 0.87, 12.56));
    System.out.print(ll);
    System.out.println();
    System.out.println(findNToLast(ll, 0));
    System.out.println(findNToLast(ll, 1));
    System.out.println(findNToLast(ll, 2));
    System.out.println(findNToLast(ll, 3));
    System.out.println(findNToLast(ll, 4));
  }

}
