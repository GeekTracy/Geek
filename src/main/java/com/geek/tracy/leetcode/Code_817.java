package com.geek.tracy.leetcode;

import com.geek.tracy.leetcode.bean.ListNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 817. 链表组件
 *
 * @Author Tracy
 * @Date 2022/10/12
 */
public class Code_817 {


    /**
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
    public void test1() {
        // 初始化示例1 链表head
        ListNode listNode3 = new ListNode(3);
        ListNode listNode2 = new ListNode(2, listNode3);
        ListNode listNode1 = new ListNode(1, listNode2);
        ListNode listNode0 = new ListNode(0, listNode1);
        int componentsNum = numComponents(listNode0, new int[]{0, 1, 3});
        Assert.assertEquals(componentsNum, 2);
    }

    @Test
    public void test2() {
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
    public void test3() {
        // 初始化示例2 链表head
        ListNode listNode2 = new ListNode(2);
        ListNode listNode1 = new ListNode(1, listNode2);
        ListNode listNode0 = new ListNode(0, listNode1);
        int componentsNum = numComponents(listNode0, new int[]{1,0});
        Assert.assertEquals(componentsNum, 1);
    }
}


