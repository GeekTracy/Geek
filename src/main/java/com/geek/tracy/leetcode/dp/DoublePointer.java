package com.geek.tracy.leetcode.dp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 排序 + 双指针
 *
 * @author mike
 * @date 2023/7/1
 */
public class DoublePointer {

    @Test
    public void test() {
//        System.out.println(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
        System.out.println(fourSum(new int[]{2, 2, 2, 2, 2}, 8));
        System.out.println(fourSum(new int[]{1000000000,1000000000,1000000000,1000000000}, -294967296));
    }

    /**
     *  18. 四数之和
     *  解法分析：同三数之和的做法：排序+双指针，遍历数组：i,j，移动指针l,r，且i < j < l < r
     *  注：int越界问题，使用long保存sum
     *
     * 参考：三数之和，排序 + 双指针，遍历数组元素 i, 左右指针 l, r，且i < l < r，l、r，指针移动寻找i、l、r 下标和等于target放入数组
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                // 左右指针：l = j + 1, r = length - 1
                int l = j + 1;
                int r = nums.length - 1;
                while (l < r) {
                    long sum = (long)nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum < target) {
                        // 左指针 l 右移
                        l++;
                    } else if (sum > target) {
                        // 右指针 r 左移
                        r--;
                    } else {
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        while (l < r && nums[r - 1] == nums[r]) {
                            r--;
                        }
                        l++;
                        r--;
                    }
                }
            }
        }
        return res;
    }



    /**
     * 15. 三数之和
     */
    @Test
    public void threeSumTest() {
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(threeSum(new int[]{0, 0, 0}));
        System.out.println(threeSum(new int[]{0, 0, 0, 0}));
    }

    /**
     * 15. 三数之和
     *      排序 + 双指针法：将数组排序，取下标i的之后的2个指针：l、r，且i < l < r，遍历数组
     *  提示：
     *  *   3 <= nums.length <= 3000
     *  *   -10^5 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return res;
        }
        // 排序
        Arrays.sort(nums);
        // 遍历数组的元素，由于求的是和为0，又数组已排序
        for (int i = 0; i < nums.length; i++) {
            // 如果下标i的元素大于0，则3数之和不可能为0，终止循环
            if (nums[i] > 0) {
                break;
            }
            // 本题存在重复元素，当nums[i] = nums[i - 1]，则遍历i时会出现重复结果，需要跳过
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 双指针，l、r一前一后，收缩遍历
            int l = i + 1;
            int r = nums.length - 1;

            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    // 左指针，重复值，取最右
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    // 右指针，重复值，取最左
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    l++;
                    r--;
                } else if (sum < 0) {
                    l++;
                } else {
                    r--;
                }
            }
        }

        return res;
    }
}
