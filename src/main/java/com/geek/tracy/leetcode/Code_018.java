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
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            int[] copy = Arrays.copyOfRange(nums, i + 1, nums.length);
            List<List<Integer>> threeSumList = threeSum(copy, target - nums[i]);
            if (threeSumList.size() > 0) {
                for (List<Integer> threeSum : threeSumList) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[i]);
                    temp.addAll(threeSum);
                    res.add(temp);
                }
            }
        }
        // 去重
        remove(res);
        return res;
    }

    private void remove(List<List<Integer>> res) {
        if (res.size() == 0) {
            return;
        }
        List<String> temp = new ArrayList<>();
        Iterator<List<Integer>> iterator = res.iterator();
        while (iterator.hasNext()) {
            List<Integer> next = iterator.next();
            String listString = next.toString();
            if (!temp.contains(listString)) {
                temp.add(listString);
            } else {
                iterator.remove();
            }

        }


    }

    private List<List<Integer>> threeSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        // 遍历数组的元素，由于求的是和为0，又数组已排序
        for (int i = 0; i < nums.length; i++) {
            // 本题存在重复元素，当nums[i] = nums[i - 1]，则遍历i时会出现重复结果，需要跳过
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 双指针，l、r一前一后，收缩遍历
            int l = i + 1;
            int r = nums.length - 1;

            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == target) {
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
                } else if (sum < target) {
                    l++;
                } else {
                    r--;
                }
            }
        }
        return res;
    }
}
