package com.geek.tracy.lambda.fourInterface;

import org.junit.Test;

import java.util.function.Supplier;

/**
 * @Author yang
 * @Date 2022/1/28
 */
public class SupplierTest {

    /**
     * 供给型接口--Supplier
     * 参数：T
     * 返回：T
     *
     * 补充：
     * BooleanSupplier, IntSupplier, LongSupplier, DoubleSupplier
     *   Java提供了以下功能接口，用于特定数据类型的Supplier。
     *
     *      BooleanSupplier: Supplier返回Boolean数据类型。它的方法是getAsBoolean()。
     *
     *      IntSupplier: Supplier返回Integer数据类型。它的方法是getAsInt()。
     *
     *      LongSupplier: Supplier返回Long数据类型。它的方法是getAsLong()。
     *
     *      DoubleSupplier: Supplier返回Double数据类型。它的方法是getAsDouble()。
     */
    @Test
    public void test() {
        Supplier<String> supplier = new Supplier() {
            @Override
            public String get() {
                return "Supplier get 方法无入参。";
            }
        };
        System.out.println(supplier.get());
    }
}
