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
	// termination condition:
        if(root==null) return null; // search miss
	
	// 1. search hit, for eigher p or q (assuming keys are distinct)
        // the found key is lca (the root of the other node subtree)
        if(root==p || root==q) return root;

	// 2. otherwise search for lca of p and q on root's subtree(s)
	// 2a. root has only one subtree
        if(root.left==null) return lowestCommonAncestor(root.right, p,q);
        if(root.right==null) return lowestCommonAncestor(root.left, p,q);
	// 2b. if root has both left and right subtree, root itself is candidate of being
 	// the lca:
        TreeNode l=lowestCommonAncestor(root.left, p,q);
        TreeNode r=lowestCommonAncestor(root.right, p,q);
        if(l==null) return r;
        if(r==null) return l;
        return root; // both q and p have been found on root subtrees
    }
}
