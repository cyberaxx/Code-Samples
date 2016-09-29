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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
	// Pre-order: DLR
        Deque<Integer> q=new ArrayDeque<Integer>();
        // add all keys in pre-order traversal in a queue
        for(int i=0; i<preorder.length; i++) 
            q.offer(preorder[i]);
        
	// initialize an empty binary tree
        TreeNode root=null;
        root=put(root, q, inorder, 0, inorder.length-1);
        return root;
    }
    
    private TreeNode put(TreeNode x, Deque<Integer> q, int[] inorder, int lo, int hi) {
        
	// if there is no more node left in the queue to add to the tree
        if(q.isEmpty()) return x;
        
	// if there is no node in x's subtrees
        if(hi<lo) return null;
        
        if(x==null) 
        {
            int key=q.poll();
            x=new TreeNode(key);
	    // find x's left and right subtrees        
            int i=find(inorder, key, lo, hi);
	    // PRE-order: DLR the head of the queue is L:
  	    // so construct the LEFT subtree FIRST
            x.left=func(x.left, q, inorder, lo, i-1);
            x.right=func(x.right, q, inorder, i+1, hi);
        }
        
        return x;
        
    }
    
    private int find(int[] inorder, int key, int lo, int hi) {
        for(int i=lo; i<=hi; i++)
            if(inorder[i]==key)
                return i;
        return -1;
    }
}
