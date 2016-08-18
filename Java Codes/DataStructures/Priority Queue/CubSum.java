import java.util.Collection;
import java.util.Arrays;
import java.util.Collections;

import java.util.PriorityQueue;

/*
Computational number theory:
Write a program CubeSum.java that prints out all integers of the form a3 + b3 where a and b are integers between 0 and N in sorted order, without using excessive space. That is, instead of computing an array of the N2 sums and sorting them, build a minimum-oriented priority queue, initially containing (03, 0, 0), (13, 1, 0), (23, 2, 0), ..., (N3, N, 0). Then, while the priority queue is nonempty, remove the smallest item (i3 + j3, i, j), print it, and then, if j < N, insert the item (i3 + (j+1)3, i, j+1). Use this program to find all distinct integers a, b, c, and d between 0 and 10^6 such that a3 + b3 = c3 + d3, e.g., 1729 = 9^3 + 10^3 = 1^3 + 12^3.

Taxicab numbers:
Find the smallest integers that can be expressed as the sum of cubes of integers in two different ways (1,729), three different ways (87,539,319), four different ways (6,963,472,309,248), five different ways (48,988,659,276,962,496), and six different ways (24,153,319,581,254,312,065,344). Such integers are named Taxicab numbers after the famous Ramanujan story. The smallest integers that can be expressed as the sum of cubes of integers in seven different ways is currently unknown. Write a program Taxicab.java that reads in a command line parameter N and prints out all nontrivial solutions of a3 + b3 = c3 + d3. such that a, b, c, and d, are less than or equal to N.

Computational number theory:
Find all solutions to the equation a + 2b^2 = 3c^3 + 4d^4 for which a, b, c, and d are less than 100,000. Hint: use one min heap and one max heap.

*/

public class CubSum {
}
