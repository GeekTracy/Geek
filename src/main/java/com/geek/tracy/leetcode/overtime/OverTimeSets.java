package com.geek.tracy.leetcode.overtime;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 超时题目汇总
 * @author Tracy
 * @date 2024/1/8
 */
public class OverTimeSets {


    /**
     * 1944.队列中可以看到的人数
     *
     * 有 n 个人排成一个队列，从左到右 编号为 0 到 n - 1 。给你以一个整数数组 heights ，每个整数 互不相同，heights[i] 表示第 i 个人的高度。     *
     * 一个人能 看到 他右边另一个人的条件是这两人之间的所有人都比他们两人矮。
     * 更正式的，第 i 个人能看到第 j 个人的条件是 i < j 且 min(heights[i], heights[j]) > max(heights[i+1], heights[i+2], ..., heights[j-1]) 。
     *     * 请你返回一个长度为 n 的数组 answer ，其中 answer[i] 是第 i 个人在他右侧队列中能 看到 的 人数 。
     */
    public int[] canSeePersonsCount(int[] heights) {
        int len = heights.length;
        if (len == 1) {
            return new int[1];
        }
        int[] ans = new int[len];
        // 从左往右，常规思路，超时
        for (int i = 0; i < len; i++) {
            // 当前参照值heigths[i]
            int cnt = 0;
            int max = 0; // 区间最大值
            int cur = heights[i];
            for (int point = i + 1; point < len; point++) {
                if (cur < heights[point]) {
                    cnt++;
                    break;
                } else {
                    if (heights[point] > max) {
                        cnt++;
                        max = Math.max(max, heights[point]);
                    }
                }
            }
            ans[i] = cnt;
        }
        return ans;
    }

    /**
     *  1944.队列中可以看到的人数  -- 单调栈解法
     *
     *  canSeePersonsCount 直接解答，对于某一个heights[i]往后遍历找到能看到的人，时间复杂度高。
     *  本题条件：第 i 个人能看到第 j 个人的条件是 i < j 且 min(heights[i], heights[j]) > max(heights[i+1], heights[i+2], ..., heights[j-1])
     *  分析可知，i能看到的人的身高是单调递增的，且看到第一个身高大于自己的即停止，由此构造一个单调栈，从右往左遍历得到一个单调递减的栈，遍历到
     *  i时，栈顶元素小于heights[i]，即出栈且可以看见该元素，直至栈为空或小于栈顶元素。
     */
    public int[] canSeePersonsCountII(int[] heights) {
        int length = heights.length;
        Deque<Integer> deque = new ArrayDeque<>();
        int[] ans = new int[length];
        // 从右向左遍历
        for (int i = length - 1; i >= 0 ; i--) {
            // 栈不为空或小于栈顶元素，保持栈元素单调递减
            while (!deque.isEmpty() && deque.peek() < heights[i]) {
                ans[i]++;
                deque.pop();
            }
            // 栈不为空，则栈顶值大于heights[i]，这个值也能被看到
            if (!deque.isEmpty()) {
                ans[i]++;
            }
            deque.push(heights[i]);
        }
        return ans;
    }

    @Test
    public void test_1944() {
        // 输入：heights = [10,6,8,5,11,9]
        //输出：[3,1,2,1,1,0]
        int[] ints = canSeePersonsCount(new int[]{10, 6, 8, 5, 11, 9});
        // 输入：heights = [5,1,2,3,10]
        //输出：[4,1,1,1,0]
        int[] ints1 = canSeePersonsCount(new int[]{5,1,2,3,10});
        System.out.println(ints1);
    }
}
