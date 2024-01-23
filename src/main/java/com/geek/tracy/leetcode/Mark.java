package com.geek.tracy.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 难题，经典汇总mark
 * @author Tracy
 * @date 2024/1/19
 */
public class Mark {


    /**
     * 2809.使数组和小于等于x的最少时间
     *
     * 给你两个长度相等下标从 0 开始的整数数组 nums1 和 nums2 。每一秒，对于所有下标 0 <= i < nums1.length ，nums1[i] 的值都增加 nums2[i] 。
     * 操作 完成后 ，你可以进行如下操作：
     *
     * 选择任一满足 0 <= i < nums1.length 的下标 i ，并使 nums1[i] = 0 。
     * 同时给你一个整数 x 。
     *
     * 请你返回使 nums1 中所有元素之和 小于等于 x 所需要的 最少 时间，如果无法实现，那么返回 -1
     */
    public int minimumTime(List<Integer> nums1, List<Integer> nums2, int x) {
        // 如果多次操作一个数，发现只有最后一次操作有效，之前的操作只会让其他数的值增大，因此每个数最多仅增加一次，即操作次数在[0, n]之间。
        int n = nums1.size();
        int[][] f = new int[n + 1][n + 1];
        int[][] nums = new int[n][2]; // nums数组，固定nums1,nums2数组相同下标的值，后面按nums2值排序时，保证对应位置的数不变
        int s1 = 0, s2 = 0;  // nums1/nums2 数组的和
        for (int i = 0; i < n; ++i) {
            s1 += nums1.get(i);
            s2 += nums2.get(i);
            nums[i] = new int[] {nums1.get(i), nums2.get(i)};
        }
        // 按照nums2数组的值，对nums1,nums2进行升序排序，即可按顺序进行操作
        Arrays.sort(nums, Comparator.comparingInt(a -> a[1]));
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= n; ++j) {
                f[i][j] = f[i - 1][j];
                if (j > 0) {
                    int a = nums[i - 1][0], b = nums[i - 1][1];
                    // 转移方程：f[i][j] = max{f[i−1][j], f[i−1][j−1] + nums1[i] + nums2[i]×j}
                    f[i][j] = Math.max(f[i][j], f[i - 1][j - 1] + a + b * j);
                }
            }
        }
        for (int j = 0; j <= n; ++j) {
            if (s1 + s2 * j - f[n][j] <= x) {
                return j;
            }
        }
        return -1;
    }

//    作者：ylb
//    链接：https://leetcode.cn/problems/minimum-time-to-make-array-sum-at-most-x/solutions/2610313/python3javacgotypescript-yi-ti-yi-jie-pa-nyni/
    public int minimumTime02(List<Integer> nums1, List<Integer> nums2, int x) {
        int n = nums1.size();
        int[] f = new int[n + 1];
        int[][] nums = new int[n][0];
        int s1 = 0, s2 = 0;  // nums1/nums2 数组的和
        for (int i = 0; i < n; ++i) {
            s1 += nums1.get(i);
            s2 += nums2.get(i);
            nums[i] = new int[] {nums1.get(i), nums2.get(i)};
        }
        Arrays.sort(nums, Comparator.comparingInt(a -> a[1]));
        for (int[] e : nums) {
            int a = e[0], b = e[1];
            for (int j = n; j > 0; --j) {
                f[j] = Math.max(f[j], f[j - 1] + a + b * j);
            }
        }

        for (int j = 0; j <= n; ++j) {
            if (s1 + s2 * j - f[j] <= x) {
                return j;
            }
        }
        return -1;
    }


    @Test
    public void test_2809() {

    }



    /**
     * 31.下一个排列
     *
     * 分析：题意即寻找下一个较大的排列，当当前排列是最大的排列时，返回最小的那个排列。
     *      例如：[1,2,3]，所有的排列有：[1,2,3]、[1,3,2]、[2,1,3]、[2,3,1]、[3,1,2]、[3,2,1]，按大小顺序排列，
     *      当当前序列是最后一个，即[3,2,1]时，下一个排列即为第一个[1,2,3]。
     *     1）从右往左找，找到第一个[i < j 且 nums[i] < nums[j] ]（升序）,则从j到end一定是递减；
     *     2）在[j,end]找到k，满足[nums[i] < nums[k] ]，交换i和k的值；
     *     3）i之后的区间一定为降序排列，将i之后调整为升序；
     *     4）如果第1步没有找到满足条件的i，j，说明nums为降序排列，即最大的那个排列，直接执行第3步将整个数组调整为升序；
     */
    public void nextPermutation(int[] nums) {
        int i = -1;
        int length = nums.length;
        for (int n = length - 1; n > 0; n--) {
            if (nums[n - 1] < nums[n]) {
                i = n -1;
                break;
            }
        }
        // 特殊情况，原数组为降序
        if (i == -1) {
            reverse(nums, 0, length - 1);
            return;
        }
        int k = 0;
        for (int n = length - 1; n > i; n--) {
            if (nums[n] > nums[i]) {
                k = n;
                break;
            }
        }
        // 交换i 和 k，并将i之后的改为升序
        int temp = nums[k];
        nums[k] = nums[i];
        nums[i] = temp;

        // i + 1 --- end 倒叙排列
        reverse(nums, i + 1, length - 1);
        System.out.println("----end----");
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int i1 = nums[end];
            nums[end] = nums[start];
            nums[start] = i1;
            start++;
            end--;
        }
    }

    @Test
    public void test_31() {
        nextPermutation(new int[]{1,2,3,8,5,7,6,4});
    }

}
