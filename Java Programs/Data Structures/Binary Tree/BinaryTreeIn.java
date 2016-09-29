/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class BinaryTreeIn {
    public List<Integer> inorderTraversal(TreeNode root) {
	// iterator:
        List<Integer> list = new ArrayList<Integer>();
        return list;
    }

    // recursive version
    private void inorder(TreeNode node, List<Integer> list) {
        // base case:
        if(node==null) return ;
        inorder(node.left, list);
        list.add(node.val);
        inorder(node.right, list);
    }
}    



