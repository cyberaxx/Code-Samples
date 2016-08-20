import java.util.Collection;
import java.util.Arrays;
import java.util.Collections;

import java.util.PriorityQueue;

/*
Computational number theory:
Write a program CubeSum.java that prints out all integers of the form a^3 + b^3 where a and b are integers between 0 and N in sorted order, without using excessive space. That is, instead of computing an array of the N^2 sums and sorting them, build a minimum-oriented priority queue, initially containing (03, 0, 0), (1^3, 1, 0), (2^3, 2, 0), ..., (N^3, N, 0). Then, while the priority queue is nonempty, remove the smallest item (i^3 + j^3, i, j), print it, and then, if j < N, insert the item (i3 + (j+1)^3, i, j+1). Use this program to find all distinct integers a, b, c, and d between 0 and 10^6 such that a^3 + b^3 = c^3 + d^3, e.g., 1729 = 9^3 + 10^3 = 1^3 + 12^3.

Taxicab numbers:
Find the smallest integers that can be expressed as the sum of cubes of integers in two different ways (1,729), three different ways (87,539,319), four different ways (6,963,472,309,248), five different ways (48,988,659,276,962,496), and six different ways (24,153,319,581,254,312,065,344). Such integers are named Taxicab numbers after the famous Ramanujan story. The smallest integers that can be expressed as the sum of cubes of integers in seven different ways is currently unknown. Write a program Taxicab.java that reads in a command line parameter N and prints out all nontrivial solutions of a3 + b3 = c3 + d3. such that a, b, c, and d, are less than or equal to N.

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

  // client of CubeSum class:
  public static void main(String[] args) {
    CubeSum
  }
}
