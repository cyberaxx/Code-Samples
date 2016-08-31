/*
  Associative Array Abstraction to implement Symbol Table (Map) interface (data type):
  for Comparable type keys: Key extends Comparable<Key>

  Array impelementation Limitaions:

  1. Binary Search Array requires array resizing because of static nature of arrays and dynmaic nature of symbol tables
  2. Binary Search Array requires Linear Time insertion/deletion to preserve the SORTED order of the keys in the keys array:
     For client applications that use symbol table in their inner loop, with Linear number of insertions and deletions (N), 
     Binary Search Array performance would degenerate to Quadratic O(N^2) and this is not useful for application for many insertion

  LinkedList Implementation Solution:

  In order to overcome two major issues mentioned above we can use Binary Search Tree abstraction implemented by LinkedLists to implement Symbol Table interface
  with Comparable type keys (keys with a total ordering):

  1. Binary Search Tree abstraction uses LinkeList to maintain a collection of key-value pairs so it is a dynamic data type with no size restriction
  2. Like Binary Search Array, Binary Search Tree must preserved keys in the Symbol Table in a SORTED order. To maintain this invariance BST must always 
     preserve BST condition (symetric order condition) such that:
     SYMETRIC ORDER CONDTION (search tree condition): For any node in the BST the KEY that belongs to the node must be GREATER than its LEFT subtree (all nodes in its left subtree)
                                                      and LESS than its RIGHT subtree (all nodes in its right subtree).

 Symetric order condition provides 1-to-1 correspondence between SORTED array of keys in Binary Search Array and Binary Tree with Symetric order of keys (BST).

*/

public class BST<Key extends Comparable<Key>, Value> {
  // instance variables:
  private Node root; // A BST characterize by a reference to its root node

  // inner class that defines nodes of the LinkList that maintain key-value pairs
  private class Node {
    // instance fields:
    // each node contains: key, value, link to nodes with keys less than its key (left), link to nodes with keys grater than its key (right)
    private Key key; // a generic type key (with a total order implemented by a compareTo method)
    private Value value; // a generic non-null value
    // links to left and right subtrees (nodes with keys less than and greater than its own key;
    private Node left;
    private Node right;
    // number of nodes in the BST rooted at the node instance:
    private int count;

    // Constructor:
    // default constructor
    public Node(){}
    // constructor that initialize key an value with arguments passed to it
    public Node(Key key, Value value) {
      //initialize Node's instance fields:
      this.key=key;
      this.value=value;
      this.count=0;
      this.left=null;
      this.right=null;
    }
  }

   // BST Constructor
   public BST(){root=new Node();}

   // API:
   // Symbol Table basic Operations: put, get, contains, delete, size, isEmpty, keys

   // insertion: an insertion to a Symbol Table is always predeceased by a search to prevent duplicate keys (same keys associated to different values -> Associative Array Abstraction violation!)
   public void put(Key key, Value value) {
     /* BST is Binary Tree (because comparison is a binary relation with a binary outcome (less than or greater than), 
        and Binary Trees are RECURSIVE data structtures (empty or a node with links to left and right binary subtrees)
        to modify the structure of BST we use private helper methods that take a copy of reference to the root as an input
        argument and go over the BST change its strucrure and pass on the changes back to the root node and all nodes in the path
     */
     // Sanity check of input arguments:
     if(key==null || value==null) throw new NullPointerException();
     root=put(root, key, value);
   }

   // private recursive helper method that takes a reference to the root of subtree and key and value to be inserted to the subtree rooted at node
   private Node put(Node x, Key key, Value value) {
     // BASE CASE: empty BST
     // create a new Node and return a reference to the created Node up to the parent of the subtree:
     if(x==null) return new Node(key, value); // return a reference to a root of BST containing a key-value pair with null left and right links
     
     /* RECURRENCE: 
      if the subtree under consideration is not empty (x is the root of subtree under consideration) put a new Node with the given key into BST 
      rooted at Node x such that the Symetric Order Condition does Not get violated:
      1. Search for the rightful position of the new Node
         by comparing its key to the key of the ROOT of the subtree under consideration
      2a. If there was already a node with the same key -> update its value to the new value
      2b. Otherwise, create a new node containing the given key-value pair to the proper subtree of Node x
      3. pass on the structural changes back up to the root node (by updating all links on the path from node x to the newly added Node) -> recursive structure takes care of this step
     */

     // similar to comparing the new key with the key of the middle element in BSA subarray under consideration (rank operation)
     // 1. Compare the new key to the key of the root under consideration (to findout which subtree we have to go down to)
     int cmp=key.compareTo(x.key); // 0 + - 

     // 2. Recurse:
     // a. if the given key is greater than the key at the root under consideration: 
     //    i. the new key must be inserted to its right subtree
     //    ii.the link to its right subtree must get modified (pass on the structural modifications up to the parent Node)
     if(cmp>0) x.right=put(x.right, key, value);

     // b. if the given key is less than key of the root of the subtree under consideration
     //    i. the new key must be inserted to its left subtree
     //    ii.the link to its left subtree must get modified (pass on the structural modifications up to the parent Node)
     else if(cmp<0) x.left=put(x.left, key, value);

     // c. if the key is equal to the key at the root of the subtree under consideration:
     // update the value of the given key in the BST (no structural change required!)
     else
       x.value=value;

     // update the size of subtree rooted at Node x
     x.count= 1+size(x.left)+size(x.right);

     // return the reference to the root of the subtree under the consideration
     return x;
   }

   // return number of Nodes (key-value pairs) in the BST instance:
   public int size() {return size(root);}// returns the size of root Node
   // returns the size of subtree rooted at node x:
   private int size(Node x) {
     // BASE CASE: empty BST
     if(x==null) return 0;
     // RECURRENCE:
     // a. +1 for the Node x itself
     // b. + size of its left subtree and
     // c. + size of its right subtree
     return 1+size(x.left)+size(x.right); 
   }

}
