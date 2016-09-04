import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class BSTReconstruction {

  public static <Key extends Comparable<Key>> Iterable<Key> bst(Deque<Key> preOrder) {
    // Stack of Keys to kee item on left subtree of the root
    Deque<Key> stack=new ArrayDeque<Key>(); // empty stack
    List<Key> inOrder=new ArrayList<Key>(); // Lit of keys in order
    
    // check if the preOrder iterable is empty:
    if(preOrder==null)  return inOrder; // return an empty list

    // Otherwise:
    // add the first key in preOrder Queue to inOrder stack
    stack.push(preOrder.poll());

    while(!stack.isEmpty() && !preOrder.isEmpty()) {
      // compare the item in the preOrder Queue with the top of the stack
      int cmp=preOrder.peek().compareTo(stack.peek());
      // if the preOrder key was less than the top of the stack (this is a left child of the key on the top)
      if(cmp<0)  stack.push(preOrder.poll());
      // if the preOrder key was greater than the top of the stack (it is right child of one on the keys in the stack)
      if(cmp>0)  inOrder.add(stack.pop());
      // this implementation will ignore duplicate keys (assuming keys are distinct)
    }

    // while stack is not empty: all keys in the stack are in reverse order:
    while(!stack.isEmpty()) {  inOrder.add(stack.pop());  }

    // preorder traversal: Data L-subtree R-subtree
    return inOrder;
  }

/*
BST reconstruction. 
Given the preorder traversal of a BST (not including null nodes), reconstruct the tree.


Level-order traversal reconstruction of a BST. 
Given a sequence of keys, design a linear-time algorithm to determine whether it is the level-order traversal of some BST (and construct the BST itself).
*/





  public static void main(String[] args) {    
    Deque<String> preOrder=new ArrayDeque<String>(Arrays.asList("z","x","t","s","q","h","d","a"));
    System.out.println(bst(preOrder));
    System.out.println(bst(new ArrayDeque<Integer>(Arrays.asList(5,3,2,4,6,7))));
  }
}
