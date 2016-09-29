/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class BinaryTreePost {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<Integer>();
        recursivePostOrder(root, list);
        return list;
    }
    
    private void recursivePostOrder(TreeNode root, List<Integer> list) {
        // base case:
        if(root==null) return ;
        recursivePostOrder(root.left, list);
        recursivePostOrder(root.right, list);
        list.add(root.val);
    }
}
