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

import java.util.NoSuchElementException;
import java.util.Deque;
import java.util.ArrayDeque;

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

  // check if BST is empty
  public boolean isEmpty(){return root==null;}

  // insertion: an insertion to a Symbol Table is always predeceased by a search to prevent duplicate keys (same keys associated to different values -> Associative Array Abstraction violation!)
  public void put(Key key, Value value) {
   /* BST is Binary Tree (because comparison is a binary relation with a binary outcome (less than or greater than), 
     and Binary Trees are RECURSIVE data structtures (empty or a node with links to left and right binary subtrees)
     to modify the structure of BST we use private helper methods that take a copy of reference to the root as an input
     argument and go over the BST change its strucrure and pass on the changes back to the root node and all nodes in the path
   */
   // Sanity check of input arguments:
   if(key==null) throw new NullPointerException();
   if(value==null) {delete(key); return ;}
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
   int cmp=key.compareTo(x.key); // 0 + - integer
   // 2. Recurse:
   // a. if the given key is greater than the key at the root under consideration: 
   //   i. the new key must be inserted to its right subtree
   //   ii.the link to its right subtree must get modified (pass on the structural modifications up to the parent Node)
   if(cmp>0) x.right=put(x.right, key, value);
   // b. if the given key is less than key of the root of the subtree under consideration
   //   i. the new key must be inserted to its left subtree
   //   ii.the link to its left subtree must get modified (pass on the structural modifications up to the parent Node)
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

  // delete a key-value pair with the given associated key from ST
  public void delete(Key key) {
    // sanity check the key:
    if(key==null) throw new NoSuchElementException();
    // if there in no key-value pair associated with the given key in ST
    if(!contains(key)) throw new NoSuchElementException();
    // if the ST is empty
    if(isEmpty()) throw new NoSuchElementException();

    // delete the node with associated key that is equal to the given key in the BST rooted at node root
    // and update the structure of tree and maintain DS invariance:
    root=delete(root, key);
  }

  // recursively search for the node with the given key associated to it and delete that node and update BST structure
  private Node delete(Node x, Key key) {
    // BASE CASE: empty node
    if(x==null) return null;

    // RECURRENCE:
    int cmp=key.compareTo(x.key);
    if(cmp<0)  x.left=delete(x.left, key);
    else if(cmp>0)  x.right=delete(x.right, key);

    // if the given key equals the key associated with node x:
    else {
      // CASE 1. if x has only one child (or no child):
      if(x.right==null)  x=x.left;
      if(x.left==null)  x=x.right;

      // CASE 2. if x has both left and right child:
      //      i. swap it with its successor
      //      ii. deleteMin on its right subtree

      // make a copy of a reference to node x:
      Node copy=x;
      // 1. replace node x with its successor node
      x=successor(x, x.key);
      // 2. delete min on the right subtree (this would delete the successor)
      copy=delMin(copy.right, x.key);
      // 3. rewire links:
      x.right=copy.right;
      x.left=copy.left;
    }

    // update the count:
    x.count=1+size(x.left)+size(x.right);
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

  // return a value associated  with a given key (if such a key-value exists in the symbol table implemented by BST)
  public Value get(Key key) {
   // sanity check the given key:
   if(key==null) throw new NullPointerException();
   // if ST is empty:
   if(isEmpty()) throw new NoSuchElementException();

   // check if the ke exist in the ST: 
   // Search for the given Key in the BST by comparing keys and directing search based on the the result of comparison (similar to Binary Search in an array)
   Node x=get(root, key);
   if(x==null) return null; // search miss
   return x.value; // return the value associated with the given key
  }
  // private recursive helper method: working on a BST by having a copy of a reference to the root node:
  private Node get(Node x, Key key) {
   // BASE CASE: empty BST
   if(x==null) return null; // search miss
   // RECURRENCE:
   // Like Binary Search Array, compare the given key with the key of the subtree rooted at the Node x and direct
   // search for the given key based on result of the comparison:
   int cmp=key.compareTo(x.key);
   if(cmp>0) // search x's right subtree
    return get(x.right, key);
   else if (cmp<0) // search x's left subtree
    return get(x.left, key);
   else return x; // return the node with the key equal to the given key: search hit 
  }
  
  // check if ST contains a key-value pair indexed by the given key:
  public boolean contains(Key key){return get(key)!=null;}

  // ORDERED Operaions: 
  // For Comparable keys we can perform ordered operations on key with SORTED order represent by BST data structure
  
  // returns the Min key (smallest key):
  // The left most node of the BST contains the key which is LESS than ALL keys in the BST
  // (because of Symetric Order condition) similar to the left most key in the Binary Search Array BSA[0]
  public Key minKey(){
   // BST is empty?
   if(isEmpty()) throw new NoSuchElementException();

   // otherwise call a recursive helper method to find the left most node of the tree:
   Node x=min(root); // All bst operations take root as an input argument and work with
   return x.key; // return the key associate with the left most key in BST (min key)
  } 
  // find the min key in a subtree root at the Node x:
  private Node min(Node x) {
   // Sanity check
   if(x==null) throw new NullPointerException();

   // BASE CASE:
   // no further left child
   if(x.left==null) return x; // x is the left most child
   // RECURRENCE:
   // go to the left subtree of x:
   return min(x.left);
  }

  // returns the key associate the max key in the BST symbol table
  public Key maxKey() {
   // check if BST symbol table is empty:
   if(isEmpty()) throw new NoSuchElementException();
   
   // call to a recursive helper method that take the root node
   // as an input and returns the right most node of the BST rooted
   // at the root node:
   Node x=max(root);
   return x.key;// retyrn the key associate with the right most node of the BSE rooted at root node
  }

  // recursive max method: Find the right most node in the BST rooted at node x:
  private Node max(Node x) {
   // sanity check:
   if(x==null) throw new NullPointerException();
   
   // BASE CASE: 
   // if node x has no right child: x itself the right most child of the tree rooted at x:
   if(x.right==null) return x;
   // RECURRENCE:
   // recusively move down the BST using its right link
   return max(x.right);
  }

  // return a key from the ST (if exists such a key) such that the given key is the LARGEST key in the ST LESS than or EQUAL to the given key:
  // st.floor(key).compareTo(key) <=0
  public Key floor(Key key) {
   // sanity check the given key:
   if(key==null) throw new NullPointerException();
   // if ST is empty
   if(isEmpty()) throw new NoSuchElementException();

   // Otherwise recursively find the floor of the given key among keys in the ST
   Node x=floor(root, key);
   if(x==null) return null; // the given key is less than all the keys in the ST
   return x.key;
  }
  // recursive helper method to find the floor of given keys among keys in subtree BST rooted at node x
  private Node floor(Node x, Key key) {
   // BASE CASE: empty subtree -> search miss
   if(x==null) return null;
   // RECURRENCE:
   /*
    Compare the given key with the key at the root of the subtree under consideration (node x):
    int cmp=key.compareTo(x.key); // 0 - + -> there are going to be 3 cases:
    1. if the given key is EQUAL to the key at the node x then return node x (it cannot get closer to the key from below)
    2. if the given key is LESS than the key at the node x recursively search for a key that is bounded from above by (less than or equal to) 
      the given key on nodes that have keys less than the node x (x's left subtree): return floor(x.left, key)
    3. if the given key is GREATER than the key at the node x:

      NOTE: node x itself can be a candidate to be the floor of the given key, and anything on its left subtree is definitely NOT a candidate
        but its right subtree (nodes with key greater than x) may contain a better candidate which is GREATER than x but still less than the given key

      a. check if node x has a right child:
       i. if NOT return the node x
      b. Otherwise:
       i. check if the right child of the node x is greater than the given key
       ii. if so, return node x (node x is the best candidate which is less than the given node)
       iii. if not, right child of the node x is greater than x and less than the given key so recursively search for the 
          floor of the given key on node x's right subtree
   */
   // compare the given key with the key at the node x:
   int cmp=key.compareTo(x.key); // the result of comparing keys would guide the search
   if(cmp==0)  return x;
   if(cmp<0) return floor(x.left, key);
   // cmp > 0 : the given key is greater than the key at the node x:
   if(x.right==null) return x;
   // if the given key is less than the key of x's right child then return x
   if(key.compareTo(x.right.key)<0) return x;
   return floor(x.right, key);
  }
  // find the node in the BST rooted at node x which its associate key 
  // is the predecessor of the given key in an array of keys in SORTED order:
  private Node predecessor(Node x) {
   // sanity check:
   if(x==null) throw new NullPointerException();
   /* predecessor of a node x is the node such that its key is the largest key LESS than the key of the node x:
    1. if node x has no left child it does not have a predecessor return null
    2. if node x has left child, return the node with the max key on its left subtree that node would be the predecessor of x
   */
   if(x.left==null) return null; // x is the left most node and it does not have any predecessor
   return max(x.left); // return the node with the max key on x's left subtree
  }

  // returns a key from the BST instance such that it is greater than or equal to the given key
  public Key ceiling(Key key) {
    // sanity check of the given key
    if(key==null) throw new NullPointerException();

    // Otherwise: recursive search for node (if existed such a node) with a key which is the ceiling key of the given key on the BST rooted root
    Node x=ceiling(root, key);
    if(x==null) return null; // given key is GREATER than all keys associated with BST nodes rooted at the root
    return x.key;
  }
  /*
   Compare the given key with the key assoctiated with the given node x:
   1. if the given key is equal to the key associated with node x then return node x (node x's key is tightest upper bound for the given key)
   2. if the given key is greater than the key associated with the node x: recursively search on x's right subtree (nodes with associated keys that are greater than x's key)
   3. if the given key is less than the key associated with tne node x:
     a. node x itselft could be an upper bound, but x.left could be a tighter upperbound
       i. if x has no left child then retrun x;
       ii. if the given key was greater than the key associated with x's left child then return x
       iii. otherwise, recursively search for a tighter upper bound for the given key among keys associated with BST nodes 
  */
  private Node ceiling(Node x, Key key) {
    // BASE CASE: empty tree
    if(x==null) return null;
    // RECURRENCE:
    int cmp=key.compareTo(x.key);
    if(cmp==0) return x;
    if(cmp<0) {
     if(x.left==null) return x;
     if(key.compareTo(x.left.key)>0) return x;
     return ceiling(x.left, key);
    }
    return ceiling(x.right, key);
  }
  // Successor: find the node such that its associated key placed right after the given key in a SORTED array of keys
  private Node successor(Node x, Key key) {
   // sanity check the key:
   if(key==null) throw new NullPointerException();
   // if ST is empty
   if(isEmpty()) throw new NoSuchElementException();
   // if x has no right child (x itself is the right most child) then it has no successor node in BST:
   if(x.right==null) return null;
   // otherwise the node with the min key associated with it on x's right subtree is x's successor:
   return min(x.right);
  }

  // Return number of nodes with keys associated to them LESS than the given key
  public int rank(Key key) {
   // sanity check the key:
   if(key==null) throw new NullPointerException();
   // check if the ST is empty
   if(isEmpty()) throw new NoSuchElementException();
   // otherwise recursively fid the number of node is BST with keys associated to them LESS than the given key
   int rank=rank(root, key);
   return rank;
  }

  // recursive helper method that finds number nodes with keys associated to them less than the given key in a BST rooted at node x:
  private int rank(Node x, Key key) {
   // BASE CASE: empty tree
   if(x==null) return 0; // 0 node with keys less than the given key
   // compare the given key with the key associated with the node x:
   int cmp=key.compareTo(x.key);
   // 1. if the given key is equal to the key associated with the node x:
   //   number of nodes with keys associated to them LESS than the give key
   //   is precisely the number of nodes on x's left subtree:
   if(cmp==0) return size(x.left);
   // 2. if the given key is GREATER than the key associated with the node x:
   //   the number of nodes with keys assoctiated to them LESS than the given key is:
   //   node x itself + all nodes on its left subtree + recurse on x's right subtree (number of nodes on x's right subtree that are less than the given key)
   if(cmp>0) return 1+size(x.left)+rank(x.right, key);
   // 3. if the given key is less than the key associated with the key at the node x there is no node in the BST rooted at node x with keys associated to it with LESS than the given key!
   return 0;
  }

  // return the node that has key associated to it which is the k-th smallest among all keys
  // associated to the node of BST
  public Key select(int k) {
   // sanity check k:
   if(k<0 || k>=size()) throw new IndexOutOfBoundsException();
   // otherwise recursively search for the node with the key assoicated to it is the k-th smallest key
   // among all node in BST rooted at node root:
   Node x=select(root, k);
   return x.key; // return the key associate with such a node
  }
  // recursive helper method: find the node with the key associated to it being kth smallest key among all keys in the BST rooted at node x
  private Node select(Node x, int k) {
   // find the rank of node x: number of nodes in ST with keys associated to them less than x's key:
   int rank=rank(x.key);
   // if the rank of node x was equal to the given k return node x
   if(k==rank) return x; // return node x
   if(k<rank) return select(x.left, k); // recursively search for the k-th smallest on x's left subtree
   // if k>rank : we have to look for k-rank-1 th smallest key in ST on x's right subtree (subtract rank, and 1 for x itself)
   return select(x.right, k-rank-1);
  }

  // delete the node with the min key
  public void delMin() {
   // if ST is empty
   if(isEmpty()) throw new NoSuchElementException();

   // find the min key:
   Key min=minKey();
   // recursively search for the key, remove the node associate with the key and update the BST structure on the way up
   root=delMin(root, min);
  }
  private Node delMin(Node x, Key key) {
   // sanity check 
   if(x==null) throw new NullPointerException();
   
   // BASE CASE: x has no left child: 
   // x itself is the node with Min key in a BST rooted at x: return a reference to its right child
   if(x.left==null) x=x.right;

   // RECURRENCE: move down on the tree and update links and counts
   else {
    int cmp=key.compareTo(x.key);
    if(cmp<0) x.left=delMin(x.left, key); // if the given key is less than the key associated to node x: search for the key among nodes on x's left subtree
    else if(cmp>0) x.right=delMin(x.right, key); // if the given key is greater than the key associated with the node x: search for key among nodes on x's right subtree
    else x=x.right; // if the given key is equal to the key associate with the node x
   }

   // recursively corrent the count for each node on the path from root to the min
   x.count=1+size(x.left)+size(x.right);
   return x;
  }

  // delete the node with max key associated with it (max key among all keys in ST)
  public void delMax() {
   // sanity check:
   if(isEmpty()) throw new NoSuchElementException(); // the BST (ST) is empty
   
   // fin the max key among all key in ST (BST)
   Key max=maxKey();
   
   // recursively move down through the tree until reaching the node with max key assoicated with it, delete the node and
   // update the tree structure moving back up recursively:
   root=delMax(root, max);
  }

  // search for the given key among the nodes of the subtree rooted at the node x and delete the node associated with the given key and update
  // the structure of the BST rooted at node x recursively:
  private Node delMax(Node x, Key key) {
   // sanity check:
   if(key==null) throw new NullPointerException();
   // isEmpty()?
   if(isEmpty()) throw new NoSuchElementException();
  
   // BASE CASE: x is the right most child of the subtree rooted at x
   if(x.right==null) x=x.left;

   // RECURRENCE: search for the key in x's subtrees
   else {
    int cmp=key.compareTo(x.key);
    if(cmp>0)  x.right=delMax(x.right, key);
    else if(cmp<0) x.left=delMax(x.left, key);
    else x=x.left;
   }

   // update the count:
   x.count=1+size(x.left)+size(x.right);
   return x;
  }
  // return a Iterable collection of all keys in the ST in a SORTED order
  public Iterable<Key> keys() {
    // Instantiate a Queue of type Deque:
    Deque<Key> queue=new ArrayDeque<Key>();
    inorder(root, queue);
    return queue;
  }
  // recursively traverse the BST in order:
  private void inorder(Node x, Deque<Key> queue) {
    // BASE CASE: empty tree
    if(x==null) return ;
    // RECURRENCE:
    inorder(x.left, queue);
    queue.offer(x.key);
    inorder(x.right, queue);
  }

  /* 
    range count:
    range keys:
  */  


}





































