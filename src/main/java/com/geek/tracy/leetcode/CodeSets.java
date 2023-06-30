package com.geek.tracy.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 合集
 * @Author Tracy
 * @Date 2023/6/26
 */
public class CodeSets {

    @Test
    public void reconstructMatrixTest() {
        System.out.println(reconstructMatrix(2, 1, new int[]{1, 1, 1}));
        System.out.println(reconstructMatrix(2, 3, new int[]{1, 1, 1,}));
        System.out.println(reconstructMatrix(5, 5, new int[]{2, 1, 2, 0, 1, 0, 1, 2, 0, 1}));
    }


    /**
     * 1253. 重构 2 行二进制矩阵
     */
    public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
        // 数组是二进制数组，则数组的元素不是0，就是1，在colsum数组中的元素只可能是：0、1、2，
        int size = colsum.length;
        // colsum数组中，值为2的个数
        List<Integer> twoIndex = new ArrayList<>();
        List<Integer> oneIndex = new ArrayList<>();

        int[] upperArr = new int[size];
        int[] lowerArr = new int[size];

        for (int i = 0; i < size; i++) {
            if (colsum[i] == 2) {
                twoIndex.add(i);
            } else if (colsum[i] == 1) {
                oneIndex.add(i);
            }
        }
        // 1) upper + lower 不等于 colusum的元素之和，则返回空数组
        if (upper + lower != twoIndex.size() * 2 + oneIndex.size()) {
            return new ArrayList<>();
        }
        // 2) upper/lower 的值都要大于2的个数，因为colsum元素为2时，即upper,lower对应的二位二进制数组对应下标的值均为1，所以 {upper,lower} >= 2的个数
        if (upper < twoIndex.size() || lower < twoIndex.size()) {
            return new ArrayList<>();
        }
        for (Integer index : twoIndex) {
            upperArr[index] = 1;
            lowerArr[index] = 1;
        }

        int oneCount = upper - twoIndex.size();
        for (Integer index : oneIndex) {
            if (oneCount-- > 0) {
                upperArr[index] = 1;
            } else {
                lowerArr[index] = 1;
            }
        }
        List<List<Integer>> result = new ArrayList<>();
        result.add(toList(upperArr));
        result.add(toList(lowerArr));
        return result;
    }

    private List<Integer> toList(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int j : arr) {
            list.add(j);
        }
        return list;
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
        int sum = (int) ((1 + n) * n / 2.0);
        // 游标平移，左侧和小于右侧，则右移，反之左移，游标取折半中点
        int left = 1;
        int right = n;
        while (left <= right) {
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
     * 1 <= digits.length <= 100
     * 0 <= digits[i] <= 9
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
