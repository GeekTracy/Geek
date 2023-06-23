package com.geek.tracy.leetcode;

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
        for (int i = 0; i <= numMax; i++) {
            greyCode.clear();
            backTrace(numMax, i, greyCode, list);
        }
        System.out.println(list);
        return list.get(0);
    }

    private void backTrace(int numMax, int curNum, Deque<Integer> greyCode, List<List<Integer>> list) {
        if (list.size() > 0) {
            return;
        }
        // 退出条件：格雷数组当前大小为numMax，且不包含当前值，且当前curNum与数组头部互为格雷数
        if (greyCode.size() == numMax && isGrayNeighbour(greyCode.peekLast(), curNum) && isGrayNeighbour(greyCode.peekFirst(), curNum)) {
            greyCode.offerLast(curNum);
            list.add(new ArrayList<>(greyCode));
            return;
        }

        // 遍历访问下一个
        for (int i = 0; i <= numMax; i++) {
            if (greyCode.contains(i) || curNum == i) {
                continue;
            }
            if (isGrayNeighbour(greyCode.peekLast(), curNum)) {
                greyCode.offerLast(curNum);
            } else {
                // 不满足条件时，需要跳过
                continue;
            }
            backTrace(numMax, i, greyCode, list);
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
        System.out.println(grayCode02(10));
    }

    /**
     * 格雷编码：方法二：动态规划法，寻找G（n） 与G（n-1）的关系
     *  寻找规律：G(n)为G(n-1)拼接【Gr(n-1)每一位加2^n-1】，其中Gr表示G的转置数列，如：G(1)=[0,1]，则Gr[1]=[1,0]
     *  其中：G(0)=[0],G(1)=[0,1]或[1,0]
     */
    public List<Integer> grayCode02(int n) {
        List<Integer> grayCode = new ArrayList<>();
        // 从G（0）开始
        grayCode.add(0);
        for (int i = 1; i <= n; i++) {
            // 偏移量 2^n-1
            int offset = 1 << (i - 1);
            for (int j = offset - 1; j >= 0; j--) {
                // 将G(n-1)倒序取值+offset添加至数组后即可得到G(n)
                grayCode.add(grayCode.get(j) + offset);
            }
        }
        return grayCode;
    }

    /**
     * 格雷编码：方法三：有方法二的基础，可以使用递归，换一种写法
     * 提示：
     *   1 <= n <= 16
     */
    public List<Integer> grayCode03(int n) {
        // 递归出口
        if (n == 1) {
            return new ArrayList<Integer>(){{add(0);add(1);}};
        }
        // 当前为G(n-1)的值
        List<Integer> grayBeforeCurrent = grayCode(n - 1);
        // 偏移量
        int offset = 1 << (n - 1);
        int size = grayBeforeCurrent.size();
        // G(n-1)数列倒置加offset，拼接到当前数组后，即为G(n)
        for (int i = size - 1; i >= 0; i--) {
            grayBeforeCurrent.add(grayBeforeCurrent.get(i) + offset);
        }
        return grayBeforeCurrent;
    }


    @Test
    public void test01() {
        grayCode03(2);
    }


}

