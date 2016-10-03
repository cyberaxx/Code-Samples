/*
  9. mirror()
  Change a tree so that the roles of the left and right pointers are swapped at every node. 
*/

import java.util.*;

public class Mirror {

  private static class Node<Key> {
    private Key key;
    private Node left, right;
    public Node(Key key) {
      this.key=key;
    }
  }
  
  // return a link to the root of mirrored binary tree
  private static <Key> Node<Key> mirror(Node<Key> x) {
    // termination condition:
    if(x==null) return x; // x has been mirrored
 
    // for all non-null nodes in the tree:
    // 1. Pointer rewiring: swap their left subtree and right subtree
    Node<Key> temp=x.left;
    x.left=x.right;
    x.right=temp;

    // 2. propagating the change down to the leaves of the tree by recursing on x's left and right subtree:
    x.left=mirror(x.left); // x's left link is going to get modified
    x.right=mirror(x.right); // x's right link is going to get modified

    return x; // return a reference to the modified x
  }

  public static void main(String [] args) {
    // let's make a small bst:
    Node<Integer> p=new Node<Integer>(2);
    p.left=new Node<Integer>(1);
    p.right=new Node<Integer>(3);

    // double the tree rooted at p
    p=mirror(p);
    inorder(p);
    System.out.println();
  }

  public static <Key> void inorder(Node<Key> x) {
    // termination case:
    if(x==null) return ; // hitting null nodes

    // for all non-null nodes:
    // L: go to the left as far as possible:
    inorder(x.left);
    // D:
    System.out.print(x.key);
    // R: go to the right
    inorder(x.right);
  }
}
