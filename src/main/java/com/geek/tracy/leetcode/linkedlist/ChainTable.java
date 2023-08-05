package com.geek.tracy.leetcode.linkedlist;

import com.geek.tracy.leetcode.linkedlist.bean.ListNode;
import com.geek.tracy.leetcode.tree.bean.TreeNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Tracy
 * @Date 2023/7/31
 */
public class ChainTable {

    @Test
    public void mergeTwoListTest() {
        ListNode list1 = ListNode.inti(1, 2, 4);
        ListNode list2 = ListNode.inti(1, 3, 4);
        ListNode merge = mergeTwoLists(list1, list2);
        ListNode.printListNode(merge);
    }

    /**
     * 21. 合并两个有序链表
     * 思考：递归解法
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        Deque<ListNode> queue = new LinkedList<>();
        while (list1 != null || list2 != null) {
            if (list1 == null) {
                queue.offerLast(list2);
                list2 = list2.next;
            } else if (list2 == null) {
                queue.offerLast(list1);
                list1 = list1.next;
            } else {
                if (list1.val > list2.val) {
                    queue.offerLast(list2);
                    list2 = list2.next;
                } else {
                    queue.offerLast(list1);
                    list1 = list1.next;
                }
            }
        }
        ListNode head = queue.pollFirst();
        ListNode curr = head;
        while (!queue.isEmpty()) {
            curr.next = queue.pollFirst();
            curr = curr.next;
        }
        curr.next = null;
        return head;
    }


    @Test
    public void recorderListTest() {
        // 初始化链表
        ListNode head = ListNode.inti(1, 2, 3, 4, 5, 6);
        ListNode.printListNode(head);
        reorderList(head);
        ListNode.printListNode(head);
    }
    /**
     * 143. 重排链表
     *
     * 给定一个单链表 L 的头节点 head ，单链表 L 表示为：
     *
     * L0 → L1 → … → Ln - 1 → Ln
     * 请将其重新排列后变为：
     *
     * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
     * 不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     */
    public void reorderList(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        ListNode point = head;
        while (point != null) {
            list.add(point);
            point = point.next;
        }
        int start = 0;
        int end = list.size() - 1;
        while (start < end) {
            list.get(end).next = list.get(start).next;
            list.get(start).next = list.get(end);
            start++;
            if (start == end) {
                break;
            }
            end--;
        }
        list.get(start).next = null;
    }

