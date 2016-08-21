import java.util.Collection;
import java.util.Arrays;
import java.util.Collections;

import java.util.PriorityQueue;
import java.util.ArrayDeque;
import java.util.Deque;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/*
Computational number theory:
Write a program CubeSum.java that prints out all integers of the form a^3 + b^3 where a and b are integers between 0 and N in sorted order, without using excessive space. That is, instead of computing an array of the N^2 sums and sorting them, build a minimum-oriented priority queue, initially containing (03, 0, 0), (1^3, 1, 0), (2^3, 2, 0), ..., (N^3, N, 0). Then, while the priority queue is nonempty, remove the smallest item (i^3 + j^3, i, j), print it, and then, if j < N, insert the item (i3 + (j+1)^3, i, j+1). Use this program to find all distinct integers a, b, c, and d between 0 and 10^6 such that a^3 + b^3 = c^3 + d^3, e.g., 1729 = 9^3 + 10^3 = 1^3 + 12^3.

Taxicab numbers:
Find the smallest integers that can be expressed as the sum of cubes of integers in two different ways (1,729), three different ways (87,539,319), four different ways (6,963,472,309,248), five different ways (48,988,659,276,962,496), and six different ways (24,153,319,581,254,312,065,344). Such integers are named Taxicab numbers after the famous Ramanujan story. The smallest integers that can be expressed as the sum of cubes of integers in seven different ways is currently unknown. Write a program Taxicab.java that reads in a command line parameter N and prints out all nontrivial solutions of a^3 + b^3 = c^3 + d^3. such that a, b, c, and d, are less than or equal to N.

Computational number theory:
Find all solutions to the equation a + 2b^2 = 3c^3 + 4d^4 for which a, b, c, and d are less than 100,000. Hint: use one min heap and one max heap.

*/

public class CubeSum implements Comparable<CubeSum> { // CubeSum data type is a Comparable type
  /*
  Computational number theory:
  Write a program CubeSum.java that prints out all integers of the form a^3 + b^3 where a and b are integers between 0 and N in sorted order, without using excessive space. 
  That is, instead of computing an array of the N^2 sums and sorting them, build a minimum-oriented priority queue, initially containing
  (0^3, 0, 0), (1^3, 1, 0), (2^3, 2, 0), ..., (N^3, N, 0). Then, while the priority queue is nonempty, remove the smallest item (i^3 + j^3, i, j), print it, 
  and then, if j < N, insert the item (i3 + (j+1)^3, i, j+1). Use this program to find all distinct integers a, b, c, and d between 0 and 10^6 such that
  a^3 + b^3 = c^3 + d^3, e.g., 1729 = 9^3 + 10^3 = 1^3 + 12^3.
  */

  // an instance of a CubSum class must have following fields:
  private int i;
  private int j;
  private int sum;

  // Constructor:
  public CubeSum(int i, int j) {
    this.i=i;
    this.j=j;
    this.sum=(i*i*i)+(j*j*j);
  }

  // Total order: 0 if equal, <0 if less than, >0 if greater that
  public int compareTo(CubeSum that) {
    if(this.sum>that.sum)  return 1;
    if(this.sum<that.sum)  return -1;
    return 0;
  }

  // Prints out all integers of the form a^3 + b^3 where
  // a and b are integers between 0 and N in sorted order
  // without using excessive space.
  public static PriorityQueue<CubeSum> orderedCubes(int n) {
    // instantiate from PriorityQueue Class (an unbounded Collection type that can maintain a collection of Comparable CubeSum objects)
    // with constant access to min (min oriented priority queue implemented by a binary heap)
    PriorityQueue<CubeSum> init=new PriorityQueue();
    PriorityQueue<CubeSum> pq=new PriorityQueue();

    // insert CubeSum objects to the min oriented priority queue 
    for(int i=0; i<=n; i++)
      init.add(new CubeSum(i, 0)); // add a CubeSum instance to a min oriented priority queue 

    // while init priority queue is not empty
    while(init.size()>0) {
      // remove an item from the head of the init min oriented priority queue
      CubeSum item=init.poll();
      // add it to the pq (resulting priority queue or ordered cubes):
      pq.offer(item);

      if(item.j<item.i) {
        item=new CubeSum(item.i, item.j+1);
        // add to the init priority queue
        init.offer(item);
      }
    }
    return pq;
  }

  public static void printCubeSums(int n) {
    PriorityQueue<CubeSum> pq=orderedCubes(n);
    for(CubeSum item : pq)
      System.out.println("sum : " + item.sum + ", i: " + item.i + ", j: " + item.j);
  }
 

  // O(nlogn): Linearithmic time algorithm
  // find all distinct integers a, b, c, and d between 0 and 10^6 such that
  // a^3 + b^3 = c^3 + d^3, e.g., 1729 = 9^3 + 10^3 = 1^3 + 12^3.
  public static HashMap<Integer, List<Integer>> distinctInts(int n) { 
    // min oriented queue of all cubesums [0 10^6]:
    PriorityQueue<CubeSum> pq=orderedCubes(n);
    Deque<CubeSum> stack=new ArrayDeque<CubeSum>();
    HashMap<Integer, List<Integer>> hm=new HashMap<Integer, List<Integer>>();
    
    // trivial case: if pq is empty:
    if(pq.size()==0) return hm; // return an empty hashmap
    // trivial case: if pq has only one item:
    if(pq.size()==1) {
      CubeSum item= pq.poll();
      List<Integer> list=new ArrayList<Integer>(Arrays.asList(item.i, item.j));
      hm.put(item.sum, list);
      return hm;
    } // return the hm with the only item in min oriented priority queue
    
    // while MinPQ instance is not empty:
    // removing from the min-oriented priority queue (removed item would be in ascending order)
    while(pq.size()>0) {
      // poll the head of MinPQ
      CubeSum prev=pq.poll();
      // add it to the result stack temporarily:
      stack.push(prev);

      // check if the MinPQ is not empty:
      if(pq.size()>0) {
        // sneak peak at the new head of MinPQ (smallest item)
        CubeSum current=pq.peek();

        // compare the new item with the previously removed item from the MinPQ
        if(stack.peek().compareTo(current)==0)  stack.push(pq.poll());
	else stack.pop();// remove the privous item added to the stack
      }
    }

    // iterate over the stack: O(n)
    for(CubeSum item: stack) {
      if(hm.containsKey(item.sum)) {
        // use item.sum as a key in the hash map:
        List<Integer> list = hm.get(item.sum);
        list.addAll(Arrays.asList(item.i, item.j));
        hm.put(item.sum, list);
      }
      else {
        List<Integer> list=new ArrayList<Integer>(Arrays.asList(item.i, item.j));
        hm.put(item.sum, list);
      }
    }

    return hm;
  }

  // client of CubeSum class:
  public static void main(String[] args) {
    // printCubeSums(5);
    HashMap<Integer, List<Integer>> distinctMap = distinctInts(13); // O(NlogN)
    for(Integer key:distinctMap.keySet())
      if(key==1729)
        System.out.println("sum: " + key + " ==> a,b,c,d are: " + Arrays.toString(distinctMap.get(key).toArray()));
  }
}
