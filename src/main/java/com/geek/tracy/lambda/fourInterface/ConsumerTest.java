package com.geek.tracy.lambda.fourInterface;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 消费型接口--Consumer
 *
 * @Author yang
 * @Date 2022/1/28
 */
public class ConsumerTest {

    /**
     * 消费型接口--Consumer
     * 参数：T
     * 返回：void
     */
    @Test
    public void test() {
        // 初始化 Consumer
        Consumer<String> consumer = new Consumer<String>() {
            /**
             * Consumer 消费型接口，无返回。
             */
            @Override
            public void accept(String s) {
                // 方法体
                System.out.println("Consumer 消费型接口，无返回。");
                System.out.println("accept 无返回值，打印入参：" + s);
            }
        };

        // 示例1
        consumer.accept("示例1入参");
        String input = "入参";
        consumer.andThen((String s) -> System.out.println("andThen-->" + input)).accept(input);
    }

    /**
     * 题目
     * 下面的字符串数组当中存有多条信息，请按照格式“ 姓名:XX。性别:XX。 ”的格式将信息打印出来。要求将打印姓名的动作作为第一个 Consumer
     * 接口的Lambda实例，将打印性别的动作作为第二个 Consumer 接口的Lambda实 例，将两个 Consumer 接口按照顺序“拼接”到一起。
     * String[] array = { "大雄，男", "静香，女", "胖虎，男" };
     */
    @Test
    public void testExam() {
        String[] array = { "大雄，男", "静香，女", "胖虎，男" };
        Stream.of(array).forEach(item -> printFunction(
                param -> System.out.print("姓名：" + item.split("，")[0] + "。"),
                param -> System.out.print("性别：" + item.split("，")[1] + "。"),
                item
                )
        );

    }

    private void printFunction(Consumer<String> consumer1, Consumer<String> consumer2, String param){
        consumer1.andThen(consumer2).accept(param);
        System.out.println();
    }
}
