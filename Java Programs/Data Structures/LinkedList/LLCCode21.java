import java.util.Collection;
import java.util.Set;
import java.util.List;

import java.util.Collections;
import java.util.Arrays;

import java.util.LinkedList;
import java.util.HashSet;

public class LLCCode21{
  /*
  2.1 Write code to remove duplicates from an unsorted linked list.
  solution: use a HashSet collection data type, dump elements of the list to the HashSet (duplicates will be removed)
  dump HashSet to back to the list; This whole procedure takes Linear time wrt number of the elements in the list
  */
  // non-destructive implementation
  // generic method with a Collection type return
  public static <Item> LinkedList<Item> removeDuplicates(LinkedList<Item> ll) {
    // is ll is already empty do nothing:
    if(ll.isEmpty()) return ll;

    HashSet<Item> hs = new HashSet<Item>();
    LinkedList<Item> newList = new LinkedList<Item>(); // empty linked list
    // traverse the LinkedList:
    for(Item item : ll)  hs.add(item); // if already contain the item, it will overwrite it (if not just put it)
    for(Item item : hs)
      if(!newList.offer(item))  throw new RuntimeException("Failed to operate offer operation on the LinkedList!");
    return newList;
  }

  // destructive implementation:
  // void generic method
  public static <Item> void removeDuplicatesDis(LinkedList<Item> ll) {
    // is ll is already empty do nothing:
    if(ll.isEmpty()) return ;

    HashSet<Item> hs = new HashSet<Item>();
    // traverse the LinkedList: O(n)
    for(Item item : ll)  hs.add(item); // if already contain the item, it will overwrite it (if not just put it)
    // clear the LinkedList:
    ll.clear(); // O(n)
    for(Item item : hs)
      if(!ll.offer(item)) throw new RuntimeException("Failed to operate offer operation on the LinkedList!");
  }

  /*
  FOLLOW UP
  How would you solve this problem if a temporary buffer is not allowed?
  dump the linked list into an array, sort the array, scan the array from left to right
  copy elements back to the linked list by checking if the previously added element is not
  the same as the current element:
  */

  // nondestructive implementation: O(NlogN) for sort
  public static <Item extends Comparable<Item>> LinkedList<Item> removeDuplicatesNoBuffer(LinkedList<Item> ll) {
    // is ll is already empty do nothing:
    if(ll.isEmpty()) return ll;

    // first sort the llist:
    // (NlogN) merge sort used by java
    Collections.sort(ll); // now items on the list are sorted:
    LinkedList<Item> newList = new LinkedList<Item>();
    // O(n) to traverse the list
    for(Item item : ll) {
      // if the newList is empty add the item to it:
      if(newList.isEmpty()) {
	if(!newList.offer(item)) throw new RuntimeException("Failed to operate offer method and add to the list!");
      }
      // if the newList is not empty
      else {
        // compare the last item in the new list with the current item to be added:
        if(newList.peekLast().compareTo(item) !=0 ) {
          // if they are not equal then: add it to the list:
          if(!newList.offer(item)) throw new RuntimeException("Failed to operate offer method and add to the list!");
        }
      }
    }
    return newList;
  }

  // destructive implementation: O(NlogN) for sort
  public static <Item extends Comparable<Item>> void removeDuplicatesNoBufferDis(LinkedList<Item> ll) {
    // is ll is already empty do nothing:
    if(ll.isEmpty()) return ;
    if(ll.size()==1) return ;

    // first sort the llist:
    // (NlogN) merge sort used by java
    Collections.sort(ll); // now items on the list are sorted:

    Item temp = ll.get(0);
    int index=1;
    // O(n^2)
    while(!ll.isEmpty()) {
      // O(n) to perform get(index) and remove(index)
      if(ll.get(index).compareTo(temp) == 0) {
        ll.remove(index);
      }
      else {
        temp=ll.get(index); // O(n)
        if(temp.compareTo(ll.peekLast())==0) break; // check if we reach the end of the list
        else index++;
      }
    }
  }  


  // client:
  public static void main(String [] args) {
    LinkedList<String> ll = new LinkedList<String>(Arrays.asList("Shervin", "Shervin", "Shervin", "Shervin", "Shervin", "Shervin", "Susan"));
    LinkedList<String> newList = new LinkedList<String>();
    System.out.print(ll);
    System.out.println();
    newList = removeDuplicatesNoBuffer(ll);
    System.out.print(newList);
    System.out.println();

    System.out.println();
    removeDuplicatesNoBufferDis(ll);
    System.out.print(ll);
    System.out.println();

  }
}
