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
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root==null) return false;
        return pathSum(root, sum);
    }
    private boolean pathSum(TreeNode x, int sum) {
        // Base case: termination condition for routing the tree
        if(x==null) return sum==0;

        // Recurrence: there exist a path from x to a leaf such that
        // the sum of nodes will equal to sum, if there exist such a path with a reduced sum
        // from x's left child OR from x's right child or both such that 
        sum=sum-x.val;

        // if x has only one subtree:
        // only right subtree: only right search
        if(x.left==null) return pathSum(x.right, sum);
        // only left subtree: only left search
        if(x.right==null) return pathSum(x.left, sum);
        
        // if x has both subtrees:
        return pathSum(x.left, sum) || pathSum(x.right, sum);
    }
}
