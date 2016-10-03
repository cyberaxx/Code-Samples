/*
  10. doubleTree()
  For each node in a binary search tree, create a new duplicate node, and insert the duplicate as the left child of the
  original node. The resulting tree should still be a binary search tree. 
*/

public class DoubleTree {

  private static class Node<Key> {
    private Key key;
    private Node left, right;
    public Node(Key key) {
      this.key=key;
    }
  }

  // return a link to the root of the doubleTree
  private static <Key> Node<Key> doubleTree(Node<Key> x) {
    // base case: termination of doubling
    if(x==null) return null; // hitting the null node

    // for all non-null nodes:
    Node<Key> t=new Node<Key>(x.key); // make a new node from x
    // assign x's left subtree to t's left subtree
    t.left=x.left;
    // assign t as the root of x's left subtree
    x.left=t;

    // recurse on x's right subtree and t's left subtree (x's original left subtree)
    x.right=doubleTree(x.right);
    t.left=doubleTree(t.left);

    return x;
  }

  public static void main(String [] args) {
    // let's make a small bst:
    Node<Integer> p=new Node<Integer>(2);
    p.left=new Node<Integer>(1);
    p.right=new Node<Integer>(3);

    // double the tree rooted at p
    p=doubleTree(p);
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
