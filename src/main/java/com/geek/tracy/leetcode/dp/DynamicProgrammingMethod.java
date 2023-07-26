package com.geek.tracy.leetcode.dp;

import org.junit.Test;

import java.util.Arrays;

/**
 * 动态规划 Dynamic Programming 集合
 *
 * @Author Tracy
 * @Date 2023/6/27
 */
public class DynamicProgrammingMethod {

    @Test
    public void minFallinPathSumTest() {
        System.out.println(minFallingPathSum(new int[][]{{2, 1, 3}, {6, 5, 4}, {7, 8, 9}}));
    }

    /**
     * 931. 下降路径最小和
     */
    public int minFallingPathSum(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        // 定义一个dp[][]二位数组，保存当前元素往下的最小路径和，自底向上进行计算
        int[][] dp = new int[row][col];
        for (int i = row - 1; i >= 0; i--) {
            // 最后一排，dp[row - 1][j] = matrix[row - 1][j]
            for (int j = 0; j < col; j++) {
                dp[i][j] = matrix[i][j] + getNextRowMin(dp, i, j);
            }
        }
        Arrays.sort(dp[0]);
        return dp[0][0];
    }

    private int getNextRowMin(int[][] dp, int row, int col) {
        int len = dp.length;
        if (row == len - 1) {
            return 0;
        }
        // 取下一行左、中、右3个值中的最小值，并注意左右边界
        // 1左边界
        if (col == 0) {
            return Math.min(dp[row + 1][col], dp[row + 1][col + 1]);
        } else if (col == len - 1) {
            return Math.min(dp[row + 1][col], dp[row + 1][col - 1]);
        } else {
            return Math.min(Math.min(dp[row + 1][col], dp[row + 1][col - 1]), dp[row + 1][col + 1]);
        }
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
     * <p>
     * 分析： sumEndHere[i - 1]，arr[i]，sumStartHere[i + 1]，其中arr[i]为待删除的那个数，使用Kadane's Algorithm思想求解即可
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
     * 1186. Maximum Subarray Sum with One Deletion 删除一次得到子数组最大和  --测试
     */
    @Test
    public void maximumSumTest() {
        System.out.println("except num: 3");
        System.out.println(maximumSum(new int[]{2, 1, -2, -5, -2}));
//        System.out.println("except num: 4");
//        System.out.println(maximumSum(new int[]{1, -2, 0, 3}));
//        System.out.println("except num: 3");
//        System.out.println(maximumSum(new int[]{1,-2,-2,3}));
//        System.out.println("except num: -1");
//        System.out.println(maximumSum(new int[]{-1,-1,-1,-1}));

    }

    /**
     * 918. 环形子数组的最大和
     * <p>
     * 分析：53. Maximum Subarray 最大子数组的变式，首尾相连
     * 解法1：环形数组最大和可分解为：
     * 1）最大和只有一段，在数组中间，此时和最大子数组相同解法；
     * 2）存在两端，一段包含头num[0]，一段包含尾nums[length-1]，这种情况，分2段求和与第一步中最大和求Max即为结果
     */
    public int maxSubarraySumCircular(int[] nums) {
        int res = nums[0]; // 环形数组最大和
        int endHereMax = nums[0]; // 记录前一个数的最大子数组和
        int len = nums.length; // 数组长度
        // 解决第一步，Kadane's Algorithm 计算以nums[i]结尾的最大子数组和
        // 解决第二步，遍历nums[j]--nums[length-1]的和，与nums[0]--num[i]首段和，最大值如比上一步res大，即结果为首位两段，否则最大和在数组中部
        // 需再定义一个leftMax[i]，用来记录nums[i]位置的左侧最大和；
        int leftSum = nums[0];
        int[] leftMax = new int[len]; // nums[i]左侧最大和
        leftMax[0] = nums[0];
        // step1
        for (int i = 1; i < len; i++) {
            endHereMax = Math.max(nums[i], nums[i] + endHereMax); // 记录以nums[i]结尾的最大连续子数组和
            res = Math.max(res, endHereMax);  // 记录最终结果的环形最大和
            leftMax[i] = Math.max(leftSum + nums[i], leftMax[i - 1]); // 记录包含起点nums[0]到nums[i]的最大子数组和
            leftSum += nums[i];  // nums[0]到nums[i]的和，用于计算leftMax
        }
        // step2
        int rightSum = 0;
        for (int j = len - 1; j > 0; j--) {  // step1已求出leftmax[]，即已得到最短最大子数组和，遍历获取右侧最大子数组和，遍历比较出res即可
            rightSum += nums[j];
            res = Math.max(res, leftMax[j - 1] + rightSum);
        }
        return res;
    }

    @Test
    public void maxSubarraySumCircularTest() {
        System.out.println(maxSubarraySumCircular(new int[]{1, -2, 3, -2}));
    }


}
