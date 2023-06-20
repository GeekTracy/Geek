package com.geek.tracy.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 89. 格雷编码
 * @Author Tracy
 * @Date 2023/6/20
 */
public class Code_089 {

    /**
     * n 位格雷码序列 是一个由 2n 个整数组成的序列，其中：
     *  每个整数都在范围 [0, 2^n - 1] 内（含 0 和 2^n - 1）
     *  第一个整数是 0
     *  一个整数在序列中出现 不超过一次
     *  每对 相邻 整数的二进制表示 恰好一位不同 ，且
     *  第一个 和 最后一个 整数的二进制表示 恰好一位不同
     *  给你一个整数 n ，返回任一有效的 n 位格雷码序列 。
     *
     *  提示：
     *      1 <= n <= 16
     * @param n
     * @return
     */
    public List<Integer> grayCode(int n) {

        int numMax = (int) (Math.pow(2, n)) - 1;
        // 记录过程中，满足格雷序列的数；如需要记录所有格雷编码，可以定义一个：List<List<Integer>> list
        Deque<Integer> greyCode = new LinkedList<>();
        List<List<Integer>> list = new ArrayList<>();
        backTrace(numMax, 0, greyCode, list);
//        for (int i = 0; i <= numMax; i++) {
//            greyCode.clear();
//            backTrace(numMax, i, greyCode, list);
//        }
        System.out.println(list);
        return list.get(0);
    }

    private void backTrace(int numMax, int curNum, Deque<Integer> greyCode, List<List<Integer>> list) {
        if (greyCode.contains(curNum)) {
            return;
        }
        // 退出条件：格雷数组当前大小为numMax - 2，且不包含当前值，且当前curNum与数组头部互为格雷数
        if (greyCode.size() == numMax && isGrayNeighbour(greyCode.peekLast(), curNum) && isGrayNeighbour(greyCode.peekFirst(), curNum)) {
            greyCode.offerLast(curNum);
            list.add(new ArrayList<>(greyCode));
            return;
        }

        if (isGrayNeighbour(greyCode.peekLast(), curNum)) {
            greyCode.offerLast(curNum);
            // 遍历访问下一个
            for (int i = 0; i <= numMax; i++) {
                backTrace(numMax, i, greyCode, list);
            }
            // 回退
            greyCode.pollLast();
        }

    }

    /**
     * 判断2个数，是否恰好一位不同
     */
    private boolean isGrayNeighbour(Integer num1, Integer num2) {
        if (num1 == null) {
            return true;
        }
        // 将Integer转为2进制串，且位数相同，使用String.format("")
        String numString1 = Integer.toBinaryString(num1);
        String numString2 = Integer.toBinaryString(num2);
        int arrayMax = Math.max(numString1.length(), numString2.length());
        String parse = "%" + arrayMax + "s";
        numString1 = String.format(parse, numString1).replace(" ", "0");
        numString2 = String.format(parse, numString2).replace(" ", "0");
        int count = 0;
        for (int i = 0; i < arrayMax; i++) {
            if (count > 1) {
                return false;
            }
            // 记录不同的位数
            if (numString1.toCharArray()[i] != numString2.toCharArray()[i] ) {
                count++;
            }
        }
        return count == 1;
    }

    @Test
    public void test() {
        grayCode(2);
    }
}

