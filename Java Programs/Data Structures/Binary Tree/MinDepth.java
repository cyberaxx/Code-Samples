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
    public int minDepth(TreeNode root) {
      if(root==null) return 0;
      return min(root);
    }
    private int min(TreeNode x) {
        // base case: null node:
        if(x==null) return 0;
        // if tree rooted at x has no left subtree:
        if(x.left==null) return 1+min(x.right);
        // if tree rooted at x has no right subtree:
        if(x.right==null) return 1+min(x.left);
        return 1+Math.min(minDepth(x.left), minDepth(x.right));
    }
}
