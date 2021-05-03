package net.kenyang.algorithm;

public class SymmetricTree {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return helper(root.left, root.right);
    }

    public boolean helper(TreeNode leftNode, TreeNode rightNode) {
		
		// 1. leaf case
		
        if (rightNode == null && leftNode == null)
            return true;
		//2. oneside is null
        if (rightNode == null)
            return false;
        if (leftNode == null)
            return false;

        //3. equal nodes and recurse
        return leftNode.val == rightNode.val
                && helper(leftNode.left, rightNode.right)
                && helper(leftNode.right, rightNode.left);

    }
}
