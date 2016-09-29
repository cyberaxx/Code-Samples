/*
  This class provide imlementation for symbol table interface, when keys are Comparable.
  LLRBBST are based on 2-3 Trees.
  
  All the complications are the price to pay in order to MAINTAIN a COLLECTION in KEYS in a SORTED ORDER
  without sacrifying performance of the basic operations of symbol table interface (insert, delete, get)
  
  2-3 Tree insertion:
  i. Adding a new node to an empty 2-3 Tree:
     Changes the root from null to a 2-node
  ii. Adding any new node to the 2-3 tree:
	1. Changes a 2-node to 3-node (tree height does NOT change)
  	Or 
	2-1. Changes a 3-node to a TEMPORARY 4-node
	2-2. Split the 4-node into two 2-node and promote the middle key one level up
        (tree heigh increases by one and maintains balanced)

  2-3 Tree preserve following invariants:
      1. It is always perfectly balanced: all simple root-to-leaf paths have same number of link/node
      2. It satisfies symmetric order condition: all nodes in the 2-3 Tree are ordered wrt their corresponding keys

  LLRBBST has a one-to-one correspondece to 2-3 Tree in following way:
  It represent 3-node as two 2-nodes (internally) glued by left leaning red link (a node with a greater key is a parent, and a node with 
  smaller key is it's left child)

  LLRB BST is a BST: Binary tree with symmetric order condition
  LLRB BST has perfect black balance (because 2-3 Tree are perfectly balanced)

*/

/*
  Associative array interface (a[key]=value) implementation:
  1. keys are comparable (have a total ordering)
  2. keys cannot be null
  3. keys are DISTINCT (keys are indexes of the associated array)
  4. each key is associated with ONE value (one to one association between keys and values)
  5. value can be any generic value (value can be a collection like queue, stack, bag, list, ...)
  
  Basic operation:
  1. put(key, value): 	a[key]=value 
  2. get(key): 		a[key] 
  3. contains(key) 
  4. delete(key) 

  All basic operation in assoiciative array abstraction begins with searching for the given key:
  1. insertion: Search for the given key
	a. if the given key was already in the associative array instance: update its value  a[key]=newValue
	b. if the given key was NOT in the associative array instance:
		i. insert the key at its rightful position
		ii. associate the given value with it
  2,3. get, contains
  4. deletion
  
  While BSTs, improve the efficiency of insertion and deletion compare to associative array implementation using parallel arrays (Sorted array of keys, and an array
  for values) on average, in thw worst case BST can get even worst than an Binary Search Array, where BST degenerates to a SORTED LinkedList (where keys come in and
  inserted to the tree in ascending or descending SORTED order). In that case the height of the BST become Linear proportional to number of nodes (items in the associative array instance)
  and not only insert and delete take linear time, even search and all other ordered operations will take linear time which comapare to Binary Search Array is way worst! In Binary Search Array
  all ordered operation except iteration took logN time or even constant time (for selection, min, max). And only linear time operations were insertion and deletion.

  How to fix?!
  For each node in the BST, NOT ONLY make sure that each node is GREATER than all nodes on its LEFT subtree and LESS than all nodes on its right subtree (so it lies
  in between nodes on its left and right), BUT ALSO, make sure that each node is exactly the middle node among all nodes in its left and right subtree (including itself)
  that's where Binary Search gets its efficiency: by repeatedly searching the middle of array and its subarrays respectively, each time it halves the size of subproblem and it does the
  seach in logN time (where keys are in a sorted order).

  Now, where each node in the BST is GREATER than nodes on its LEFT and LESS than nodes on its RIGHT and also it is a MEDIAN (middle node of all nodes [leftmost  rightmost]) number of
  nodes on its left and right subtree are equal, and since BST is Binary, at each level n/2 of nodes will go to each subtree and worst case height of the tree would be logN (number of time
  that N can be divided by 2). Not only the height of the BST becomes logarithmic, for each node in the tree number of the hobs to from the node to its relative min and max are equal (Duh! definition of 
  the middle) and more generally any simple path from root of the BST to all nodes at leaves has equal length of logN.
*/

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;

