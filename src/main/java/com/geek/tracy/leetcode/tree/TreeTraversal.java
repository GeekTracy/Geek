package com.geek.tracy.leetcode.tree;

import com.geek.tracy.leetcode.tree.bean.TreeNode;

import java.util.List;

/**
 * 遍历方法
 *
 * @author mike
 * @date 2023-06-26
 */
public class TreeTraversal implements TreeTraversalWay {

    /**
     * 前序遍历 -- 递归
     * @param tree 树节点
     * @param list 遍历保存数据的数组
     */
    @Override
    public void preOrder(TreeNode tree, List<Integer> list) {
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
     * 中序遍历 -- 递归
     *
     * @param tree 树节点
     * @param list 遍历保存数据的数组
     */
    @Override
    public void inOrder(TreeNode tree, List<Integer> list) {
        // 左
        if (tree.left != null) {
            inOrder(tree.left, list);
        }
        // 根
        list.add(tree.val);
        // 右
        if (tree.right != null) {
            inOrder(tree.right, list);
        }
    }

    /**
     * 后序遍历 -- 递归
     *
     * @param tree 树节点
     * @param list 遍历保存数据的数组
     */
    @Override
    public void postOrder(TreeNode tree, List<Integer> list) {
        // 左
        if (tree.left != null) {
            postOrder(tree.left, list);
        }
        // 右
        if (tree.right != null) {
            postOrder(tree.right, list);
        }
        // 根
        list.add(tree.val);
    }
}
