/*
  10. prinPaths()
  Given a binary tree, print out all of its root-to-leaf paths.
*/

import java.util.*;

public class AllPaths {

  private static class Node<Key> {
    private Key key;
    private Node left, right;
    public Node(Key key) {
      this.key=key;
    }
  }

  // return a link to the root of the doubleTree
  private static <Key> void allPaths(Node<Key> x, Deque<Key> path, List<List<Key>> list) {
    // termination condition:
    if(x==null) return ; // hitting the null node

    // for all non-null nodes in the tree:

    // 1. add the node to the path: 
    path.push(x.key);

    // 2. search for the next node on the path if such a node exists:
    // there are 4 possible cases:

    // a. node x is a leaf node and has no children
    if(x.left==null && x.right==null) {
      // add the leaf node:
      // 1. reverse the path (currently the path is in leaf to root order)
      List<Key> rPath=new LinkedList<Key>(path);
      Collections.reverse(rPath);
      // 2. add the reverse path to the collection of paths
      list.add(rPath);

      // pop the last node added to the path from the path stack, and
      // return to the caller method
      path.pop();
      return ;      
    }

    // b. if x only has one subtree: (left)
    if(x.right==null)  allPaths(x.left, path, list);
    // c. if x only has one subtree: (right)
    else if(x.left==null) allPaths(x.right, path, list);
    // d. if x has both subtrees:
    else {
      allPaths(x.left, path, list);
      allPaths(x.right, path, list);
    }

    // 3. before returning back to the caller, remove the most recently added node to the path
    path.pop();
  }

  public static void main(String [] args) {
    // let's make a small bst:
    Node<Integer> r=new Node<Integer>(2);
    r.left=new Node<Integer>(1);
    r.right=new Node<Integer>(3);

    List<List<Integer>> list=new LinkedList<List<Integer>>();
    Deque<Integer> path=new ArrayDeque<Integer>();
    allPaths(r, path, list);
    
    // Print all root to leaf paths:
    System.out.println(list);

  }
}