public class LLRBBST<Key extends Comparable<Key>, Value> {
  // Nodes colors:
  private static final boolean BLACK=true;
  private static final boolean RED=false;

  // a BST is always characterized by a reference to its root
  private Node root;

  // Constructor:
  public LLRBBST(){}

  // API: 1. Basic ST behaviours 2. Ordered key operations

  // 1. Basic ST behaviours
  public void put(Key key, Value value) {
    // check if key is not null:
    if(key==null) throw new NullPointerException("Key cannot be null!");

    // if value is null it means delete:
    if(value==null) { delete(key); return ;} // delete the key-value pair associated with the given key
 
    // bst is characterized by the reference to its root node
    // recursively add a new node to bst rooted at root and propagate the modification all the way 
    // back to the root
    root=put(root, key, value);
    // root must be alway black
    if(isRed(root))
      root.color=BLACK;
  }

  // recursive helper method:
  // put method returns a link to the modified subtree after insertion
  private Node put(Node x, Key key, Value value) {
    // Base Case: search miss -> rigthtful position of key on bst
    if(x==null) return new Node(key, value, RED);
    
    // RECURRENCE: search for the given key in the bst rooted at x
    int cmp=key.compareTo(x.key);

    // if the given key already exists in the bst
    if(cmp==0)  x.value=value; // update the value associated with x with the new given value
    // search left or right subtrees of x according to the comparison result
    else if(cmp<0)
      // new node must get inserted to the left subtree of x and all changes must be propagated back up to the root of x's left subtree:
      x.left=put(x.left, key, value);
    else
      // new node must get inserted to the right subtree of x and all changes must be propagated back up to the root of x's right subtree:
      x.right=put(x.right, key, value);

     // update the count field of x: 1+number of nodes on its left and right subtrees
     x.count=1+size(x.left)+size(x.right);

     // recover the balance of the bst after insertion:
     if(isRed(x.right))  x=rotateLeft(x); // rotate the right leaning red link to the left
     if(x.left!=null && isRed(x.left.left))  x=rotateRight(x); // if two red links where incident to a node, put the node with median key as a root
     if(isRed(x.left) && isRed(x.right))  colorFlip(x); // split a tem 4-node to  two 2-node and move the middle node to the parent level

     return x; 
  }

  // Rotations:
  // 1. Rotate a left leaning red link to right
  private Node rotateRight(Node x) {
    // x.left link is red, so:
    // 1. pointer rewiring
    Node t=x.left; // copy a reference to x.left node
    x.left=t.right; // move the link to node in between x and t from t's right to x's left around the node in between
    t.right=x; // make x as a right child of t

    // 2. set t and x colors:
    t.color=x.color;
    // set x color RED
    x.color=RED;

    // return t:
    return t;
  }

  // 2. Rotate a right leaning red link to left
  private Node rotateLeft(Node x) {
    // x.right link is red
    // 1. rewiring pointers:
    Node t=x.right;
    x.right=t.left;
    t.left=x;

    // colors
    t.color=x.color;
    x.color=RED;

    return t;
  }

  // 3. flip color (equivalent to splittin temp 4-node in 2-3 trees)
  private void colorFlip(Node x) {
    x.right.color=BLACK;
    x.left.color=BLACK;
    x.color=RED;
  }

  // helper method:
  private boolean isRed(Node x) {
    // null links are black (no red null link dangling)
    if(x==null) return false;
    return x.color==RED;
  }

  // Search for the given key
  public Value get(Key key) {
    if(isEmpty()) throw new NoSuchElementException();
    // check if key is not null:
    if(key==null) throw new NullPointerException("Key cannot be null!");
    
    // recursively search the bst, rooted at the node "root":
    // return a Node with a key associated to it equals the given key, null otherwise:
    Node x=get(root, key);
    if(x==null) return null;
    return x.value;
  }

