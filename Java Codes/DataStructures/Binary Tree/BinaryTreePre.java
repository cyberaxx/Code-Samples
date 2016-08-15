/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class BinaryTreePre {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<Integer>();
        // stack for recursive calls:
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        // copy of the root node:
        TreeNode currentNode = root;
        
        // base case:
        if(root==null) return list;
        
        // recursive calls:
        stack.push(root);
        while(!stack.isEmpty()) {
            // add the data to the list:
            currentNode = stack.pop();
            list.add(currentNode.val);            
            
            // recursive calls:
            // reverse the recursive calls to left and right, because 
            // while they are poping back they would be in correct order
            
            // recursive call on right
            if(currentNode.right!=null) {
                stack.push(currentNode.right);
            }
            
            // recursive call on left
            if(currentNode.left!=null) {
                stack.push(currentNode.left);
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
