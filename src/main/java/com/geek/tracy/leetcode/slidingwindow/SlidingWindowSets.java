package com.geek.tracy.leetcode.slidingwindow;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 滑动窗口
 *
 * @author mike
 * @date 2024/9/2
 */
public class SlidingWindowSets {

    /**
     * ### 1）定长滑动窗口############################################################################
     */
    @Test
    public void test_1004() {
        Assert.assertEquals(6, longestOnes(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2));
        Assert.assertEquals(10, longestOnes(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3));
    }

    /**
     * 1004.最大连续的个数III
     * <p>定长滑动窗口</p>
     */
    public int longestOnes(int[] nums, int k) {
        int len = nums.length;
        int left = 0;
        int ans = 0;
        int cntZero = 0;
        for (int right = 0; right < len; right++) {
            // 1.入窗
            if (nums[right] == 0) {
                cntZero++;
            }

            // 2.更新：左边界右移，更新ans
            while (cntZero > k) {
                if (nums[left] == 0) {
                    cntZero--;
                }
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    /**
     * 2024.考试的最大困扰度. 窗口中T或F个数到达K次时，窗口达到最大值
     * <p>定长滑动窗口</p>
     */
    public int maxConsecutiveAnswers(String answerKey, int k) {
        int len = answerKey.length();
        int left = 0;
        int ans = 0;
        int cntT = 0;
        int cntF = 0;
        for (int right = 0; right < len; right++) {
            // 计算T、F出现的次数
            if (answerKey.charAt(right) == 'T') {
                cntT++;
            } else {
                cntF++;
            }

            // 1.入窗：F、T出现的次数都超过了k次时，则移动左边界，控制窗口大小
            while (cntT > k && cntF > k) {
                // 移动左边界
                if (answerKey.charAt(left) == 'T') {
                    cntT--;
                } else {
                    cntF--;
                }
                left++; // 左边界右移
            }
            // 2.更新
            ans = Math.max(ans, right - left + 1);
            // 3.出窗

        }
        return ans;
    }

    @Test
    public void test_2090() {
        getAverages(new int[]{7, 4, 3, 9, 1, 8, 5, 2, 6}, 3);
    }


    /**
     * 2090.半径为k的子数组平均值
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] getAverages(int[] nums, int k) {
        int len = nums.length;
        int[] ans = new int[len];
        Arrays.fill(ans, -1);
        if (len <= k * 2) {
            return ans;
        }
        long sum = 0;
        int winLen = k * 2 + 1;
        // l, i, r ,
        for (int i = 0; i < len; i++) {
            // 1入窗口，窗口长度为： k * 2 + 1
            sum += nums[i];
            if (i < winLen - 1) {
                continue;
            }
            // 2更新ans[i]的值
            int index = i - k;
            ans[index] = (int) (sum / winLen);
            // 3出窗口
            sum -= nums[i - k * 2];
        }
        return ans;
    }


    /**
     * 1343.大小为k且平局值大于等于阈值的子数组数目
     *
     * @param arr
     * @param k
     * @param threshold
     * @return
     */
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int len = arr.length;
        int sum = 0;
        int count = 0;
        for (int i = 0; i < len; i++) {
            // 入窗
            sum += arr[i];
            if (i < k - 1) {
                continue;
            }
            // 2更新个数
            if (sum * 1.0 / k >= threshold) {
                count++;
            }
            // 3出窗
            sum -= arr[i - k + 1];

        }
        return count;
    }

    @Test
    public void test_643() {
        Assert.assertEquals(-1, findMaxAverage(new int[]{-1}, 1));
    }

    /**
     * 643. 子数组最大平均数 I
     * 给你一个由 n 个元素组成的整数数组 nums 和一个整数 k 。
     * <p>
     * 请你找出平均数最大且 长度为 k 的连续子数组，并输出该最大平均数。
     * <p>
     * 任何误差小于 10^5 的答案都将被视为正确答案。
     *
     * @param nums
     * @param k
     * @return
     */
    public double findMaxAverage(int[] nums, int k) {
        int len = nums.length;
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            // 1进入窗口
            sum += nums[i];
            if (i < k - 1) {
                continue;
            }
            // 2更新平均数
            maxSum = Math.max(maxSum, sum);
            // 3出窗口
            sum -= nums[i - k + 1];
        }
        return 1.0 * maxSum / k;
    }

    /**
     * 1984. 学生分数的最小差值
     * 排序+滑动窗口
     *
     * @param nums
     * @param k
     * @return
     */
    public int minimumDifference(int[] nums, int k) {
        Arrays.sort(nums);
        if (k == 1) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int i = k - 1; i < nums.length; i++) {
            // 1入窗
            // 2更新min
            min = Math.min(min, nums[i] - nums[i - k + 1]);
            // 3出窗
        }
        return min;
    }

