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
    public int sumNumbers(TreeNode root) {
        
        Deque<Integer> nums=new ArrayDeque<Integer>();
        pathSum(root, 0, nums);
        
        int sum=0;
        for(Integer key:nums)
            sum+=key;
        
        return sum;
    }
    
    private void pathSum(TreeNode x, int num, Deque<Integer> nums) {
	// base case: empty tree
        if(x==null) return ;

        // For all non-null nodes x:
        // add x's digit to the number
        num=num*10+x.val;
        
        // at leaf node, add the num to the q of nums
        if(x.left==null && x.right==null) {
            nums.offer(num); // add the full-blown number to the nums q
            return ;
        }
        
        // at internal nodes, add a digit to the num and search for the next digit on x's subtrees
	// 1. x has only right subtree
        else if(x.left==null)
            pathSum(x.right, num, nums);
        
	// 2. x has only left subtree
        else if(x.right==null)
            pathSum(x.left, num, nums);

	// 3. x has both left and right subtrees
        else {
            pathSum(x.left, num, nums);
            pathSum(x.right, num, nums);
        }
    }

}
