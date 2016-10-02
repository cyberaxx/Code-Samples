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
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> list=new LinkedList<List<Integer>>();
        
        // corner case:
        if(root==null) return list;
        
        Deque<Integer> path=new ArrayDeque<Integer>();
        
        pathFinder(root, sum, list, path);
        return list;
    }
    
    private void pathFinder(TreeNode x, int sum, List<List<Integer>> list, Deque<Integer> path) {
        // termination condition: hitting the null node:
        if(x==null) return ;
        
        // for all non-null nodes update the value of sum:
        sum-=x.val;
        
        // at the leaf node if the remaining ends up to 0, 
        // add the leaf node to the path and add the path to the collection
        if(x.left==null && x.right==null) {
            if(sum==0) {
                path.push(x.val);
                
                // create a list from the path stack
                List<Integer> route=new LinkedList<Integer>(path);
                // reverse the order of nodes in the list (because of the stack order):
                Collections.reverse(route);
                // add the route to the list of all routes
                list.add(route);
                
                path.pop();
            }
            return ;
        }
        
        // in all internal nodes: add the node temporarily to the path
        path.push(x.val);
        if(x.left==null)
            pathFinder(x.right, sum, list, path);
        else if(x.right==null)
            pathFinder(x.left, sum, list, path);
        else {
            pathFinder(x.left, sum, list, path);
            pathFinder(x.right, sum, list, path);
        }
        path.pop();
        
    }
}
