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
                
        //empty list to maintain keys
        List<Integer> list=new ArrayList<Integer>(); 
        // recursion stack:
        Deque<TreeNode> stack=new ArrayDeque<TreeNode>();

	// similar to DFS, keep track of visited node not to visit any node more than once
        HashSet<TreeNode> marked=new HashSet<TreeNode>();
        
        // if tree is empty:
        if(root==null) return list; // equivalent to if(root==null) return ; in recursive approach
        
        // Otherwise: start recursion from the root node: preorder(root, list)
        stack.push(root);
        
        // while recursion stack is not empty
        while(!stack.isEmpty()) {
            // peek the top of the recursion stack
            TreeNode x=stack.peek();
            
            // D
            if(!marked.contains(x)) {
                list.add(x.val); // add its value to the list: list.add(x.val) in recursive approach
                marked.add(x);
            }
            
            // LR
            // if x has a left subtree
            if(x.left!=null && !marked.contains(x.left))
                stack.push(x.left); // preorder(x.left)
            else {
                // if x has no left subtree (x.left==null) go to the right
                if(x.right!=null && !marked.contains(x.right))
                    stack.push(x.right);
                    
                // Node x scanned and recursion has to unfold
                else {
                    // if x has no left and right subtree remove it from the stack
                    stack.pop();
                }
                
                
            }
        }
        
        return list;
        
    }
}


