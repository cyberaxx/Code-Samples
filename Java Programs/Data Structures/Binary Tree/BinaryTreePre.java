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
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list=new ArrayList<Integer>(); // an empty list node keys
        // if tree is empty return the list
        if(root==null) return list;
        
        // Otherwise:
        // Recursion stack of TreeNodes
        Deque<TreeNode> stack=new ArrayDeque<TreeNode>();
        TreeNode x=root;
        
        while(x!=null || !stack.isEmpty()) {
            if(x==null) {
                x=stack.pop();
                x=x.right;
            }
            else {
                list.add(x.val);
                stack.push(x);
                x=x.left;
            }
        }
        return list;
    }

    private void recursivePreOrder(TreeNode root, List<Integer> list) {
        // base case:
        if(root==null) return ;
        list.add(root.val);
        recursivePreOrder(root.left, list);
        recursivePreOrder(root.right, list);
    }
}
