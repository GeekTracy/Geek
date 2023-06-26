package com.geek.tracy.leetcode.tree;

import com.geek.tracy.leetcode.tree.bean.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 树 -- 相关题目
 * @Author Tracy
 * @Date 2023/6/26
 */
public class CodeTreeSets {

    /**
     * 144. 二叉树的前序遍历
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        preOrder(root, list);
        return list;
    }

    /**
     * 前序遍历--递归
     * @param tree
     * @param list
     */
    private void preOrder(TreeNode tree, List<Integer> list) {
        // 根
        list.add(tree.val);
        // 左
        if (tree.left != null) {
            preOrder(tree.left, list);
        }
        // 右
        if (tree.right != null) {
            preOrder(tree.right, list);
        }
    }

    /**
     * 94. 二叉树的中序遍历
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        return null;
    }

    /**
     * 145. 二叉树的后序遍历
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        return null;
    }
}
