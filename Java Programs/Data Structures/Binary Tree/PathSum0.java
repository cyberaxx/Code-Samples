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
    public int sumOfLeftLeaves(TreeNode root) {
        if(root==null) return 0;
	
	// a collection of keys associated to all left leaves        
        Deque<Integer> q=new ArrayDeque<Integer>();

        // traverse the tree and keep track of the link the traversal comming into a given node 
        // (whether is a left link or a right link)
        leftSum(root, true, q);
        
	// sum all keys associated to the left leaves
        int sum=0;
        for(Integer key:q)
          sum+=key;
          
        return sum;
    }

    private void leftSum(TreeNode x, boolean fromLeft, Deque<Integer> q) {
        if(x==null) return ;
        
	// add keys assoicated to the left leaf to the q
        if(x.left==null && x.right==null && fromLeft)
            q.offer(x.val);
        
	// traverse the entire tree from x's left link and its right link
        leftSum(x.left,true,q);
        leftSum(x.right,false,q);
    }
}
