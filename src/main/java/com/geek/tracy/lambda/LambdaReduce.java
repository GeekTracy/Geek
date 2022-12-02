package com.geek.tracy.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * reduce
 *
 * @Author yang
 * @Date 2022/2/8
 */
public class LambdaReduce {

    /**
     * reduce 有3个默认方法
     */
    @Test
    public void test() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        // 1. Optional<T> reduce(BinaryOperator<T> accumulator);
        Integer result = stream.reduce((a, b) -> {
            System.out.println("a: " + a + ",b: " + b);
            return a + b;
        }).get();
        System.out.println(result);

        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5);
        // 2. T reduce(T identity, BinaryOperator<T> accumulator);
        // 多了一个字定义初始值identity
        Integer result1 = stream1.reduce(1, (a, b) -> {
            System.out.println("a: " + a + ",b: " + b);
            return a + b;
        });
        System.out.println(result1);

        // 3. <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator,
        //                 BinaryOperator<U> combiner);
        // eg: 多了一个字定义初始值identity和conbiner（可忽略）
        // 第三种签名的用法相较前两种稍显复杂，由于前两种实现有一个缺陷，它们的计算结果必须和stream中的元素类型相同，如上面的代码示例，
        // stream中的类型为int，那么计算结果也必须为int，这导致了灵活性的不足，甚至无法完成某些任务， 比入我们要对一个一系列int值求和，
        // 但是求和的结果用一个int类型已经放不下，必须升级为long类型，此实第三签名就能发挥价值了，它不将执行结果与stream中元素的类型绑死。
        // 当然这只是其中一种应用罢了，犹豫拜托了类型的限制我们还可以通过他来灵活的完成许多任务，比入将一个int类型的ArrayList转换成一个String类型的ArrayList
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6);
        ArrayList<String> result3 = numList.stream().reduce(new ArrayList<>(), (a, b) -> {
            System.out.println("a: " + a + ",b: " + b);
            a.add("element-" + b);
            return a;
        }, (a, b) -> null); // 另外，还需要注意的是这个reduce的签名还包含第三个参数，一个BinaryOperator<U>类型的表达式。在常规情况下我们可以忽略这个参数，敷衍了事的随便指定一个表达式即可，目的是为了通过编译器的检查
        System.out.println(result3);


    }
}
