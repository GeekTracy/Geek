package com.geek.tracy.lambda.fourInterface;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Author yang
 * @Date 2022/1/28
 */
public class PredicateTest {

    /**
     * 断言型接口--Predicate
     * 参数：T
     * 返回：boolean
     */
    @Test
    public void test() {
        // 1原始写法
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.equals("hello");
            }
        } ;
        System.out.println(predicate.test("hello"));
        System.out.println("-----------------------------");

        Predicate<Object> equal = Predicate.isEqual("你好");
        System.out.println(equal.test("你好"));
        System.out.println("-----------------------------");
        // 2简单写法
        Predicate<Integer> predicate1 = x -> x > 10;
        System.out.println(predicate1.test(5));
        System.out.println(predicate1.negate().test(5));
        System.out.println(predicate1.or(x -> x % 5 == 0).test(9));

    }

    @Test
    public void testPredicate() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str)->str.startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str)->str.endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str)->true);

        System.out.println("Print no language : ");
        filter(languages, (str)->false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str)->str.length() > 4);
    }

    public void filter(List<String> names, Predicate<String> condition) {
        for(String name: names)  {
            if(condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

}
