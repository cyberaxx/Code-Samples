/*
https://leetcode.com/problems/validate-binary-search-tree/

Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.

*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

public class Verification {
    public boolean isValidBST(TreeNode root) {
        // extreme test case: empty tree is a BST
        if(root==null) return true;
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    // helper method to validate BST recursively
    private static boolean validate(TreeNode root, long min, long max) {
        // BASE CASE: where to stop division:
        // 1. EMPTY TREE
        // 2. TREE WITH 1 NODE
        if(root==null) return true;// 0 node is BST
        if(root.left==null && root.right==null) return true; // 1 node is a BST
        
        // RECURRENCE:
        // 1. only left subtree is NOT null
        if(root.right==null) {
            // check if BST condition is violated:
            if(root.val<=root.left.val || (long)root.left.val<=min) return false;
            // update max
            return validate(root.left, min, (long)root.val);
        }
        // 2. only right subtree is NOT null
        if(root.left==null) {
            // check if BST condition is violated:
            if(root.val>=root.right.val || (long)root.right.val>=max) return false;
            // update min
            return validate(root.right, (long)root.val, max);
        }
        
        // 3. have both left and right subtrees are not null:
        // check if BST condition is violated:
        if(root.val<=root.left.val || root.val>=root.right.val || (long)root.right.val>=max || (long)root.left.val<=min) return false;
        return validate(root.left, min, (long)root.val) && validate(root.right, (long)root.val, max);
    }
}
