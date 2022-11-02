package com.geek.tracy.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 946. 验证栈序列
 * @Author Tracy
 * @Date 2022/11/2
 */
public class Code_946 {

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Deque<Integer> stack = new LinkedList<>();
        int pushIndex = 0;
        int popIndex = 0;
        for (int i = 0; i < pushed.length; i++) {
            if (stack.isEmpty() || stack.peek() != popped[popIndex]) {
                stack.push(pushed[pushIndex++]);
            }
            while (!stack.isEmpty() && stack.peek() == popped[popIndex]) {
                stack.pop();
                popIndex++;
            }
        }
        return pushIndex == popIndex && pushIndex == pushed.length;

    }

    @Test
    public void test01 () {
        int[] pushed = {1, 2, 3, 4, 5};
        int[] popped = {4, 5, 3, 2, 1};
        Assert.assertEquals(true, validateStackSequences(pushed, popped));
    }

    @Test
    public void test02 () {
        int[] pushed = {1, 2, 3, 4, 5};
        int[] popped = {4, 5, 3, 1, 2};
        Assert.assertEquals(false, validateStackSequences(pushed, popped));
    }
}
