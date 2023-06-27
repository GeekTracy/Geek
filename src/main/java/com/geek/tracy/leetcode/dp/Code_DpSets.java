package com.geek.tracy.leetcode.dp;

/**
 * 动态规划 Dynamic Programming 集合
 * @Author Tracy
 * @Date 2023/6/27
 */
public class Code_DpSets {


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
        return 0;
    }

    /**
     * 1186. Maximum Subarray Sum with One Deletion 删除一次得到子数组最大和
     */
    public int maximumSum(int[] arr) {
        return 0;
    }

    /**
     *
     */
}
