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
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        
        // list of all levels:
        List<List<Integer>> list=new LinkedList<List<Integer>>();
        
        // if tree is empty:
        if(root==null) return list;
        
        // q of nodes under process
        Deque<TreeNode> grey=new ArrayDeque<TreeNode>();
        // q of processed nodes
        Deque<TreeNode> black=new ArrayDeque<TreeNode>();
        // stack of levels
        Deque<List> stack=new ArrayDeque<List>();
        
        // add the root to the processing q:
        grey.offer(root);
        
        // until all nodes in the binary tree are processed
        while(!grey.isEmpty() || !black.isEmpty()) {
            // while processing queue is not empty
            while(!grey.isEmpty()){
                // remove the head of q
                TreeNode x=grey.poll();
                // add it to black q
                black.offer(x);
            }
            
            // list of node keys in the level:
            List<Integer> level=new LinkedList<Integer>();
            
            // while the processed q is not empty
            while(!black.isEmpty()) {
                // remove the head and all node adjacent to it to the grey q
                TreeNode x=black.poll();
                // add x to the list
                level.add(x.val);
                
                // add nodes adjacent to x to the processing q
                if(x.left!=null)
                    grey.offer(x.left);
                if(x.right!=null)
                    grey.offer(x.right);
            }
            stack.push(level);
        }
        
        while(!stack.isEmpty()) {
            list.add(stack.pop());
        }
        
        return list;
        
    }
}
