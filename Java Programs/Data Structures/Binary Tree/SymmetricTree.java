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
    public boolean isSymmetric(TreeNode root) {
        return sym(root, root);
    }
    
    private boolean sym(TreeNode x1, TreeNode x2) {
        // termination case: if one of the subtrees hit the null node
        if(x1==null || x2==null) {
            if(x1==null && x2==null) return true;
            else return false;
        }
        
        // recursion:
        // compare x1 and x2 values
        if(x1.val!=x2.val) return false;
        
        // recurse on x1 and x2 subtrees
        return sym(x1.left, x2.right) && sym(x1.right, x2.left);
    }
}
