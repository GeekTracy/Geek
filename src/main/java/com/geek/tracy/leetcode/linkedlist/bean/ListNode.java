package com.geek.tracy.leetcode.linkedlist.bean;

/**
 * 链表
 * @Author Tracy
 * @Date 2022/10/12
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    /**
     * 初始化链表，返回表头节点
     */
    public static ListNode inti(Integer... val) {
        if (val.length == 0) {
            return null;
        }
        ListNode head = new ListNode(val[0]);
        ListNode curr = head;
        for (int i = 1; i < val.length; i++) {
            curr.next = new ListNode(val[i]);
            curr = curr.next;
        }
        return head;
    }

    /**
     * 打印链表
     */
    public static void printListNode(ListNode head) {
        if (head == null) {
            System.out.println("null (空链表)");
            return;
        }
        ListNode curr = head;
        StringBuilder res = new StringBuilder();
        while (curr != null) {
            res.append(curr.val).append("-->");
            curr = curr.next;
        }
        String result = res.substring(0, res.length() - 3);
        System.out.println(result);
    }

}
