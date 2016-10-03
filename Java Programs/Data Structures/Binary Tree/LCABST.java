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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
      if(p==null) throw new NullPointerException();
      if(q==null) throw new NullPointerException();
      
      if(root==null) return null;
      TreeNode x=lca(root, p, q);
      return x;
    }
    
    // Search for p and q in a bst rooted at x
    private TreeNode lca(TreeNode x, TreeNode p, TreeNode q) {
        // termination condition:
        if(x==null) return null; // search miss

        // if p and q are on the same side of x:
        // 1. both p and q are on x's left:
        if(x.val>p.val && x.val>q.val) return lca(x.left, p,q); // find their lca on x's left subtree
        // 2. both p and q are on x's right:
        else if(x.val<p.val && x.val<q.val)  return lca(x.right, p,q); // find their lca on x's right subtree
	// 3. p and q are on two different subtrees of x
        else return x; 
    }
}
