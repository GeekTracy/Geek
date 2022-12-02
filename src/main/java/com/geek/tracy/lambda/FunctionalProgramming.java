package com.geek.tracy.lambda;

import org.junit.Test;

import java.util.Objects;

/**
 * 函数式编程
 *
 * @Author yang
 * @Date 2022/1/29
 */
public class FunctionalProgramming {

    /**
     * 传统的if-else方式，求2个数的相加、相减、相乘、相除
     */
    @Test
    public void traditionalIfElse() {
        System.out.println(add(5, 6));
        System.out.println(subtract(5, 6));
        System.out.println(multiply(5, 6));
        System.out.println(divide(25, 6));
        System.out.println(divide(5, 0));
    }

    /**
     * 加法
     */
    private Integer add(Integer x, Integer y) {
        System.out.print(x + " + " + y + " = ");
        return x + y;
    }

    /**
     * 减法
     */
    private Integer subtract(Integer x, Integer y) {
        System.out.print(x + " - " + y + " = ");
        return x - y;
    }

    /**
     * 乘法
     */
    private Integer multiply(Integer x, Integer y) {
        System.out.print(x + " * " + y + " = ");
        return x * y;
    }

    /**
     * 除法
     */
    private Integer divide(Integer x, Integer y) {
        Objects.requireNonNull(y, "除数不能为0");
        System.out.print(x + " / " + y + " = ");
        return x / y;
    }


    @Test
    public void testFunctionalProgramming() {

    }


}
