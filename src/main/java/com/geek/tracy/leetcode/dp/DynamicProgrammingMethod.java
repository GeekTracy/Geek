package com.geek.tracy.leetcode.dp;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 动态规划 Dynamic Programming 集合
 *
 * @Author Tracy
 * @Date 2023/6/27
 */
public class DynamicProgrammingMethod {

    @Test
    public void test_5() {
        System.out.println(longestPalindrome("erecbabcpoip"));
        System.out.println(longestPalindrome("adacbazxz"));
        System.out.println(longestPalindrome("adacbaabczxz"));
    }

    /**
     * 5.最长回文字串
     *
     * 提示：
     * 1 <= s.length <= 1000
     * s 仅由数字和英文字母组成
     */
    public String longestPalindrome(String s) {
        if (s.length() == 1) {
            return s;
        }
        int len = s.length();
        // dp[i][j] 表示下标i--j的字串是否为回文字串
        boolean dp[][] = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i] = true; // 每一个单独的字符都是一个回文字串
        }
        int maxLen = 1;
        int start = 0;
        char[] chars = s.toCharArray();
        // 字串长度为length时，
        for (int length = 2; length <= len; length++) {
            for (int i = 0; i < len; i++) {
                // 长度 length = j - i + 1，所以，j = length + i - 1
                int j = length + i - 1;
                if (j >= len) {
                    break;
                }
                if (chars[i] == chars[j]) {
                    if ((j - i >= 2 && dp[i + 1][j - 1]) || j - i == 1) {
                        dp[i][j] = true;
                        if (length > maxLen) {
                            maxLen = length;
                            start = i;
                        }
                    }
                }
            }
        }
        return s.substring(start, maxLen + start);
    }

    @Test
    public void test_2707 () {
        // "dwmodizxvvbosxxw"
        // ["ox","lb","diz","gu","v","ksv","o","nuq","r","txhe","e","wmo","cehy","tskz","ds","kzbu"]  except:7
        Assert.assertEquals(7, minExtraChar("dwmodizxvvbosxxw", new String[]{"ox","lb","diz","gu","v","ksv","o","nuq","r","txhe","e","wmo","cehy","tskz","ds","kzbu"}));
        // "rkmsilizktprllwoimafyuqmeqrujxdzgp"
        // "rkmsilizktprllwoimafyuqmeqrujxdzgp"
        // ["afy","lyso","ymdt","uqm","cfybt","lwoim","hdzeg","th","rkmsi","d","e","tp","r","jx","tofxe","etjx","llqs","cpir","p","ncz","ofeyx","eqru","l","demij","tjky","jgodm","y","ernt","jfns","akjtl","wt","tk","zg","lxoi","kt"]
        Assert.assertEquals(2, minExtraChar("rkmsilizktprllwoimafyuqmeqrujxdzgp", new String[]{"afy","lyso","ymdt","uqm","cfybt","lwoim","hdzeg","th","rkmsi","d","e","tp","r","jx","tofxe","etjx","llqs","cpir","p","ncz","ofeyx","eqru","l","demij","tjky","jgodm","y","ernt","jfns","akjtl","wt","tk","zg","lxoi","kt"}));
    }

    /**
     * 2707.字符串中的额外字符
     *
     *  定义f[i]标识下标为i的字串的额外字符个数，则原题可转换为转移方程中f[i - 1] + 1 和 f[j]的大小。其中f[0]=0
     *  状态转移方程： f[i] = Min(f[i - 1] + 1, f[j]),说明：f[j]，其中j∈（0，i-1）,s[j]--s[i-1] 为字典中存在的单词，则f[i] = f[j]
     */
    public int minExtraChar(String s, String[] dictionary) {
        HashSet<String> set = new HashSet<>();
        Collections.addAll(set, dictionary);
        int length = s.length();
        int[] f = new int[length + 1];
        f[0] = 0;
        for (int i = 1; i <= length; i++) {
            f[i] = f[i - 1] + 1;
            for (int j = 0; j < i; j++) {
                if (set.contains(s.substring(j, i))){
                    f[i] = Math.min(f[i], f[j]);
                }
            }
        }

        return f[length];
    }

    @Test
    public void minFallingPathSumIITest() {
        System.out.println(minFallingPathSumII(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));
        System.out.println(minFallingPathSumII(new int[][]{{2,2,1,2,2},{2,2,1,2,2},{2,2,1,2,2},{2,2,1,2,2},{2,2,1,2,2}}));
        // 回溯法会超时
        System.out.println(minFallingPathSumII(new int[][]{
                {-2, -18, 31, -10, -71, 82, 47, 56, -14, 42},
                {-95, 3, 65, -7, 64, 75, -51, 97, -66, -28},
                {36, 3, -62, 38, 15, 51, -58, -90, -23, -63},
                {58, -26, -42, -66, 21, 99, -94, -95, -90, 89},
                {83, -66, -42, -45, 43, 85, 51, -86, 65, -39},
                {56, 9, 9, 95, -56, -77, -2, 20, 78, 17},
                {78, -13, -55, 55, -7, 43, -98, -89, 38, 90},
                {32, 44, -47, 81, -1, -55, -5, 16, -81, 17},
                {-87, 82, 2, 86, -88, -58, -91, -79, 44, -9},
                {-96, -14, -52, -8, 12, 38, 84, 77, -51, 52}}));

    }

    /**
     * 1289. 下降路径最小和 II
     *
     * 给你一个 n x n 整数矩阵 grid ，请你返回 非零偏移下降路径 数字和的最小值。
     * 非零偏移下降路径 定义为：从 grid 数组中的每一行选择一个数字，且按顺序选出来的数字中，相邻数字不在原数组的同一列。
     */
    public int minFallingPathSumII(int[][] grid) {
        // 定义一个dp[][]，(n + 1)*(n + 1)的矩阵，最后一列记录当前行的最小dp值
        int n = grid.length;
        int[][] dp = new int[n][n + 1];
        int min = 1000;  // 本题数据范围：-99 <= grid[i][j] <= 99，所以设置1000
        for (int i = 0; i < n; i++) {
            min = Math.min(min, grid[0][i]);
            dp[0][i] = grid[0][i];
        }
        dp[0][n] = min;

        for (int i = 1; i < n; i++) {
            min = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                // dp[i][j] 的值为：grid[i][j] 加上上一行，列不等于j的最小dp[i-1][!j]
                if (dp[i - 1][j] == dp[i - 1][n]) {
                    // 上方值为最小，则需要遍历其他找到最小dp值，否则直接取上一行最后列dp值
                    int temp = Integer.MAX_VALUE;
                    for (int k = 0; k < n; k++) {
                        if (k == j) continue;
                        temp = Math.min(temp, dp[i - 1][k]);
                    }
                    dp[i][j] = temp + grid[i][j];
                } else {
                    dp[i][j] = grid[i][j] + dp[i - 1][n];
                }
                min = Math.min(min, dp[i][j]);
            }
            // 给当前行最后一列赋值
            dp[i][n] = min;
        }
        return dp[n - 1][n];
    }

    @Test
    public void minFallinPathSumTest() {
        System.out.println(minFallingPathSum(new int[][]{{2, 1, 3}, {6, 5, 4}, {7, 8, 9}}));
    }

    /**
     * 931. 下降路径最小和
     *
     * 给你一个 n x n 的 方形 整数数组 matrix ，请你找出并返回通过 matrix 的下降路径 的 最小和 。
     * 下降路径 可以从第一行中的任何元素开始，并从每一行中选择一个元素。在下一行选择的元素和当前行所选元素最多相隔一列（即位于正下方或者沿对角
     * 线向左或者向右的第一个元素）。具体来说，位置 (row, col) 的下一个元素应当是 (row + 1, col - 1)、(row + 1, col) 或者 (row + 1, col + 1) 。
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
