package com.geek.tracy.leetcode.tree;

import com.geek.tracy.leetcode.tree.bean.TreeNode;

import java.util.*;

/**
 * 遍历方法
 *
 * @author mike
 * @date 2023-06-26
 */
public class TreeTraversal implements TreeTraversalWay {

    /**
     * 前序遍历 -- 递归
     *
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
     *
     * @param root 树节点
     */
    public List<Integer> preOrderIteration(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        // 使用栈，进栈顺序：右、左，前序遍历保持左节点在栈顶
        Deque<TreeNode> deque = new LinkedList<>();
        // 初始状态，队列只有根节点root
        deque.offerFirst(root);
        while (!deque.isEmpty()) {
            TreeNode queueHead = deque.pollFirst();
            if (queueHead != null) {
                // 先获取根节点数据
                result.add(queueHead.val);
                // 后压栈，压栈顺序：右、左，前序遍历保持左节点在栈顶
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
     *
     * @param root 树节点
     */
    public List<Integer> inOrderIteration(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        // 使用栈
        Deque<TreeNode> deque = new LinkedList<>();
        while (!deque.isEmpty() || root != null) {
            // 前序遍历，左--根--右，节点指针指向左子节点
            while (root != null) {
                deque.offerFirst(root);
                root = root.left;
            }
            // 此时栈顶元素，即为最左子节点，访问之
            root = deque.pollFirst();
            result.add(root.val);
            // 访问右子节点
            root = root.right;
        }
        return result;
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

    /**
     * 后序遍历 -- 迭代，非递归
     *
     * @param root 树节点
     */
    public List<Integer> postOrderIteration(TreeNode root) {
        List<Integer> ans = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode prev = null;
        //主要思想：
        //由于在某颗子树访问完成以后，接着就要回溯到其父节点去
        //因此可以用prev来记录访问历史，在回溯到父节点时，可以由此来判断，上一个访问的节点是否为右子树
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            //从栈中弹出的元素，左子树一定是访问完了的
            root = stack.pop();
            //现在需要确定的是是否有右子树，或者右子树是否访问过
            //如果没有右子树，或者右子树访问完了，也就是上一个访问的节点是右子节点时
            //说明可以访问当前节点
            if (root.right == null || prev == root.right) {
                ans.add(root.val);
                //更新历史访问记录，这样回溯的时候父节点可以由此判断右子树是否访问完成
                prev = root;
                root = null;
            } else {
                //如果右子树没有被访问，那么将当前节点压栈，访问右子树
                stack.push(root);
                root = root.right;
            }
        }
        return ans;
    }

}
