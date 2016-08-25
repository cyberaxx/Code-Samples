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
    public boolean isValidBST(TreeNode root) {
        if(root==null) return true;
        return validate(root, root.val, root.val);
    }
    private static boolean validate(TreeNode root, int min, int max) {
        // BASE CASE: 1. EMPTY TREE,  TREE WITH ONE NODE
        if(root==null)  return true; // 0
        if(root.left==null && root.right==null) return true; // 1
        
        //RECURRENCE:
        // only has LEFT subtree: work with max
        if(root.right==null) {
            if(root.val<=root.left.val || root.left.val>=min)  return false;
            // update the min
            min=root.left.val;
            return validate(root.left, min, root.val);
        }
        // only has RIGHT subtree:
        if(root.left==null) {
            if(root.val>=root.right.val || root.right.val<=max)  return false;
            // update the max
            max=root.right.val;
            return validate(root.right, root.val, max);
        }
        // bothe left and right subtree
        else {
            if(root.val<=root.left.val || root.val>=root.right.val || root.left.val>=min || root.right.val<=max)  return false;
            min=root.left.val;
            max=root.right.val;
            return validate(root.left, min, root.val) && validate(root.right, root.val, max);
        }
    }
}
