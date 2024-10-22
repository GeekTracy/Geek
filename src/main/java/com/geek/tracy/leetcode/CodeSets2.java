package com.geek.tracy.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author mike
 * @date 2024/9/24
 */
public class CodeSets2 {


    @Test
    public void test_3191() {
        // 输入：nums = [0,1,1,1,0,0]
        //
        //输出：3
        Assert.assertEquals(3, minOperations(new int[]{0, 1, 1, 1, 0, 0}));
    }

    /**
     * 3191.使二进制数组全部等于1的额最少操作次数I
     *
     * @param nums
     * @return
     */
    public int minOperations(int[] nums) {
        // 固定窗口，找到第一个为0的数，进行滑动窗口处理
        // 窗口数据，遇到0就出窗
        int left = 0;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (left == i && nums[i] == 1) {
                left++;
                continue;
            }
            // 进窗
            if (i - left < 2) {
                continue;
            }
            // 出窗
            if (i - left == 2) {
                sum++;
                for (int j = left; j <= i; j++) {
                    if (j == left && nums[left] == 0) {
                        left ++;
                    }
                    nums[j] = nums[j] ^ 1;
                }
            }
        }

        return left == nums.length ? sum : -1;
    }

    /**
     * 2207.字符串中最多数目的子序列
     *
     * @param text
     * @param pattern 长度为 2
     * @return
     */
    public long maximumSubsequenceCount(String text, String pattern) {
        // pattern 长度为 2，将其插入到text中，令x = pattern[0]，y = pattern[1]。
        char x = pattern.charAt(0);
        char y = pattern.charAt(1);
        long ans = 0;
        int cntx = 0;
        int cnty = 0;
        for (char c : text.toCharArray()) {
            // 遇到y时，则x的个数代表了xy子串的个数
            if (c == y) {
                ans += cntx;
                cnty++;
            }
            if (c == x) {
                cntx++;
            }
        }
        // 因为可以在text中插入x或者y，若将x插入到最左侧，则ans增加cnty个，反之增加cntx个，即判断cntx和cnty哪个更大即可
        return ans + Math.max(cntx, cnty);
    }
}
