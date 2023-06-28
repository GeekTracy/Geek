package com.geek.tracy.leetcode.dp;

import org.junit.Test;

/**
 * 动态规划 Dynamic Programming 集合
 * @Author Tracy
 * @Date 2023/6/27
 */
public class Code_DpSets {


    @Test
    public void maximumSumTest() {
        System.out.println("except num: 3");
        System.out.println(maximumSum(new int[]{2,1,-2,-5,-2}));
//        System.out.println("except num: 4");
//        System.out.println(maximumSum(new int[]{1, -2, 0, 3}));
//        System.out.println("except num: 3");
//        System.out.println(maximumSum(new int[]{1,-2,-2,3}));
//        System.out.println("except num: -1");
//        System.out.println(maximumSum(new int[]{-1,-1,-1,-1}));

    }

    /**
     * ----- Kadane's Algorithm 算法   -----
     * Kadane是卡内基梅隆大学的教授，这个算法是为了解决最大子序列的和（maximum subarray）提出的
     *
     * ** Kadane's Algorithm 算法的伪代码如下：
     * --   def max_subarray(A):
     * --       max_ending_here = max_so_far = A[0]
     * --       for x in A[1:]:
     * --           max_ending_here = max(x, max_ending_here + x)
     * --           max_so_far = max(max_so_far, max_ending_here)
     * --       return max_so_far
     *
     * **/

    /**
     * 53. Maximum Subarray 最大子数组
     * 分析：
     */
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * 1186. Maximum Subarray Sum with One Deletion 删除一次得到子数组最大和
     */
    public int maximumSum(int[] arr) {
        int max = arr[0];
        // 保存i节点开始的最大子数组和
        int[] sumStartHere = new int[arr.length];
        // 保存到i节点的最大子数组和
        int[] sumEndHere = new int[arr.length];
        // 从i节点开始的最大子数组和
        sumStartHere[arr.length - 1] = arr[arr.length - 1];
        for (int i = arr.length - 2; i >= 0; i--) {
            sumStartHere[i] = Math.max(arr[i], sumStartHere[i + 1] + arr[i]);
        }
        // 到i节点的最大子数组和
        sumEndHere[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sumEndHere[i] = Math.max(sumEndHere[i - 1] + arr[i], arr[i]);
            max = Math.max(max, sumEndHere[i]);
        }
        // 当去掉的那个数下标为i，则比较：max 与 sumStartHere[i] + sumEndHere[i] 的大小
        for (int i = 1; i < arr.length - 1; i++) {
            max = Math.max(max, sumStartHere[i + 1] + sumEndHere[i - 1]);
        }
        return max;
    }


    /**
     *
     */
}
