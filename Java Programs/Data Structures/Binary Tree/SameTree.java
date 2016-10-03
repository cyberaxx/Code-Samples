/*
  11. sameTree()
  Given two binary trees, return true if they are structurally identical -- they are made of nodes with the same values
  arranged in the same way. (Thanks to Julie Zelenski for suggesting this problem.)
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
public class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // termination condition: hitting the null node on one of two trees
        if(p==null||q==null) {
          if(p==null&&q==null) return true;
          else return false;
        }

        // if both p and q are both not null:
        // 1. compare their value:
        if(p.val!=q.val) return false;
        // 2. if p and q have same value, recursively compare their subtrees:
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
