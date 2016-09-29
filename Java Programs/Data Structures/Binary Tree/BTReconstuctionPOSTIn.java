/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Deque<Integer> stack=new ArrayDeque<Integer>(); // an empty stack of integer
        for(int i=0; i<postorder.length; i++) {
            stack.push(postorder[i]); // push postorder keys to the stack
        }
        
        // Create a new null TreeNode call it root to characterize the binary tree
        TreeNode root=null; // empty binary tree
        // populate the tree using pre and post order traversals:
        root=put(root, stack, inorder, 0, inorder.length-1);
        return root;
    }
    
    // recursively build the binary tree from nodes in the stack
    private TreeNode put(TreeNode x, Deque<Integer> stack, int[] inorder, int lo, int hi) {
        // No more node left in the stack of nodes
        if(stack.isEmpty()) return x; // nothing to add to the subtree rooted at x
        
        // if nothing left in the inorder array to add to x as a child:
        if(hi<lo) return null;
        
        if(x==null) {
            // if Tree is empty
            int key=stack.pop();
            x=new TreeNode(key); // create the tree
            int r=find(inorder, key, lo, hi);
            
            // populate its left and right subtrees recursively: LRD
            // Create the right subree FIRST (stack top is R now)
            x.right=put(x.right, stack, inorder, r+1, hi);
            // Create the right subree SECOND
            x.left=put(x.left, stack, inorder, lo, r-1);
            
        }
        
        return x;
    }
    
    // Keys can be duplicates: lo and hi take care of that problem
    private int find(int[] inorder, int key, int lo, int hi) {
        for(int i=lo; i<=hi; i++) 
            if(key==inorder[i])
                return i;
        return -1;
    }
}
