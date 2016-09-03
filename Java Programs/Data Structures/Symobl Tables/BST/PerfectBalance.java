/*
Perfect balance. 
Write a program PerfectBalance.java that inserts a set of keys into an initially empty BST such that
the tree produced is equivalent to binary search (BSA), in the sense that the sequence of compares done 
in the search for any key in the BST is the same as the sequence of compares used by binary search for the same set of keys.

*****************************************************************************
 *  Compilation:  javac PerfectBalance.java
 *  Execution:    java PerfectBalance < input.txt
 *  Dependencies: StdOut.java
 *  
 *  Read sequence of strings from standard input (no duplicates),
 *  and insert into a BST so that BST is perfectly balanced.
 *
 *  % java PerfectBalance
 *  P E R F C T B I N A R Y S R H 
 *  N E B A C H F I R R P R T S Y 
 *
 ****************************************************************************

*/

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.MaxPQ;
import java.util.Arrays;

public class PerfectBalance<Key extends Comparable<Key>> {
  private BST<Key> bst;
  
  // constructor:
  public PerfectBalance(Key[] keys) {
    // sort comparable keys:
    Arrays.sort(keys);
    
  }
  
}
