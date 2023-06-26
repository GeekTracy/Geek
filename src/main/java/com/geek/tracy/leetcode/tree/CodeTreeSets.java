package com.geek.tracy.leetcode.tree;

import com.geek.tracy.leetcode.tree.bean.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 树 -- 相关题目
 * @Author Tracy
 * @Date 2023/6/26
 */
public class CodeTreeSets extends TreeTraversal {

    /**
     * 144. 二叉树的前序遍历
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        // 前序遍历
        preOrder(root, list);
        return list;
    }

    /**
     * 94. 二叉树的中序遍历
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        // 中序遍历
        inOrder(root, list);
        return list;
    }

    /**
     * 145. 二叉树的后序遍历
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        // 后续序遍历
        postOrder(root, list);
        return list;
    }
}
