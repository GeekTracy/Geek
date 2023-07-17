package com.geek.tracy.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * 18. 四数之和
 *
 * @author mike
 * @date 2023/7/15
 */
public class Code_018 {

    @Test
    public void test() {
//        System.out.println(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
        System.out.println(fourSum(new int[]{2, 2, 2, 2, 2}, 8));
        System.out.println(fourSum(new int[]{1000000000,1000000000,1000000000,1000000000}, -294967296));
    }

    /**
     * 四数之和，同三数之和的做法：排序+双指针，遍历数组：i,j，移动指针l,r，且i < j < l < r
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



    @Test
    public void threeSumTest() {
//        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}, 0));
        System.out.println(threeSum(new int[]{0, 0, 0}, 0));
//        System.out.println(threeSum(new int[]{0, 0, 0, 0}, 0));
    }

    /**
     * 三数之和，排序 + 双指针，遍历数组元素 i, 左右指针 l, r，且i < l < r，l、r指针移动寻找i、l、r 下标和等于target放入数组
     * @param nums
     * @param target
     * @return
     */
    private List<List<Integer>> threeSum(int[] nums, int target) {
        if (nums.length < 3) {
            return new ArrayList<>();
        }
        // 排序
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            // 遍历下标：i，去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 左指针：l = i + 1，右指针：r = size - 1
            int l = i + 1;
            int r = nums.length - 1;
            // 若：nums[i] + nums[l] + nums[r] < target，则左指针右移；
            // 若：nums[i] + nums[l] + nums[r] > target，则右指针左移；
            // 否则满足条件，放入列表
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum < target) {
                    l++;
                } else if (sum > target) {
                    r--;
                } else {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
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
        return res;
    }
}