    /**
     * 142. 环形链表 II
     */
    public ListNode detectCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (!set.add(head)) {
                return head;
            }
            head = head.next;
        }
        return null;
    }

    /**
     * 141. 环形链表
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        // 快慢指针
        ListNode slow = head;
        ListNode quick = head.next;
        while (quick != null || quick.next != null) {
            if (slow == quick) {
                return true;
            }
            slow = slow.next;
            quick = quick.next.next;
        }
        return false;
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

    /**
     * 445. 两数相加 II
     * 高位在链表表头，使用栈保存，FILO先进后出
     */
    public ListNode addTwoNumbersII(ListNode l1, ListNode l2) {
        Deque<ListNode> stack1 = new LinkedList<>();
        Deque<ListNode> stack2 = new LinkedList<>();

        while (l1 != null) {
            stack1.offerLast(l1);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.offerLast(l2);
            l2 = l2.next;
        }
        Deque<ListNode> result = new LinkedList<>();
        boolean more = false;
        // 遍历stack1 和 stack2,
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            int sum = 0;
            if (!stack1.isEmpty()) {
                sum += stack1.pollLast().val;
            }
            if (!stack2.isEmpty()) {
                sum += stack2.pollLast().val;
            }
            // 是否进位
            if (more) {
                sum++;
                more = false;
            }
            if (sum > 9) {
                more = true;
                sum -= 10;
            }
            result.offerLast(new ListNode(sum));
        }
        if (more) {
            result.offerLast(new ListNode(1));
        }
        // 栈result转为链表，表头为root
        ListNode root = result.pollLast();
        ListNode temp = root;
        while (!result.isEmpty()) {
            temp.next = result.peekLast();
            temp = result.pollLast();
        }
        return root;
    }

    @Test
    public void addTwoNumbersTest() {
//        l1 = [2,4,3]
//        l2 = [5,6,4]
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        addTwoNumbers(l1, l2);
    }

    /**
     * 2. 两数相加
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode tempRoot = new ListNode();
        ListNode tempL1 = l1;
        ListNode tempL2 = l2;
        ListNode resultTemp = tempRoot;
        boolean more = false;
        while (tempL1 != null || tempL2 != null || more) {
            int sum = 0;
            if (tempL1 != null) {
                sum += tempL1.val;
                tempL1 = tempL1.next;
            }
            if (tempL2 != null) {
                sum += tempL2.val;
                tempL2 = tempL2.next;
            }
            if (more) {
                sum++;
            }
            ListNode curr;
            if (sum > 9) {
                curr = new ListNode(sum - 10);
                more = true;
            } else {
                curr = new ListNode(sum);
                more = false;
            }
            resultTemp.next = curr;
            resultTemp = curr;
        }
        return tempRoot.next;
    }


    /**
     *
     * 817. 链表组件
     *
     * 给定链表头结点 head，该链表上的每个结点都有一个 唯一的整型值 。同时给定列表 nums，该列表是上述链表中整型值的一个子集。
     * <p>
     * 返回列表 nums 中组件的个数，这里对组件的定义为：链表中一段最长连续结点的值（该值必须在列表 nums 中）构成的集合。
     * <p>
     * 提示：
     * -| 链表中节点数为n
     * -| 1 <= n <= 10^4
     * -| 0 <= Node.val < n
     * -| Node.val 中所有值 不同
     * -| 1 <= nums.length <= n
     * -| 0 <= nums[i] < n
     * -| nums 中所有值 不同
     *
     * @param head
     * @param nums
     * @return
     */
    public int numComponents(ListNode head, int[] nums) {
        // 思路：二位数组array[][]，array[0][x]顺序记录链表的元素，array[x][1]标记是否为数组nums的元素
        // 遍历完一次array后，连续标记的为一个组件
        // 1)记录链表数据
        int size = 0;
        ListNode temp = head;
        while (temp != null) {
            size++;
            temp = temp.next;
        }
        int[][] array = new int[size][2];
        int index = 0;
        List<Integer> collect = Arrays.stream(nums).boxed().collect(Collectors.toList());

        // 遍历链表，保存到array数组
        while (head != null) {
            array[index][0] = head.val;
            if (collect.contains(head.val)) {
                array[index][1] = 1;
            }
            index++;
            head = head.next;
        }
        // 2)遍历array，获取组件个数
        int count = 0;
        for (int i = 0; i < array.length; ) {
            if (i < array.length && array[i][1] == 0) {
                i++;
                continue;
            }
            while (i < array.length && array[i][1] == 1) {
                i++;
            }
            count++;
        }
        return count;
    }
    // 注：做复杂了，值遍历一次即可

    /**
     * 示例1：
     * - 输入: head = [0,1,2,3], nums = [0,1,3]
     * - 输出: 2
     * - 解释: 链表中,0 和 1 是相连接的，且 nums 中不包含 2，所以 [0, 1] 是 nums 的一个组件，同理 [3] 也是一个组件，故返回 2。
     * 示例2：
     * - 输入: head = [0,1,2,3,4], nums = [0,3,1,4]
     * - 输出: 2
     * - 解释: 链表中，0 和 1 是相连接的，3 和 4 是相连接的，所以 [0, 1] 和 [3, 4] 是两个组件，故返回 2。
     */
    @Test
    public void numComponentsTest1() {
        // 初始化示例1 链表head
        ListNode listNode3 = new ListNode(3);
        ListNode listNode2 = new ListNode(2, listNode3);
        ListNode listNode1 = new ListNode(1, listNode2);
        ListNode listNode0 = new ListNode(0, listNode1);
        int componentsNum = numComponents(listNode0, new int[]{0, 1, 3});
        Assert.assertEquals(componentsNum, 2);
    }

    @Test
    public void numComponentsTest2() {
        // 初始化示例2 链表head
        ListNode listNode4 = new ListNode(4);
        ListNode listNode3 = new ListNode(3, listNode4);
        ListNode listNode2 = new ListNode(2, listNode3);
        ListNode listNode1 = new ListNode(1, listNode2);
        ListNode listNode0 = new ListNode(0, listNode1);
        int componentsNum = numComponents(listNode0, new int[]{0, 3, 1, 4});
        Assert.assertEquals(componentsNum, 2);
    }

    @Test
    public void numComponentsTest3() {
        // 初始化示例2 链表head
        ListNode listNode2 = new ListNode(2);
        ListNode listNode1 = new ListNode(1, listNode2);
        ListNode listNode0 = new ListNode(0, listNode1);
        int componentsNum = numComponents(listNode0, new int[]{1,0});
        Assert.assertEquals(componentsNum, 1);
    }
}
