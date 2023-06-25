package com.geek.tracy.leetcode;


import org.junit.Test;

/**
 * 2496. 数组中字符串的最大值
 *
 */
public class Code_2496 {

    /**
     * 一个由字母和数字组成的字符串的 值 定义如下：
     *
     * 如果字符串 只 包含数字，那么值为该字符串在 10 进制下的所表示的数字。
     * 否则，值为字符串的 长度 。
     * 给你一个字符串数组 strs ，每个字符串都只由字母和数字组成，请你返回 strs 中字符串的 最大值 。
     * @param strs
     * @return
     */
    public int maximumValue(String[] strs) {
        // 记录数组strs中的最大值
        int max = 0;
        for (String str : strs) {
            if (allNum(str)) {
                if (max < Integer.parseInt(str)) {
                    max = Integer.parseInt(str);
                }
            } else {
                if (max < str.length()) {
                    max = str.length();
                }
            }
        }
        return max;
    }

    private boolean allNum(String str) {
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (aChar < '0' || aChar > '9') {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test () {
        String[] str1 = new String[] {"alic3","bob","3","4","00000"};
        System.out.println(maximumValue(str1));
        String[] str2 = new String[] {"1","01","001","0001"};
        System.out.println(maximumValue(str2));
        String[] str3 = new String[] {"89","bob","3","4","00000"};
        System.out.println(maximumValue(str3));
    }
}
