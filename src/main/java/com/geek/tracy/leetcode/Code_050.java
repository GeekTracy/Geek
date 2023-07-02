package com.geek.tracy.leetcode;

import org.junit.Test;

/**
 *
 * 50. Pow(x, n)
 * @author mike
 * @date 2023-06-24
 */
public class Code_050 {

    /**
     * 提示：
     *
     * -100.0 < x < 100.0
     * -2^31 <= n <= 2^31-1
     * n 是一个整数
     * 要么 x 不为零，要么 n > 0 。
     * -10^4 <= x^n <= 10^4
     *
     * 分析：常规做法，由于n比较大，会超时，使用折半n进行计算
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (x == 0) {
            return 0;
        }

//        double res = 1;
//        for (int i = n; i > 0; n / 2) {
//        }

        return 0.0;
    }

    @Test
    public void test() {

    }
}
