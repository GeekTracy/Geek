package com.geek.tracy.leetcode;

import com.geek.tracy.leetcode.bean.ListNode;
import com.geek.tracy.leetcode.bean.TreeNode;
import org.junit.Test;

import java.util.*;

/**
 * 合集
 * @Author Tracy
 * @Date 2023/6/26
 */
public class CodeSets {


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
     *
      while(!queue.isEmpty()) {
          int size = queue.size();
          // list 保存某一层所有数据
          List<TreeNode> list = new ArrayList<>();
          //size个元素左右节点分别入队
          while (size-- > 0) {
              TreeNode node = queue.poll();
              list.add(node);
              if(node.left != null)
                  queue.add(node.left);
              if(node.right != null)
                  queue.add(node.right);
          }
          //此时list就是某一层所有数据
      }
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


    /**
     * 2485. 找出中枢整数 --测试
     */
    @Test
    public void pivotIntegerTest() {
        // 输入：n = 8，输出：6
        //解释：6 是中枢整数，因为 1 + 2 + 3 + 4 + 5 + 6 = 6 + 7 + 8 = 21 。
        System.out.println(pivotInteger(8));
        //输入：n = 1,输出：1
        //解释：1 是中枢整数，因为 1 = 1 。
//        System.out.println(pivotInteger(1));
        // 输入：n = 4,输出：-1
        //解释：可以证明不存在满足题目要求的整数。
        System.out.println(pivotInteger(4));
    }

    /**
     * 2485. 找出中枢整数
     * 提示：
     *      1 <= n <= 1000
     */
    public int pivotInteger(int n) {
        if (n == 1) {
            return 1;
        }
        // 总和
        int sum = (int) ((1 + n) * n /2.0);
        // 游标平移，左侧和小于右侧，则右移，反之左移，游标取折半中点
        int left = 1;
        int right = n;
        while(left <= right) {
            int pivot = (left + right + 1) / 2;
            int leftSum = (1 + pivot) * pivot / 2;
            int rightSum = sum - leftSum + pivot;
            if (leftSum == rightSum) {
                return pivot;
            } else if (leftSum < rightSum) {
                left = pivot + 1;
            } else {
                right = pivot - 1;
            }
        }
        return -1;
    }

    /**
     * 66. 加一 --测试
     */
    @Test
    public void plusOneTest() {
        System.out.println(Arrays.toString(plusOne01(new int[]{8, 9, 9, 9})));
        System.out.println(Arrays.toString(plusOne01(new int[]{2, 3, 4, 5, 5, 6, 6})));
        System.out.println(Arrays.toString(plusOne01(new int[]{9, 9, 9})));
    }

    /**
     * 66. 加一
     * 提示：
     *      1 <= digits.length <= 100
     *      0 <= digits[i] <= 9
     */
    public int[] plusOne(int[] digits) {
        // 记录数组末尾数字为9的个数
        int count = 0;
        int length = digits.length;
        for (int i = length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                count++;
            } else {
                break;
            }
        }
        if (count == 0) {
            digits[length - 1] = digits[length - 1] + 1;
            return digits;
        } else {
            for (int i = count; i > 0; i--) {
                digits[length - i] = 0;
            }
            if (length > count) {
                digits[length - count - 1] = digits[length - count - 1] + 1;
                return digits;
            } else {
                int[] newDigits = new int[digits.length + 1];
                newDigits[0] = 1;
                return newDigits;
            }
        }

    }

    /**
     * 看题解后的优化解法
     */
    public int[] plusOne01(int[] digits) {
        int length = digits.length;
        for (int i = length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                digits[i] = 0;
            } else {
                digits[i] += 1;
                return digits;
            }
        }
        int[] newDigits = new int[length + 1];
        newDigits[0] = 1;
        return newDigits;
    }

}
