package com.geek.tracy.leetcode;

import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 394. 字符串解码
 *
 * @Author Tracy
 * @Date 2022/10/31
 */
public class Code_394 {

    /**
     * 给定一个经过编码的字符串，返回它解码后的字符串。
     * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
     * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
     *
     * 限制条件：
     * 1 <= s.length <= 30
     * s 由小写英文字母、数字和方括号 '[]' 组成
     * s 保证是一个 有效 的输入。
     * s 中所有整数的取值范围为 [1, 300]
     */
    public String decodeString(String s) {
        // 保存遍历的字符
        Deque<String> stack = new LinkedList<>();
        Deque<String> deque = new LinkedList<>();
        for (char c : s.toCharArray()) {
            // ]
            if (c == ']') {
//                StringBuilder sb = new StringBuilder();
                // 出栈，并拼接，再入栈
                while (true) {
                    String pop = stack.pop();
                    if (pop.equals("[")) {
                        StringBuilder times = new StringBuilder();
                        // 计算倍数，并将sb翻倍拼接
                        while (!stack.isEmpty()) {
                            if (stack.peek().length() == 1 && stack.peek().toCharArray()[0] >= '0' && stack.peek().toCharArray()[0] <= '9') {
                                times.append(stack.pop());
                            } else {
                                break;
                            }
                        }

                        String temp = getString(deque);
                        Integer sum = Integer.valueOf(times.reverse().toString());
                        String ss = "";
                        for (int i = 0; i < sum; i++) {
                            ss = ss + temp;
                        }
                        stack.offerFirst(ss);
                        break;
                    } else {
                        deque.addFirst(pop);
                    }
                }
            } else {
                // 数字 字母 [
                stack.offerFirst(String.valueOf(c));
            }
        }
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pollLast());
        }
        return result.toString();
    }

    private String getString(Deque<String> deque) {
        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty()) {
            sb.append(deque.pollFirst());
        }
        return sb.toString();
    }

    public String decodeString11(String s) {
        StringBuilder res = new StringBuilder();
        int multi = 0;
        LinkedList<Integer> stack_multi = new LinkedList<>();
        LinkedList<String> stack_res = new LinkedList<>();
        for (Character c : s.toCharArray()) {
            if (c == '[') {
                stack_multi.addLast(multi);
                stack_res.addLast(res.toString());
                multi = 0;
                res = new StringBuilder();
            } else if (c == ']') {
                StringBuilder tmp = new StringBuilder();
                int cur_multi = stack_multi.removeLast();
                for (int i = 0; i < cur_multi; i++) tmp.append(res);
                res = new StringBuilder(stack_res.removeLast() + tmp);
            } else if (c >= '0' && c <= '9') {
                multi = multi * 10 + Integer.parseInt(c + "");
            }
            else res.append(c);
        }
        return res.toString();
    }


    @Test
    public void test() {
//        System.out.println(decodeString("3[z]2[2[y]pq4[2[jk]e1[f]]]ef"));
        System.out.println(decodeString11("100[leetcode]"));
//        System.out.println(decodeString("3[a]2[bc]"));
//        System.out.println(decodeString("3[a2[c]]"));
//        System.out.println(decodeString("2[abc]3[cd]ef"));
    }
}
