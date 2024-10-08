package com.geek.tracy.leetcode.linkedlist;

import com.geek.tracy.leetcode.linkedlist.bean.ListNode;
import com.geek.tracy.leetcode.tree.bean.TreeNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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


    /**
     * 2181.合并零之间的节点
     * @param head
     * @return
     */
    public ListNode mergeNodes(ListNode head) {
        ListNode p = head;

        for (ListNode crr = head.next; crr != null; crr = crr.next) {
            if (crr.val != 0) {
                p.val += crr.val;
            } else {
                if (crr.next != null) {
                    p.next = crr;
                    p = crr;
                } else {
                    p.next = null;
                }
            }
        }
        return head;
    }


    @Test
    public void test_2487 () {
        //输入：head = [5,2,13,3,8]
        //输出：[13,8]
        removeNodes(ListNode.init(5,2,13,3,8));
        removeNodesII(ListNode.init(5,2,13,3,8));
    }

    /**
     * 2487.从链表中移除节点
     *
     * * 给你一个链表的头节点 head 。
     * * 移除每个右侧有一个更大数值的节点。
     * * 返回修改后链表的头节点 head
     * @param head
     * @return
     */
    public ListNode removeNodes(ListNode head) {
        // 遍历得到数值的顺序数组
        ListNode cur = head;
        List<Integer> list = new ArrayList<>();
        while (cur != null) {
            list.add(cur.val);
            cur = cur.next;
        }
        // 反向遍历数组，获取当前值右侧最大值
        int[] maxFromRight = new int[list.size()];
        int curMax = Integer.MIN_VALUE;
        for (int i = list.size() - 1; i >= 0; i--) {
            curMax = Math.max(list.get(i), curMax);
            maxFromRight[i] = curMax;
        }

        ListNode pre = new ListNode(0, head);
        ListNode res = pre;
        cur = head;
        int index = 0;
        while (cur != null) {
            if (cur.val < maxFromRight[index++]) {
                // 移除 cur
                pre.next = cur.next;
                // cur 后移
                cur = cur.next;
            } else {
                pre = cur;
                cur = cur.next;
            }
        }
        return res.next;
    }

    /**
     * 2487.从链表中移除节点
     *
     * 优化，去掉中间数组的过渡比较
     */
    public ListNode removeNodesII(ListNode head) {
        Deque<ListNode> deque = new ArrayDeque<>();
        while (head != null) {
            deque.push(head);
            head = head.next;
        }
        int maxFromRight = Integer.MIN_VALUE;
        ListNode pre = null;
        while (!deque.isEmpty()) {
            if (maxFromRight <= deque.peek().val) {
                ListNode cur = deque.pop();
                cur.next = pre;
                pre = cur;
                maxFromRight = pre.val;
            } else {
                // 移除
                deque.pop();
            }
        }
        return pre;
    }

    /**
     * 1019.链表中下一个最大节点
     * @param head
     * @return
     */
    public int[] nextLargerNodes(ListNode head) {
        // 反转链表
        ListNode pre = null, cur1 = head;
        int n = 0;
        while (cur1 != null) {
            ListNode nxt = cur1.next;
            cur1.next = pre;
            pre = cur1;
            cur1 = nxt;
            ++n;
        }
        head = pre;  // 反转后的表头
        int[] ans = new int[n]; // 保存各个节点的下一最大节点值
        Deque<Integer> st = new ArrayDeque<>(); // 单调栈（节点值），保存当前最大值
        for (ListNode cur = head; cur != null; cur = cur.next) {
            while (!st.isEmpty() && st.peek() <= cur.val)
                st.pop(); // 弹出无用数据
            // 逆向填充ans，取栈顶值
            ans[--n] = st.isEmpty() ? 0 : st.peek();
            st.push(cur.val);
        }
        return ans;
    }


    @Test
    public void test_024() {
        ListNode listNode = swapPairs(ListNode.init(1, 2, 3));
        ListNode.printListNode(listNode);
    }

    /**
     * 24.两两交换链表中的节点  -- 暴力总结解答，不推荐，思考递归解法
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        for (int i = 1; i < list.size(); i += 2) {
            list.get(i).next = list.get(i - 1);
            if (i + 2 < list.size()) {
                list.get(i - 1).next = list.get(i + 2);
            }
        }
        if (list.size() % 2 == 0) {
            list.get(list.size() - 2).next = null;
        }
        if (list.size() % 2 == 1 && list.size() >= 3) {
            list.get(list.size() - 3).next = list.get(list.size() - 1);
        }
        return list.get(1);
    }

    /**
     * 23.合并k个升序链表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        List<Integer> mergeList = new ArrayList<>();
        for (int i = 0; i < lists.length; i++) {
            ListNode temp = lists[i];
            while (temp != null) {
                mergeList.add(temp.val);
                temp = temp.next;
            }
        }
        mergeList.sort(Comparator.naturalOrder());
        ListNode root = new ListNode();
        for (int i = mergeList.size() - 1; i >= 0; i--) {
            ListNode newNode = new ListNode(mergeList.get(i));
            if (i != mergeList.size() - 1) {
                newNode.next = root.next;
            }
            root.next = newNode;
        }
        return root.next;

    }


    @Test
    public void test_019() {
        ListNode root = ListNode.init(1);
        ListNode.printListNode(root);
        ListNode.printListNode(removeNthFromEnd(root, 1));
    }

    /**
     * 19.删除链表的倒数第N个节点
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        int nthFromStart = count - n - 1;
        if (nthFromStart == -1) {
            return head.next;
        }
        temp = head;
        while (nthFromStart > 0) {
            temp = temp.next;
            nthFromStart--;
        }
        temp.next = temp.next.next;
        return head;
    }
    @Test
    public void recorderListTest() {
        // 初始化链表
        ListNode head = ListNode.init(1, 2, 3, 4, 5, 6);
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

    /**
     * 206.反转链表
     *
     * 双指针解法，pre、cur，
     * 例如：    1 --> 2 --> 3 --> 4 --> 5 --> null ，5个节点，最后一个节点为5；
     *         cur                             pre
     *         (后移)                          (后移)
     * 第一次：
     * null <-- 1     2 --> 3 --> 4 --> 5 --> null
     *          pre  cur
     * 第二次：
     * null <-- 1 <-- 2     3 --> 4 --> 5 --> null
     *                pre  cur
     *  ...  依次类推
     * 最后一次：
     * null <-- 1 <-- 2 <-- 3 <-- 4 <-- 5   null
     *                                 pre  cur
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;  // 指向前一节点，初始化为null，即反转前head的下一节点
        ListNode cur = head;  // 指向当前节点，初始指向head
        while (cur != null) {
            ListNode temp = cur.next;  // 暂存下一节点
            cur.next = pre;  // 当前节点next指向pre
            pre = cur;       // pre节点后移
            cur = temp;      // 当前节点指向暂存的下一节点
        }
        return pre;  // 此时cur指向null，pre指向头节点
    }

    /**
     * 25.K个一组反转链表
     *
     * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
     * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        Deque<ListNode> deque = new ArrayDeque<>();
        ListNode beforeEnd = null;  // 上一组最后一个
        ListNode nextFirst = null;  // 下一组第一个
        ListNode curEnd = null;     // 当前组最后一个
        ListNode ans = new ListNode(0, head);     // 哨兵
        int tempNum = k;
        while (head != null || !deque.isEmpty()) {
            if (head == null && tempNum > 0 && tempNum < k) {
                break; // 栈未放满k个元素，停止循环
            }
            if (tempNum == 0) {
                curEnd = deque.peek();
                nextFirst = curEnd.next;
                // 上一组最后一个为null时，则栈为第一组，第一组的栈顶元素即为反转后的头元素，设置为哨兵的下一个
                if (beforeEnd == null) {
                    ans.next = curEnd;
                }
                // 栈顶元素出栈
                ListNode pop = null;
                while (!deque.isEmpty()) {
                    pop = deque.pop();
                    pop.next = deque.peek();
                }
                pop.next = nextFirst;
                if (beforeEnd != null) {
                    beforeEnd.next = curEnd;
                }
                beforeEnd = pop;  // 上一组最后一元素即为当前的pop
                tempNum = k;
            } else if (tempNum > 0) {
                deque.push(head);
                head = head.next;
                tempNum--;
            }
        }
        return ans.next;
    }

    /**
     * 25.K个一组反转链表
     *
     * 指针法：
     *      [哨兵] -> [1 -> 2 -> 3] >断开< [4 -> 5 -> 6] -> [7 -> 8 -> 9] -> [...]
     *       ans       |         |        |
     *       pre     start     end      next
     *      [哨兵] -> [3 -> 2 -> 1] -> [4 -> 5 -> 6] >断开< [7 -> 8 -> 9] -> [...]
     *       ans                |      |         |          |
     *                          pre   start     end       next
     *
     *  说明：此前有【206.反转链表】可直接反转链表，此题分解为分段反转kGroup，依上图示，使用：pre/start/end/next标记，其中：
     *  [start - end]区段反转返回temp,   pre --> temp --> next --> ....，即可解决分段反转
     */
    public ListNode reverseKGroupII(ListNode head, int k) {
        ListNode ans = new ListNode(0, head);     // 哨兵
        ListNode pre = ans;
        ListNode start = ans;
        ListNode end = ans;
        ListNode next;

        while (end.next != null) {
            // 遍历链表，找去k个节点的最后一节点end
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            // 区段不足k个元素时，元素不反转，即 end!=null
            if (end == null) {
                break;
            }
            next = end.next; // 下一区段第一个元素
            start = pre.next;  // 区段起始元素，使用链表反转，表头为:reverseList(start)
            // 断开end与之后的连接
            end.next = null;
            // 调用链表反转方法
            pre.next = reverseList(start);
            start.next = next;   // 本区段反转后，start指向下一区段第一个元素
            // 指针开始后移，pre/end均指向当前区段反转后的最后一个元素，即反转前的start
            pre = start;
            end = start;
        }
        return ans.next;
    }

    @Test
    public void test_25() {
        // 输入：head = [1,2,3,4,5], k = 2
        //输出：[2,1,4,3,5]
        ListNode reverse1 = reverseKGroup(ListNode.init(1, 2, 3, 4, 5), 2);

        //输入：head = [1,2,3,4,5], k = 3
        //输出：[3,2,1,4,5]
        ListNode reverse2 = reverseKGroup(ListNode.init(1, 2), 3);
    }

    /**
     * 83.删除排序链表中的重复元素
     *
     * 给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            while (cur.next != null && cur.val == cur.next.val) {
                cur.next = cur.next.next;
            }
            cur = cur.next;
        }
        return head;
    }

    /**
     * 82.删除排序链表中的重复元素II
     *
     * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
     */
    public ListNode deleteDuplicatesII(ListNode head) {
        ListNode dummy = new ListNode(0, head); // 哨兵
        ListNode cur = dummy;
        ListNode flagNote = null;
        while (cur.next != null) {
            if (cur.next.next != null) {
                if (cur.next.val == cur.next.next.val) {
                    flagNote = cur.next;
                } else {
                    flagNote = null;
                    cur = cur.next;
                }
            } else {
                cur = cur.next;
            }
            // flagNote 不为空，且cur.next 值为flagNote.val时，移除cur.next
            while (flagNote != null && cur.next != null && cur.next.val == flagNote.val) {
                cur.next = cur.next.next;
            }
        }
        return dummy.next;
    }
    //  dummy --> 1 --> 1 --> 1 --> 2 --> --> 2 --> --> 3 --> null
    //     cur
    //     flagNote = 1

    @Test
    public void test_83() {
        ListNode root1 = deleteDuplicatesII(ListNode.init(1, 1, 1, 2, 2, 2, 3, 3, 4, 5));
        ListNode root2 = deleteDuplicatesII(ListNode.init(1, 2, 3, 4, 4, 4, 4, 5));
        System.out.println("check");
    }


}
