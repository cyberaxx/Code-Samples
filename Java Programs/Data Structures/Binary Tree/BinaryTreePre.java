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
	// an empty list for node keys
        List<Integer> list=new ArrayList<Integer>(); 
        // if tree is empty return the list
        if(root==null) return list;

        // Otherwise:
        // Recursion stack of TreeNodes
        Deque<TreeNode> stack=new ArrayDeque<TreeNode>();
        // A set of TreeNodes to keep track of marked nodes
        HashSet<TreeNode> marked=new HashSet<TreeNode>();
        
        // starting from the root:
        // add root to the recursive stack (first recursive call on root)
        stack.push(root);
        
        // while recursive stack is not empty
        while(!stack.isEmpty()) {
            // sneek peek at the top of the stack (color it gray)
            TreeNode x=stack.peek();
            
	    /* D */
            // if x has not been visited already mark x as visited
            if(!marked.contains(x)) {
                marked.add(x);
                list.add(x.val);
            }
            
            // recursively visite x's l and r child
	    /* L */ //---> Deep LEFT
            if(x.left!=null && !marked.contains(x.left)) {
                stack.push(x.left);
            }
	    /* R */ //---> if not possible to go LEFT any further, then right
            else if(x.right!=null && !marked.contains(x.right)) {
                stack.push(x.right);
            }
            else stack.pop();
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
