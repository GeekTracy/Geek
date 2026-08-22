package com.geek.tracy.leetcode.monotonicstack;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * 单调栈 MonotonicStack
 * <p>
 *     栈：保存单调递增/减的元素，减少不必要的数据比较
 * </p>
 *
 */
public class MonotonicStackSets {

    /**
     * 496.下一个更大元素Ⅰ
     */
    public int[] nextGreaterElementI(int[] nums1, int[] nums2) {
        // 定义哈希表，保存nums2的值与下标，反向遍历nums2，保存单调栈
        HashMap<Integer, Integer> numIndexMap = new HashMap<>();
        int[] nextBigger = new int[nums2.length];
        int[] ans = new int[nums1.length];
        Deque<Integer> deque = new ArrayDeque<>();  // 单调栈
        for (int i = nums2.length - 1; i >= 0; i--) {
            numIndexMap.put(nums2[i], i);
            // 固定结构：栈不为空，若栈顶元素小于候选元素，则出栈直到栈顶元素大于候选
            while (!deque.isEmpty() && deque.peek() < nums2[i]) {
                deque.pop();
            }
            nextBigger[i] = deque.isEmpty() ? -1 : deque.peek();
            // 较大候选元素压栈
            deque.push(nums2[i]);
        }
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = nextBigger[numIndexMap.get(nums1[i])];
        }
        return ans;
    }

    @Test
    public void test_496() {
        // 输入：nums1 = [4,1,2], nums2 = [1,3,4,2].
        // 输出：[-1,3,-1]
        int[] actualResult = nextGreaterElementI(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2});
        System.out.println("输入：nums1 = [4,1,2], nums2 = [1,3,4,2],输出：[-1,3,-1]");
        int[] actualResult1 = nextGreaterElementI(new int[]{2, 4}, new int[]{1, 2, 3, 4});
        System.out.println("输入：nums1 = [2,4], nums2 = [1,2,3,4],输出：[3,-1]");
    }

    /**
     * 503.下一个更大元素Ⅱ
     * <p>
     * nums是一个循环数组
     */
    public int[] nextGreaterElementsII(int[] nums) {
        int[] ans = new int[nums.length];
        // 找到循环数组的最大值，从最大值处开始倒叙循环
        int maxInArray = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (maxInArray < nums[i]) {
                maxInArray = nums[i];
                index = i;
            }
        }
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) { // 方向遍历
            // 转换遍历时的index
            int trueIndex = (index + nums.length - i) % nums.length;
            while (!deque.isEmpty() && deque.peek() <= nums[trueIndex]) {
                deque.pop();
            }
            ans[trueIndex] = deque.isEmpty() ? -1 : deque.peek();
            deque.push(nums[trueIndex]);
        }
        return ans;
    }

    @Test
    public void test_503() {
        // 输入: nums = [1,2,3,4,3]
        //输出: [2,3,4,-1,4]
        int[] actualResult = nextGreaterElementsII(new int[]{1, 2, 3, 4, 3});
        System.out.println("输入：nums = [1,2,3,4,3],输出：[2,3,4,-1,4]");
    }

    /**
     * 739.每日温度
     * <p>给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度
     * 出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。</p>
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        // 从右到左遍历temperatures数组，栈保存单调递减（从栈底到栈顶）的温度值
        Deque<Integer> ms = new LinkedList<>(); // 保存单调递减的气温的下标
        int[] res = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            int tmp = temperatures[i];
            // 遍历栈，直到:[栈顶元素]>tem，计算下标i后面高于tmp的天数
            while (!ms.isEmpty() && tmp >= temperatures[ms.peek()]) {
                ms.pop();  // 将栈顶小于等于tmp的温度出栈，只保存靠左的较大的温度下标
            }
            // 栈不为空，则栈顶元素为i元素右侧第一个大于tmp的温度下标
            if (!ms.isEmpty()) {
                res[i] = ms.peek() - i;
            }
            ms.push(i);
        }
        return res;
    }

    /**
     * 739.每日温度-2 :解法2，从左到右遍历tmp，栈保存左侧温度下标，如果tmp大于栈顶元素，则计算栈顶元素与temperatuers[i]下标差值
     */
    public int[] dailyTemperatures_2(int[] temperatures) {
        int n = temperatures.length;;
        int[] res = new int[n];
        Deque<Integer> ms = new LinkedList<>(); // 保存单调递减
        for (int i = 0; i < n; i++) {
            int tmp = temperatures[i];
            while (!ms.isEmpty() && tmp > temperatures[ms.peek()]) {
                // 计算栈顶元素的天数，并出栈
                Integer popIndex = ms.pop();
                res[popIndex] = i - popIndex;
            }
            ms.push(i);
        }
        return res;
    }



    @Test
    public void test_sets() {
        System.out.println(Arrays.toString(dailyTemperatures_2(new int[]{73,74,75,71,69,72,76,73})));
        //输入: temperatures = [73,74,75,71,69,72,76,73]
        //输出: [1,1,4,2,1,1,0,0]
        //示例 2:
        //
        System.out.println(Arrays.toString(dailyTemperatures_2(new int[]{30,40,50,60})));

        //输入: temperatures = [30,40,50,60]
        //输出: [1,1,1,0]
        //示例 3:
        //
        System.out.println(Arrays.toString(dailyTemperatures_2(new int[]{30,60,90})));
        //输入: temperatures = [30,60,90]
        //输出: [1,1,0]
    }
}