  // private helper search method:
  // search in the bst rooted at node x, for a node with key associated to it equal to the given key
  // search hit: return the node. search miss: return null
  private Node get(Node x, Key key) {
    // Base Case: search termination by a hiting null node (empty subtree) 
    if(x==null) return null; // search miss
    
    // Recurrence: directed search using compareTo method
    int cmp=key.compareTo(x.key);
    
    if (cmp==0) return x; // search hit (a node with a key associated to it equal to the given key was found)
    else if (cmp<0) 
      // search for the given key on the tree rooted at x.left (left subree of x). A bst with all nodes in it, have keys
      // associated to them less than the key associated to x
      return get(x.left, key);
    else 
      // resume the recursive search on bst rooted at x.right (right subtree of x)
      return get(x.right, key);
  }

  public boolean contains(Key key) {
    if(isEmpty()) throw new NoSuchElementException();
    return get(key)!=null;
  }
  public int size(){return size(root);}
  public boolean isEmpty(){return root==null;}

  /*
    NOTE: All these delete methods are suitable for BST and they are not LLRBBST delete methods!!!
          This means these deletion methods do NOT preserve:
	  1. The perfect black balance of bst, 
	  2. Red links always leaning left
	  3. No node has two red links connected to it

    Red Black Tree deletion such that preserve all llrbbst invariants would be implemented later this week.
  */

  // Hibbard Deletion for bsts
  public void delete(Key key) {
    if(isEmpty()) throw new NoSuchElementException();
    // check if key is not null:
    if(key==null) throw new NullPointerException("Key cannot be null!");

    //  delete a node with key associated to it equals to the given key from the bst rooted at the node "root"
    // update all links on the way from the deleted node to the root:
    root=delete(root, key);
  }
  // recursively search in bst rooted at node x for a node with a key associated to it, equals the given key
  // delete that node, update the links all the way back to the root of bst at x
  private Node delete(Node x, Key key) {
    // if the search hits the null node before finding any node with a key associated to them equal to the given key
    if(x==null) throw new NoSuchElementException();

    // directed search using comparison between the key associated with the node x and the given key
    int cmp=key.compareTo(x.key);
    if(cmp<0)  x.left=delete(x.left, key); // the node to be deleted must be on x's left subtree, and so x's left link is going to get modified
    else if(cmp>0) x.right=delete(x.right, key); // the node to be deleted must be on the x's right subtree, and so x's link to it right subtree must get modified
    else {
      // the node with the key associated to it equal the given key is hit:
      // 1. case 0, 1: check if node x has 0 or 1 child
      if(x.left==null) return x.right; // return a link to x's child -> x's caller would NOT refere to x no more, x would be collected by garbage collector
      if(x.right==null) return x.left; // return a link to x's child -> x's caller would NOT refere to x no more, x would be collected by garbage collector

      // if x has both left and right children:
      // replace it with its successor (min of its right subtree)
      Node t=x; // copy a reference to node x
      x=min(x.right); // search on bst rooted at x.right, to find a node with key associated with it is min among all node in that bst
      x.left=t.left; // left does not change
      x.right=delMin(t.right); // delete the node with the min key in right subtree of t, and return the link to modified t.right to assign to x.right 
    }
 
    // update the count:
    x.count=1+size(x.left)+size(x.right);
    return x;
  }
  public void delMin() {
    if(isEmpty()) throw new NoSuchElementException();
    // delete the node with key associated to it was the min key among all keys in the bst rooted at node "root"
    // return the link (a reference to a node) to the modified node (deleted node) back up to its parent and so forth recursively
    root=delMin(root);
  }
  // recursively search in a bst rooted at node x, for a node with a min key, return the link to its right subtree (it's min, so it has no left chid)
  private Node delMin(Node x) {
    // Base case: a node with no left child
    if(x.left==null) return x.right;
    
    // if x has a left subtree, the node with the min key must be on x's left subtree, and so x's left link must get modifed
    x.left=delMin(x.left);
    // update the count:
    x.count=1+size(x.left)+size(x.right);
    return x; // propagate the modification back up to the root of the bst 
  }
  public void delMax() {
    if(isEmpty()) throw new NoSuchElementException();
    root=delMax(root);
  }
  private Node delMax(Node x) {
    // Base case: a node with no right child
    if(x.right==null) return x.right;
    x.right=delMax(x.right);
    // update the count:
    x.count=1+size(x.left)+size(x.right);
    return x; // propagate the modification back up to the root of the bst 
  }

