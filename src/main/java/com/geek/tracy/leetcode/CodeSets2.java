package com.geek.tracy.leetcode;

/**
 * @author mike
 * @date 2024/9/24
 */
public class CodeSets2 {

    /**
     * 2207.字符串中最多数目的子序列
     * @param text
     * @param pattern 长度为 2
     * @return
     */
    public long maximumSubsequenceCount(String text, String pattern) {
        // pattern 长度为 2，将其插入到text中，令x = pattern[0]，y = pattern[1]。
        char x = pattern.charAt(0);
        char y = pattern.charAt(1);
        long ans = 0;
        int cntx = 0;
        int cnty = 0;
        for (char c : text.toCharArray()) {
            // 遇到y时，则x的个数代表了xy子串的个数
            if (c == y) {
                ans += cntx;
                cnty++;
            }
            if (c == x) {
                cntx++;
            }
        }
        // 因为可以在text中插入x或者y，若将x插入到最左侧，则ans增加cnty个，反之增加cntx个，即判断cntx和cnty哪个更大即可
        return ans + Math.max(cntx, cnty);
    }
}
