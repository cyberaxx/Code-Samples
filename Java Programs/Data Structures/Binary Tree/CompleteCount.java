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
    public int countNodes(TreeNode root) {
        // termination case:
        if(root==null) return 0;
        
        // 1. if root only has a left subtree:
        if(root.right==null)
          // count the number of complete nodes on root's left subtree
          return 1+countNodes(root.left);
          
        // 2. if root only has a right subtree:
        if(root.left==null)
          // count the number of complete nodes on root's right subtree
          return 1+countNodes(root.right);
          
        // 3. if root has both left and right subtrees
        // root itself is a complete node: add count of compelete nodes on its subtress to it
        return 1+countNodes(root.left)+countNodes(root.right);
    }   
}