  // 2. Ordered key operations
  public Key min(){
    if(isEmpty()) throw new NoSuchElementException();
    
    // search in the bst rooted at node root, for the node which the key associated with it is the least among all
    // nodes in the tree (the left most node), if exists such a node, null otherwise (null case cannot happen because we have already checked for
    // tree not being empty)
    Node x=min(root);

    // return the key associated with such a node:
    return x.key; 
  }
  // recursive helper method:
  // find a node with min key in a bst rooted at node x
  private Node min(Node x) {
    // Base Case: stop recursion if x itself is the left most node in bst rooted at x
    if(x.left==null) return x;
    
    // otherwise: keep moving to the left and search for the min on the left subtrees
    return min(x.left);
  }
  public Key max(){
    if(isEmpty()) throw new NoSuchElementException();
    Node x=max(root);
    return x.key; 
  }
  // recursive helper method:
  // find a node with max key in a bst rooted at node x
  private Node max(Node x) {
    if(x.right==null) return x;
    return max(x.right);
  }

  public Key floor(Key key){
    if(isEmpty()) throw new NoSuchElementException();
    // check if key is not null:
    if(key==null) throw new NullPointerException("Key cannot be null!");
    
    // in a bst root at node root, recursively search for a node with the key associated to it being the
    // LARGEST key LESS THAN or EQUAL to the given key, null otherwise (search miss)
    Node x=floor(root, key);
    if(x==null) return null; // search miss
    return x.key; // search hit
  }
  // private recursive helper method to search in bst rooted at node x to find a node with key associated to it
  // be a floor of the given key:
  private Node floor(Node x, Key key) {
    // Base case: search miss
    if(x==null) return null; // hitting the null node before finding the proper node to return

    // Recurrence: guid the search using the comparsion between the key associated with node x and the given key
    int cmp=key.compareTo(x.key);

    if(cmp==0) return x; // search hit (there is no tighter floor than equal!)
    else if(cmp<0) 
      // if the search key is less than the key associated to x recursively search for a node on x's left subtree
      return floor(x.left, key);
    else {
      // if the search key is greater than the key associated with x:
      // 1. x itself is a candidate to be the floor of the search key
      // 2. there might be a tighter floor on x's right subtree
      Node t=floor(x.right, key);
      if(t!=null) return t;
      else return x;
    }
  }

  public Key ceiling(Key key) {
    if(isEmpty()) throw new NoSuchElementException();
    // check if key is not null:
    if(key==null) throw new NullPointerException("Key cannot be null!");
    
    // in a bst rooted at node root, recursively search for a node with key associated to it being
    // a ceiling to the given key, null otherwirse
    Node x=ceiling(root, key);
    if(x==null) return null; // search miss
    return x.key; // search hit: return the key associated to the found node
  }
  // private helper method:
  // recursively search in a bst rooted at x for a node with a key associated to it being 
  // ceiling to the given key:
  private Node ceiling(Node x, Key key) {
    // Base case: search miss
    if(x==null) return null; // if search hits a null node before finding a node with desired properties
    
    // Recurrence:
    // guided search through the bst rooted at x, using comparsion between the given key and 
    // the key associated wtih the nodex:
    int cmp=key.compareTo(x.key);

    // search hit:
    if(cmp==0) return x;
    // if the given ley is greater than the key associated with the node x, search for the desired node with theceiling key on x's right subtree
    else if(cmp>0) return ceiling(x.right, key);
    else {
      // if the given key is less than the key associated to x:
      // 1. x itselft is a candidate to be the desired node with the ceiling key
      // 2. there might be a tighter candidate on x's left subtree
      Node t=ceiling(x.left, key);
      if(t!=null) return t;
      else return x;
    }
  }

  public Key select(int k){
    if(isEmpty()) throw new NoSuchElementException();
    // check if k is a valid number:
    if(k<0||k>=size()) throw new NoSuchElementException();

    // in a bst rooted at node "root", recursively search for a node with key associated to it
    // being the k-th smallest key among all nodes in the bst
    Node x=select(root, k);
    // return that key
    return x.key;
  }
  // private recursive helper method, that search in bst rooted at node x for a node with a key associated to it
  // is kth smallest key among all keys in the bst
  private Node select(Node x, int k) {
    // if number of nodes with keys associated to them less than the node x is ke
    // then x would be the k-th smallest node
    if(size(x.left)==k)  return x;
    else if(k<size(x.left)) return select(x.left, k);
    else return select(x.right, k-1-size(x.left));
  }

