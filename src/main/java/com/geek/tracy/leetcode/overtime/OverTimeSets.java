package com.geek.tracy.leetcode.overtime;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * 30.串联所有单词的字串  -- 超时（常规思路）
     *
     * 给定一个字符串 s 和一个字符串数组 words。 words 中所有字符串 长度相同。
     *
     *  s 中的 串联子串 是指一个包含  words 中所有字符串以任意顺序排列连接起来的子串。
     *
     * 例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"， "abefcd"，"cdabef"， "cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。
     *  "acdbef" 不是串联子串，因为他不是任何  words 排列的连接。
     *  返回所有串联子串在 s 中的开始索引。你可以以 任意顺序 返回答案。
     *
     *  提示：
     *    1 <= s.length <= 104
     *    1 <= words.length <= 5000
     *    1 <= words[i].length <= 30
     *    words[i] 和 s 由小写英文字母组成
     */
    public List<Integer> findSubstring(String s, String[] words) {
        // 解法：滑动窗口，单词个数 m，单词长度 wl，原字串长度 sl，窗口长度 wl*m，每次滑动长度wl，外层循环次数wl即可滑动遍历所有的情况。
        // 利用哈希表，窗口内的单词存入哈希表，标记单词的次数，然后从中移除words数组中的单词，最后哈希表为空则该窗口与数组words单词相同。
        int m = words.length; // 单词个数 m
        int wl = words[0].length(); // 单词长度 wl
        int sl = s.length(); // 原字串长度 sl
        List<Integer> ans = new ArrayList<>();
        // 外层循环次数wl即可滑动遍历所有的情况  **关键**
        for (int i = 0; i < wl; i++) {
            // 窗口内的字符，切分为单词长度的字串放入哈希表
            if (i + wl * m > sl) {
                break;
            }
            Map<String, Integer> hash = new HashMap<>();
            // 1)将当前窗口的m个单词放入哈希map中
            for (int j = 0; j < m; j++) {
                String curr = s.substring(i + j * wl, i + (j + 1) * wl);
                hash.put(curr, hash.getOrDefault(curr, 0) + 1);
                // 哈希map个数为0的移除
                if (hash.get(curr) == 0) {
                    hash.remove(curr);
                }
            }
            // 2)遍历words数组，移除数组中的单词，若哈希map为空，则当前窗口的单词与数组中相同
            for (String word : words) {
                hash.put(word, hash.getOrDefault(word, 0) - 1);
                // 哈希map个数为0的移除
                if (hash.get(word) == 0) {
                    hash.remove(word);
                }
            }
            // 3)窗口每次后移一个单词，直至最后
            for (int start = i; start < sl - wl * m + 1; start += wl) {
                // 1，2步已处理
                if (start == i) {
                    if (hash.isEmpty()) {
                        ans.add(start); // 满足条件的下标起始值，存入ans
                    }
                } else {
                    // 窗口后移，将后面增加的单词放入哈希map，将前面移出窗口的单词移出哈希map
                    String rightAdd = s.substring(start + wl * (m - 1), start + wl * m);  // 右侧移进窗口的单词
                    hash.put(rightAdd, hash.getOrDefault(rightAdd, 0) + 1);  // 标识值加1
                    // 哈希map个数为0的移除
                    if (hash.get(rightAdd) == 0) {
                        hash.remove(rightAdd);
                    }

                    String leftRemove = s.substring(start - wl, start);   // 左侧移出窗口的单词
                    hash.put(leftRemove, hash.getOrDefault(leftRemove, 0) - 1);  // 标识值减1
                    // 哈希map个数为0的移除
                    if (hash.get(leftRemove) == 0) {
                        hash.remove(leftRemove);
                    }
                    if (hash.isEmpty()) {
                        ans.add(start); // 满足条件的下标起始值，存入ans
                    }
                }
            }
        }

        return ans;
    }

    /**
     * 30.串联所有单词的字串  -- 滑动窗口[答案]
     *
     */
    public List<Integer> findSubstringII(String s, String[] words) {
        List<Integer> res = new ArrayList<Integer>();
        int m = words.length;      // 单词个数
        int n = words[0].length(); // 单词长度
        int ls = s.length();       // 原始串长度

        for (int i = 0; i < n; i++) {
            if (i + m * n > ls) {
                break;
            }
            Map<String, Integer> differ = new HashMap<String, Integer>();
            for (int j = 0; j < m; j++) {
                String word = s.substring(i + j * n, i + (j + 1) * n);
                differ.put(word, differ.getOrDefault(word, 0) + 1);
            }
            for (String word : words) {
                differ.put(word, differ.getOrDefault(word, 0) - 1);
                if (differ.get(word) == 0) {
                    differ.remove(word);
                }
            }
            // 右移一个单词长度，遍历到最后
            for (int start = i; start < ls - m * n + 1; start += n) {
                if (start != i) {
                    // 右侧增加的一个单词
                    String word = s.substring(start + (m - 1) * n, start + m * n);
                    differ.put(word, differ.getOrDefault(word, 0) + 1);
                    if (differ.get(word) == 0) {
                        differ.remove(word);
                    }
                    // 去掉移动窗口前的一个单词
                    word = s.substring(start - n, start);
                    differ.put(word, differ.getOrDefault(word, 0) - 1);
                    if (differ.get(word) == 0) {
                        differ.remove(word);
                    }
                }
                if (differ.isEmpty()) {
                    res.add(start);
                }
            }
        }
        return res;
    }

    /**
     * 示例 1：
     *
     * 输入：s = "barfoothefoobarman", words = ["foo","bar"]
     * 输出：[0,9]
     * 解释：因为 words.length == 2 同时 words[i].length == 3，连接的子字符串的长度必须为 6。
     * 子串 "barfoo" 开始位置是 0。它是 words 中以 ["bar","foo"] 顺序排列的连接。
     * 子串 "foobar" 开始位置是 9。它是 words 中以 ["foo","bar"] 顺序排列的连接。
     * 输出顺序无关紧要。返回 [9,0] 也是可以的。
     * 示例 2：
     *
     * 输入：s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
     * 输出：[]
     * 解释：因为 words.length == 4 并且 words[i].length == 4，所以串联子串的长度必须为 16。
     * s 中没有子串长度为 16 并且等于 words 的任何顺序排列的连接。
     * 所以我们返回一个空数组。
     * 示例 3：
     *
     * 输入：s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
     * 输出：[6,9,12]
     * 解释：因为 words.length == 3 并且 words[i].length == 3，所以串联子串的长度必须为 9。
     * 子串 "foobarthe" 开始位置是 6。它是 words 中以 ["foo","bar","the"] 顺序排列的连接。
     * 子串 "barthefoo" 开始位置是 9。它是 words 中以 ["bar","the","foo"] 顺序排列的连接。
     * 子串 "thefoobar" 开始位置是 12。它是 words 中以 ["the","foo","bar"] 顺序排列的连接。
     */
    @Test
    public void test_30() {

        // 示例 1：
        //输入：s = "barfoothefoobarman", words = ["foo","bar"]
        //输出：[0,9]
        List<Integer> trueReturn11 = findSubstring("foobarfoobar", new String[]{"foo", "bar"});

        List<Integer> trueReturn = findSubstring("aaaaaaaaaaaaaa", new String[]{"aa", "aa"});
        //示例 2：
        //输入：s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
        //输出：[]
        List<Integer> trueReturn1 = findSubstring("wordgoodgoodgoodbestword", new String[]{"word","good","best","word"});

        //示例 3：
        //输入：s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
        //输出：[6,9,12]
        List<Integer> trueReturn2 = findSubstring("barfoofoobarthefoobarman", new String[]{"bar","foo","the"});

    }

}
