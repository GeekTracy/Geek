package com.geek.tracy.leetcode;

import com.geek.tracy.leetcode.tree.bean.ListNode;
import com.geek.tracy.leetcode.tree.bean.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 面试题 04.03. 特定深度节点链表
 * @Author Tracy
 * @Date 2023/6/26
 */
public class Code_0403 {
    /**
     * 二叉树：层序遍历框架如下：
     */
    @Test
    public void listOfDepthTest() {
        Deque<TreeNode> deque = new LinkedList<>();
        // 具体代码框架如下：
        while(!deque.isEmpty()) {
            int size = deque.size();
            // list 保存某一层所有数据
            List<TreeNode> list = new ArrayList<>();
            //size个元素左右节点分别入队
            while (size-- > 0) {
                TreeNode node = deque.poll();
                list.add(node);
                if(node.left != null)
                    deque.add(node.left);
                if(node.right != null)
                    deque.add(node.right);
            }
            //此时list就是某一层所有数据
        }

    }

    /**
     * 04.03. 特定深度节点链表
     *
     *
     * 具体代码框架如下：
     *         while(!deque.isEmpty()) {
     *             int size = deque.size();
     *             // list 保存某一层所有数据
     *             List<TreeNode> list = new ArrayList<>();
     *             //size个元素左右节点分别入队
     *             while (size-- > 0) {
     *                 TreeNode node = deque.poll();
     *                 list.add(node);
     *                 if(node.left != null)
     *                     deque.add(node.left);
     *                 if(node.right != null)
     *                     deque.add(node.right);
     *             }
     *             //此时list就是某一层所有数据
     *         }
     *
     */
    public ListNode[] listOfDepth(TreeNode tree) {
        if (tree == null) {
            return new ListNode[]{};
        }
        Deque<TreeNode> deque = new LinkedList<>();

        List<ListNode> list = new ArrayList<>();

        deque.offer(tree);
        // 使用2层while循环，进行dfs深度优先遍历，利用栈每次放当前层的节点遍历循环达到：层序遍历
        // deque栈，每次遍历放入当前层的节点，dummy.next为当前层链表的表头元素
        while (!deque.isEmpty()) {
            int size = deque.size();
            // dummy:傀儡，挂名代表，这里代表链表的伪表头，实际表头为dummy.next
            ListNode dummy = new ListNode(0);
            // 当前节点对象
            ListNode currNode = dummy;
            while (size-- > 0) {
                TreeNode node = deque.poll();
                currNode.next = new ListNode(node.val);
                // 当前对象节点后移
                currNode = currNode.next;
                // 左子树压栈
                if (node.left != null) {
                    deque.offer(node.left);
                }
                // 右子树压栈
                if (node.right != null) {
                    deque.offer(node.right);
                }
            }
            list.add(dummy.next);
        }
        return list.toArray(new ListNode[0]);
    }

}
