package com.geek.tracy.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * @author mike
 * @date 2024/9/24
 */
public class CodeSets2 {


    @Test
    public void test_3175() {
        Assert.assertEquals(2, findWinningPlayer(new int[]{16,4,7,17}, 562084119));
    }

    /**
     * 3175.找到连续赢k场比赛的第一位玩家
     * <p>直接使用队列模拟，当k非常大时，就会空转比较；分析便知，遍历一圈，未得到连续赢了k次的赢家，这时数组的最大值即是那个赢家</p>
     */
    public int findWinningPlayer(int[] skills, int k) {
        // 模拟整个比较过程，定义一个队列，对于k非常大时有空的消耗；所以遍历一次所以数据即可，增加一个退出的判断
        int winner = 0;
        int count = 0;
        for (int i = 1; i < skills.length && count < k; i++) {
            if (skills[winner] < skills[i]) {
                winner = i;
                count = 0;
            }
            // 赢的回合数增加
            count++;
        }

        return winner;
    }


    /**
     * 910.最小差值 II
     * <p>
     * 一）对数组进行排序；对于数组的操作总的有两种请情况：
     * 1）全部增或全部减；2）部分增，部分减，按贪心思想，对于排序后的数组，前面增，后面减，这样可以减小最大值与最小值的差值；
     * </p>
     * <p>
     * 二）全部进行增减k、或者减少k的操作，则最大值与最小值之间的差值不改变；
     * </p>
     * 三）左边的增减k，右边的减少k，针对这种情况，遍历整个数组，每次基于相邻的2个数进行遍历，i下标及其左边的增加k，i+1下标及其右边减少k，每次判断最大最小值，并对差值进行比较，遍历完成即可得到最小差值；
     * </p>
     *
     * @param nums
     * @param k
     * @return
     */
    public int smallestRangeII(int[] nums, int k) {
        // 对数组进行排序
        Arrays.sort(nums);
        int len = nums.length;
        // 针对情况1，全部增减或全部减少，计算差值
        int ans = nums[len - 1] - nums[0];
        // 针对情况2，遍历正数组
        for (int i = 0; i < len - 1; i++) {
            int max = Math.max(nums[i] + k, nums[len - 1] - k);
            int min = Math.min(nums[i + 1] - k, nums[0] + k);
            ans = Math.min(max - min, ans);
        }
        return ans;

    }

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
                        left++;
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
