package com.geek.tracy.leetcode.tree;

import com.geek.tracy.leetcode.tree.bean.TreeNode;

import java.util.List;

/**
 * 树的遍历方法
 * @author mike
 * @date 2023/6/26
 */
public interface TreeTraversalWay {

    /**
     * 前序遍历 -- 递归
     * @param tree 树节点
     * @param list 遍历保存数据的数组
     */
    void preOrder(TreeNode tree, List<Integer> list);

    /**
     * 中序遍历 -- 递归
     * @param tree 树节点
     * @param list 遍历保存数据的数组
     */
    void inOrder(TreeNode tree, List<Integer> list);

    /**
     * 后序遍历 -- 递归
     * @param tree 树节点
     * @param list 遍历保存数据的数组
     */
    void postOrder(TreeNode tree, List<Integer> list);

    /**
     * 前序遍历 -- 迭代，非递归
     *
     * @param root 树节点
     */
    List<Integer> preOrderIteration(TreeNode root);

    /**
     * 中序遍历 -- 迭代，非递归
     *
     * @param root 树节点
     */
    List<Integer> inOrderIteration(TreeNode root);

    /**
     * 后序遍历 -- 迭代，非递归
     *
     * @param root 树节点
     */
    List<Integer> postOrderIteration(TreeNode root);
}
