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
    public List<List<Integer>> levelOrder(TreeNode root) {
        
        List<List<Integer>> list=new LinkedList<List<Integer>>();
        // if tree is empty return list
        if(root==null) return list;
        
        Deque<TreeNode> grey=new ArrayDeque<TreeNode>(); // an empty processing q
        Deque<TreeNode> black=new ArrayDeque<TreeNode>(); // an empty processed q
        
        // strating from root
        grey.offer(root);

        while(!grey.isEmpty() || !black.isEmpty()) {
            // while processing q is not empty:
            while(!grey.isEmpty()) {
                // remove the head of the processing q:
                TreeNode x=grey.poll();
                // add it to the processed q
                black.offer(x);
            }
            
 	    // List of all nodes in each level
            List<Integer> level=new ArrayList<Integer>();
            
            while(!black.isEmpty()) {
                // remove the head of black q
                TreeNode x=black.poll();
                // add it to the list
                level.add(x.val);
                
                // add all its adjacent nodes to the grey
                if(x.left!=null)
                    grey.offer(x.left);
                if(x.right!=null)
                    grey.offer(x.right);
            }
                
	    // add the level to the level order traversal
            list.add(level);
        }
        
        return list;
    }
}
