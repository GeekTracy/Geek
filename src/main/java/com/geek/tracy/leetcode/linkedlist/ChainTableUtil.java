package com.geek.tracy.leetcode.linkedlist;

import com.geek.tracy.leetcode.linkedlist.bean.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tracy
 * @date 2023/12/15
 */
public class ChainTableUtil {

    public static ListNode init(int... val) {
        if (val == null || val.length == 0) {
            return null;
        }
        ListNode temp = null;
        for (int i = val.length - 1; i >= 0; i--) {
            ListNode curr = new ListNode(val[i]);
            curr.next = temp;
            temp = curr;
        }
        return temp;
    }

    public static void printChainTable(ListNode root) {
        if (root == null) {
            System.out.println("[]");
            return;
        }
        List<Integer> list = new ArrayList<>();
        ListNode temp = root;
        while (temp != null) {
            list.add(temp.val);
            temp = temp.next;
        }
        System.out.println(Arrays.toString(list.toArray()));
    }
}