  public int rank(Key key) {
    if(isEmpty()) throw new NoSuchElementException();
    // check if key is not null:
    if(key==null) throw new NullPointerException("Key cannot be null!");
    
    // in a bst rooted at node root, find the number of nodes with keys associated to them less than the given key
    int rank=rank(root, key);
    return rank;  
  }
  // private recursive helper method: in bst rooted at node x, find the number of nodes with keys associated to them less than the given key
  private int rank(Node x, Key key) {
    // Base case: search hit the null node before find any node with key associated to it less than the given key (the key was less than the min key in bst rooted at x) 
    if(x==null) return 0;
    // compare the given key with the key at the node x
    int cmp=key.compareTo(x.key);
    if(cmp==0) return size(x.left); // number of nodes in x's left subtree
    else if(cmp<0) return rank(x.left, key);
    // if the given key is greater than x, it's also greater than all nodes on x's left subtree
    else return 1+size(x.left)+rank(x.right, key);
  }

  // returns the number of nodes in the bst rooted at node "root" such that
  // the key associated to them lies within the range of given keys
  public int rangeCount(Key lo, Key hi) {
    if(isEmpty()) throw new NoSuchElementException();
    if(lo==null) throw new NullPointerException("Key cannot be null!");
    if(hi==null) throw new NullPointerException("Key cannot be null!");

    // compare the endpoints of the range:
    if(hi.compareTo(lo)<0)  return 0;
    // 1. find the rank of lo: number of nodes in bst rooted at node "root" with key associated to them less than the lo key
    // 2. find the rank of hi: number of nodes in bst rooted at node "root" with key associated to them less than the hi key
    // 3. number of nodes in bst rooted at the node "root" with keys within inclusive range[lo, hi]
    if(contains(hi)) return rank(hi)-rank(lo)+1;
    else return rank(hi)-rank(lo);
  }

  // Iterating over symbol table keys (basic st operation that has been specified in st interface)
  public Iterable<Key> keys(){
    if(isEmpty()) throw new NoSuchElementException();
    // instantiate a queue of Key objects
    Deque<Key> keys=new ArrayDeque<Key>(); // an empty q

    // pass the q to the recursive method that traverse the bst rooted at the node "root" from the root to its leaves
    // copy the node's key to and add them to the q
    inorder(root, keys);
    return keys;
  }
  
  private void inorder(Node x, Deque<Key> keys) {
    // if null node got hit return ;
    if(x==null) return ; // the traversal reaches the leaf node
    
    // Recurrence: 
    // traverse x's left subtree until there no more node remaining on the left
    inorder(x.left, keys);
    // add the key associated with the x to the q:
    keys.offer(x.key);
    // then traverse x's right subtree (all node with key associated to them greater than x's key)
    inorder(x.right, keys);
  }

  public Iterable<Key> keys(Key lo, Key hi){
    if(isEmpty()) throw new NoSuchElementException();
    if(lo==null) throw new NullPointerException("Key cannot be null!");
    if(hi==null) throw new NullPointerException("Key cannot be null!");

    // compare the endpoints of the range:
    List<Key> list=new LinkedList<Key>(); // an empty collection of keys
    if(hi.compareTo(lo)<0)  return list;

    // in a bst rooted at node "root", find all nodes with keys associated to them lies within
    // the given range [lo hi], add their corresponding key to the list
    keys(root, lo, hi, list);
    return list;
  }

  private void keys(Node x, Key lo, Key hi, List<Key> list) {
    // termination conditions:
    if(x==null) return ;
  }

  // Helper Methods:
  private int size(Node x) {
    if(x==null) return 0;
    return x.count;
  }

