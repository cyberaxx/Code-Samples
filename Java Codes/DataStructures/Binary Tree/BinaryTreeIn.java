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
        // recursion stack
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();

        TreeNode currentNode = root;
        

        /*Inorder(root.left):*/
        // similar to recuresive calls:
        // starting from root and keep following left links (push them to stack)
	// reaching a null node:
        while(currentNode!=null) {
            stack.push(currentNode);
            currentNode=currentNode.left;            
        }
        
        // pop from the stack (similar to solved recursive calls)
        while(!stack.isEmpty()){
            currentNode=stack.pop();

	    /*print root.val*/
            list.add(currentNode.val);
            
            // check if it has a right subtree
            if(currentNode.right!=null) {

                // now go to the right subtree
                currentNode=currentNode.right;
                  
                // follow the recursion: inorder(root.right)
                while(currentNode!=null){
                    stack.push(currentNode);
                    currentNode=currentNode.left;
                }
            }
        }   // if currentNode does NOT have any right child, keep poping from the stack
            
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



