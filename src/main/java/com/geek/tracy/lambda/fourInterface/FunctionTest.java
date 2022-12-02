package com.geek.tracy.lambda.fourInterface;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * @Author yang
 * @Date 2022/1/28
 */
public class FunctionTest {

    /**
     * 函数型接口--Function
     * 参数：T
     * 返回：R
     */
    @Test
    public void test() {
        Function<String, String> function = s -> s + " doing apply function.";

        System.out.println(function.apply("test"));
    }


    @Test
    public void test11() {
    }

    private <E> Set<E> union(Set<E> s1, Set<E> s2){
        Set result = new HashSet();
        result.add(s2);
        return result;
    }
}

