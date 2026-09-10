package com.geek.tracy.leetcode.tree.bean;

import java.util.Deque;
import java.util.LinkedList;

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

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode inti(Integer[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(arr[0]);
        // 通过栈进行初始化
        Deque<TreeNode> deque = new LinkedList<>();
        deque.addFirst(root);
        int index = 1;
        int n = arr.length;
        while (!deque.isEmpty()) {
            TreeNode first = deque.pollFirst();
            if (index < n) {
                TreeNode left = arr[index] == null ? null : new TreeNode(arr[index]);
                index++;
                first.left = left;
                deque.addLast(left);
            }
            if (index < n) {
                TreeNode right = arr[index] == null ? null : new TreeNode(arr[index]);
                index++;
                first.right = right;
                deque.addLast(right);
            }
        }
        return root;
    }
}