  // check if a bst rooted at the node root is bst (all keys associated to its nodes preserve search tree properties)
  private boolean isBST() {
     // check if the binary tree rooted at the node "root" is bst by examining all its nodes and check if satisfy binary search property (left < root < right)
     // starting from the root node, examing all node reachable from the root
     return isBST(root, null, null); // since root node can have any key and there is no range bound imposed to the root node, set min and max range to null
  }
  // check if the node x falls within the legal range, if so check then recursively check nodes on its left and right subtrees
  private boolean isBST(Node x, Key min, Key max) {
    // Base Case: an empty tree is a bst
    if(x==null) return true; // if search hits a leaf without violated the key range boundaries, the subtree rooted at x is a legal bst
    
    // Recurrence:
    if(x.key.compareTo(min)<=0 && min!=null) return false; // node x has key associated to it out of legal range (violates bst property of binary tree)
    if(x.key.compareTo(max)>=0 && max!=null) return false; // node x has key associated to it out of legal range (violates bst property of binary tree)

    // now node x fulfilled the constrint of being within legal range, now all nodes on its subtrees must be checked:
    return isBST(x.left, min, x.key) && isBST(x.right, x.key, max); 
  }
   
  // check if llrbbst is perfectly black balanced:
  private boolean isBalanced() {return isBalanced(root);}
  private boolean isBalanced(Node x) {
    // count the number of black edges in one arbitary route from the root to a leaf node (null node)
    int black=0;
    while(x!=null) {
      if(!isRed(x.left)) black++;
      x=x.left;
    }
    return isBalanced(root, black);
  }

  // traverse another arbitary root to leaf path and check if all black edges have been consumed
  private boolean isBalanced(Node x, int black) {
    if(x==null) return black==0; // reaching the null node, all black edges must have been processed, otherwise llrbbst is not in perfect black balanced
    if(!isRed(x.left)) black--;
    return isBalanced(x.left, black);
  }

  // a linear time algorithm to check if a bst rooted at node "root" is a "height" balanced bst
  // Hint: using a same function that recursively computed the height of bst
  private boolean isHbbst() {return h(root)!=Integer.MIN_VALUE;}
  private int h(Node x) {
    if(x==null) return -1; // to get 0 height for a single node bst
    
    // in order to compute the h of x, we require to compute the h of its subtrees:
    int l=h(x.left);
    // if subtree rooted at x.left is not height balanced:
    if(l==Integer.MIN_VALUE) return Integer.MIN_VALUE; // bst rooted ax x is not balanced
    
    // do the same computation on x's right subtree
    int r=h(x.right);
    // if subtree rooted at x.right is not height balanced:
    if(r==Integer.MIN_VALUE) return Integer.MIN_VALUE; // bst rooted ax x is not balanced

    // before computing the height of x, check if the height of its left and right subtree makes x a height balanced bst
    // 1. compare the height of x's left subtree and x's right subtree
    if(Math.abs(l-r)>1) return Integer.MIN_VALUE;
    // 2. compute the height of node x in terms of height of its subtree
    return 1+Math.max(l, r);
  }

  // iterative methods for get, and keys (iterative in order traversal of bst)
  public Value iGet(Key key) {
    if(isEmpty()) throw new NoSuchElementException();
    if(key==null) throw new NullPointerException("Key cannot be null!");
    
    // search for the given key in a bst rooted at node root
    Node x=iGet(root, key);
    // search miss:
    if(x==null) return null;
    return x.value;
  }
  private Node iGet(Node x, Key key) {
    while(x!=null) {
      // compare the key associated with the node x with then given key:
      int cmp=key.compareTo(x.key);
      if(cmp==0) return x; // node x is found with key associated to it equal to the given key
      else if(cmp<0) x=x.left;
      else x=x.right;
    }
    return x;
  }

  // 1. iterative inorder, preorder traversal
  // 2. level order traversal of bbst

  // Helper inner class:
  private class Node {
    // instance fields:
    private Key key;
    private Value value;
    private int count;
    private boolean color;
   
    // reference to left and right subtrees
    private Node left, right;

    // Constructors:
    public Node(){}
    public Node(Key key, Value value, boolean color) {
      this.key=key;
      this.value=value;
      this.color=this.color;
    }
  }
}