    /**
     * 2269.找到一个数字的 K 美丽值
     * <p>
     * 一个整数 num 的 k 美丽值定义为 num 中符合以下条件的 子字符串 数目：
     * <p>
     * 子字符串长度为 k 。
     * 子字符串能整除 num 。
     * 给你整数 num 和 k ，请你返回 num 的 k 美丽值。
     * <p>
     * 注意：
     * <p>
     * 允许有 前缀 0 。
     * 0 不能整除任何值。
     * 一个 子字符串 是一个字符串里的连续一段字符序列。
     * <p>
     * 滑动窗口
     */
    public int divisorSubstrings(int num, int k) {
        String numStr = String.valueOf(num);
        int count = 0;
        for (int i = 0; i <= numStr.length() - k; i++) {
            String sub = numStr.substring(i, i + k);
            Integer cur = Integer.valueOf(sub);
            if (cur != 0 && num % cur == 0) {
                count++;
            }
        }
        return count;
    }

    @Test
    public void test_2269() {
        int i = divisorSubstrings(609070001, 3);
    }


    @Test
    public void test_1456() {
//        Assert.assertEquals(4, maxVowels("weallloveyou", 7));
        Assert.assertEquals(3, maxVowels("abciiidef", 3));
    }

    public int maxVowels_(String s, int k) {
        // 滑动窗口解答，套路格式：入窗-更新max-出窗
        int max = 0;
        char[] arr = s.toCharArray();
        int vowels = 0;
        for (int i = 0; i < arr.length; i++) {
            // 1 入窗
            if ('a' == arr[i] || 'e' == arr[i] || 'i' == arr[i] || 'o' == arr[i] || 'u' == arr[i]) {
                vowels++;
            }
            // 入窗个数不足k个，继续入窗
            if (i < k - 1) {
                continue;
            }
            // 2 更新(入窗个数刚好k个)
            max = Math.max(max, vowels);

            // 3 出窗
            int outIndex = i - k + 1;
            if ('a' == arr[outIndex] || 'e' == arr[outIndex] || 'i' == arr[outIndex] || 'o' == arr[outIndex] || 'u' == arr[outIndex]) {
                vowels--;
            }
        }
        return max;

    }

    /**
     * 1456.定长子串中元音的最大数目
     * <p>
     * 给你字符串 s 和整数 k 。
     * 请返回字符串 s 中长度为 k 的单个子字符串中可能包含的最大元音字母数。
     * 英文中的 元音字母 为（a, e, i, o, u）。
     * <p>
     * 移动窗口
     */
    public int maxVowels(String s, int k) {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("a");
        hashSet.add("e");
        hashSet.add("i");
        hashSet.add("o");
        hashSet.add("u");

        char[] arr = s.toCharArray();
        // toHere[i]表示节点i之前的所有元素中元音的个数
        int[] toHere = new int[arr.length];
        toHere[0] = hashSet.contains(String.valueOf(arr[0])) ? 1 : 0;
        for (int i = 1; i < arr.length; i++) {
            String curr = String.valueOf(arr[i]);
            if (hashSet.contains(curr)) {
                toHere[i] = toHere[i - 1] + 1;
            } else {
                toHere[i] = toHere[i - 1];
            }
        }
        int max = toHere[k - 1];
        for (int i = k; i < toHere.length; i++) {
            max = Math.max(max, toHere[i] - toHere[i - k]);
        }
        return max;
    }

    /**
     * ### 2）不定长滑动窗口############################################################################
     */


    /**
     * ### 3）单序列双指针############################################################################
     */


    /**
     * ### 4）双序列双指针############################################################################
     */

    /**
     * ### 5）三指针############################################################################
     */
}
