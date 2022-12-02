package com.geek.tracy.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1769. 移动所有球到每个盒子所需的最小操作数
 *
 * @Author Tracy
 * @Date 2022/12/2
 */
public class Code_1769 {

    /**
     * 1769. 移动所有球到每个盒子所需的最小操作数
     */
    public int[] minOperations(String boxes) {
        char[] chars = boxes.toCharArray();
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '1') {
                indexList.add(i);
            }
        }
        int [] answer = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            int sum = 0;
            for (Integer index : indexList) {
                sum += Math.abs(index - i);
            }
            answer[i] = sum;
        }
        return answer;
    }

    public void printArray(int [] arr) {
        Arrays.stream(arr).forEach(item -> System.out.print(item + " "));
        System.out.println();
    }
    @Test
    public void test1() {
        printArray(minOperations("110"));
        printArray(minOperations("001011"));
    }
}
