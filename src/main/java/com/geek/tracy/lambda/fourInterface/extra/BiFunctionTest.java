package com.geek.tracy.lambda.fourInterface.extra;

import org.junit.Test;

import java.util.function.BiFunction;

/**
 * @Author yang
 * @Date 2022/2/8
 */
public class BiFunctionTest {

    @Test
    public void test() {
        BiFunction<String, Integer, String> bf = (a, b) -> a + b;
        // 1. BiFunction.apply方法
        System.out.println(bf.apply("t", 10));
        // 2. BiFunction.andThen方法
        System.out.println(bf.andThen(a -> "andThen-->" + a).apply("B", 20));

    }
}
