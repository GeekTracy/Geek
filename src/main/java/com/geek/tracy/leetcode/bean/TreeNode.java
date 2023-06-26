package com.geek.tracy.leetcode.bean;

/**
 * @Author yang
 * @Date 2022/3/14
 */
public class TreeNode {
    // 节点值
    public int val;
    // 左子树
    public TreeNode left;
    // 右子树
    public TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
