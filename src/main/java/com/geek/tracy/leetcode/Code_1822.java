package com.geek.tracy.leetcode;

/**
 * 1822. 数组元素积的符号
 * @Author Tracy
 * @Date 2022/10/27
 */
public class Code_1822 {

    /**
     * 1)数组中有0，则返回0，数组中负数个数为偶数返回1，否者返回-1
     */
    public int arraySign(int[] nums) {
        int result = 1;
        for (int num : nums) {
            if (num == 0) {
                return 0;
            }
            if (num < 0) {
                result *= -1;
            }
        }
        return result;
    }
}
