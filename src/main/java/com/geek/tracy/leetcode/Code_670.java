package com.geek.tracy.leetcode;


import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 670. 最大交换
 *
 * @Author Tracy
 * @Date 2022/9/13
 */
public class Code_670 {
    /**
     * 给定一个非负整数，你至多可以交换一次数字中的任意两位。返回你能得到的最大值。
     * 注：给定数字的范围是 [0, 10^8]
     * <p>
     * <p>
     * 示例 1 :
     * 输入: 2736
     * 输出: 7236
     * 解释: 交换数字2和数字7。
     * <p>
     * 示例 2 :
     * 输入: 9973
     * 输出: 9973
     * 解释: 不需要交换。
     */
    public int maximumSwap(int num) {
        if (num == 0) {
            return num;
        }
        // 转为List<Integer>数组
        Deque<Integer> deque = new LinkedList<>();
        while (true) {
            if (num / 10 > 0) {
                deque.offerFirst(num % 10);
                num /= 10;
            } else {
                deque.offerFirst(num);
                break;
            }
        }

        if (deque.size() == 1) {
            return num;
        }

        List<Integer> list = new ArrayList<>(deque);
        int min = 0;
        int max = list.size() - 1;
        int left = 1;
        int right = deque.size() - 2;
        while (left < right) {
            if (list.get(min) > list.get(left)) {
                min = left++;
                continue;
            }
            if (list.get(max) < list.get(right)) {
                max = right;
                continue;
            }
        }

        return 0;
    }

    /**
     * 暴力解法
     * 数字为 0 - 9，可转为char
     */
    public int maximumSwap1(int num) {
        // 转为数组
        char[] numChar = Integer.toString(num).toCharArray();
        for (int i = 0; i < numChar.length; i++) {
            for (int j = i + 1; j < numChar.length; j++) {

            }
        }

        return 0;

    }

    @Test
    public void test() {
       Integer ii1 = 12;
       Integer ii2 = 13;
        String ss = String.valueOf(ii1 + ii2);
        System.out.println(ss);
        System.out.println(Integer.valueOf(ss) + 5);


    }
}
