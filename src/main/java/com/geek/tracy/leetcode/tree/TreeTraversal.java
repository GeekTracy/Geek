package com.geek.tracy.leetcode.tree;

import com.geek.tracy.leetcode.tree.bean.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
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
        if (tree == null) {
            return;
        }
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
     * 前序遍历 -- 迭代，非递归
     * @param root 树节点
     */
    public List<Integer> preOrderIteration(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        // 使用队列，FIFO，进队顺序：右、左，前序遍历保持左节点在队首
        Deque<TreeNode> deque = new LinkedList<>();
        // 初始状态，队列只有根节点root
        deque.offerFirst(root);
        while (!deque.isEmpty()) {
            TreeNode queueHead = deque.pollFirst();
            if (queueHead != null) {
                result.add(queueHead.val);
                // 进队顺序：右、左，前序遍历保持左节点在队首
                deque.offerFirst(queueHead.right);
                deque.offerFirst(queueHead.left);
            }
        }
        return result;
    }

    /**
     * 中序遍历 -- 递归
     *
     * @param tree 树节点
     * @param list 遍历保存数据的数组
     */
    @Override
    public void inOrder(TreeNode tree, List<Integer> list) {
        if (tree == null) {
            return;
        }
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
     * 中序遍历 -- 迭代，非递归
     * @param root 树节点
     */
    public List<Integer> inOrderIteration(TreeNode root) {
        return null;
    }

    /**
     * 后序遍历 -- 递归
     *
     * @param tree 树节点
     * @param list 遍历保存数据的数组
     */
    @Override
    public void postOrder(TreeNode tree, List<Integer> list) {
        if (tree == null) {
            return;
        }
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
