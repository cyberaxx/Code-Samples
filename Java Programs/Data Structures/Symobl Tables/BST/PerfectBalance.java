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

import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PerfectBalance<Key extends Comparable<Key>, Value> {
  private Node root; // the root of the BST

  // Node inner class: each node contains a key-value pair
  private class Node {
    private Key key;
    private Value value;
    private Node left, right;
    // Constructor:
    public Node(Key key, Value value) {
      this.key=key;
      this.value=value;
    }
  }

  // constructor:
  public PerfectBalance(Key[] keys) {
    // if the array was not already sorted:
    if(!isSorted(keys)) {
      // sort comparable keys:
      Arrays.sort(keys); // java system sort would use merge sort
    }

    // build a bst from the array and return a reference to its root:
    root=bst(keys,0, keys.length-1);

    assert isBalanced(root);
  }

  public Iterable<Key> postOrder() {
    if(root==null) throw new NoSuchElementException();
    List<Key> list=new ArrayList<Key>();// an empty list
    postOrder(root, list);
    return list;
  }
  // recursive method that goes down the tree rooted at x and add keys to the collection:
  private void postOrder(Node x, List<Key> list) {
    // BASE CASE:
    // empty subtree
    if(x==null) return ; // do nothing

    // RECURRENCE:
    // LRD
    postOrder(x.left, list); // recursively process x's left subtree
    postOrder(x.right, list); // recursively process x's right subtree
    list.add(x.key); // add the key associated with the node x to the list
  }
  // recursively build the bst from the sorted array:
  private Node bst(Key [] keys, int lo, int hi) {
    if(hi<lo) return null; // empty tree
    // find the median in the sorted array:
    int mid=(lo+hi)/2;
    Node x=new Node(keys[mid], null);
    // consruct left and right subtree recursively:
    x.left=bst(keys, lo, mid-1);
    x.right=bst(keys, mid+1, hi);
    return x;
  }

  // returns false if the input array is not sorted in ascending order:
  private boolean isSorted(Key [] keys) {
    if(keys==null) throw new NullPointerException();
    // iterate over array of keys
    for(int i=1; i<keys.length; i++) {
      // if the key at index i is less the previous key at index i-1
      if(keys[i].compareTo(keys[i-1])<0) return false;
    }
    return true;
  }

  // recursivlely goes down the tree nodes root at x and check if the tree is not null
  // all its subtrees have the same height
  private boolean isBalanced(Node x){
    // BASE CASE: empty tree
    if(x==null) return true;
    // RECURRENCE:
    if(height(x.left)!=height(x.right)) return false;
    return isBalanced(x.right) && isBalanced(x.left);
  }

  // recursive method to process a bst rooted at node x
  private int height(Node x){
    // BASE CASE:
    // empty bst:
    if(x==null)  return -1;
    // RECURRENCE:
    // the height of the bst is the height of its tallest child +1
    return 1+Math.max(height(x.left), height(x.right));
  }

  // test client:
  public static void main(String[] args) {
    String[] input=new String[]{"z","x","t","s","q","h","d","a"};
    PerfectBalance<String, Integer> bst=new PerfectBalance<String, Integer>(input);
    System.out.println(bst.postOrder());
  }

}
