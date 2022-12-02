package com.geek.tracy.lambda.fourInterface.extra;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * BinaryOperator 继承 BiFunction，2个T型入参，且出参也是T，特殊的BiFunction，增加了minBy，maxBy方法
 *
 * @Author yang
 * @Date 2022/2/8
 */
public class BinaryOperatorTest {

    @Test
    public void test() {
        BinaryOperator<Integer> bo = (a, b) -> a + b;  // 求和
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println("数组求和：" + list.stream().reduce(bo).get());
        // minBy为静态方法，可以直接调用Comparator.naturalOrder传入Comparator
        System.out.println("min: " + list.stream().reduce(BinaryOperator.minBy(Comparator.naturalOrder())).get());
        // 同上
        System.out.println("max: " + list.stream().reduce(BinaryOperator.maxBy(Comparator.naturalOrder())).get());
    }
}
