package com.geek.tracy.concurrent.synchronizer;

import com.google.common.collect.Lists;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch
 * 注意：
 *      1）countDown() 放在finally中，防止业务操作的时候异常导致countDown()失败，导致主线程一直阻塞；
 *      2）await() 建议设置过期时间，防止countDown()异常，导致计数器不能为0一直阻塞主线程；
 *      3）计数器数值不可以重置；
 *      4）CountDownLatch会阻塞主线程；CountDownLatch则是通过AQS的“共享锁”实现
 * @Author Tracy
 * @Date 2023/9/6
 */
public class CountDownLatchExample {

    public static void main(String[] args) {
//        studentLeaveSchool();
        studentSplitLeaveSchool();
    }

    /**
     * 学生放学离校。全部离校后，在家长群通知！
     */
    public static void studentLeaveSchool() {
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        final int num = 50; // 学生人数
        CountDownLatch countDownLatch = new CountDownLatch(num);
        StopWatch watch = new StopWatch();
        watch.start("放学铃声响起...");
        for (int i = 0; i < num; i++) {
            int sduNum = i + 1;
            threadPool.execute(() ->{
                try {
                    // 离校前准备doing sth
                    int spentTime = new Random().nextInt(2000); // 离校花费的时间
                    System.out.println("Student " + sduNum + " leave school took: " + spentTime + " ms");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // notes: countDown() 放在finaly中，防止业务操作的时候异常导致countDown()失败，导致主线程一直阻塞
                    // 计算器-1
                    countDownLatch.countDown();
                    System.out.println("there still has " + countDownLatch.getCount() + " in classroom");
                }
            });
        }
        watch.stop();
        try {
            watch.start("所有学生已准备离校中");
            // notes: await() 建议设置过期时间，防止countDown()异常，导致计数器不能为0一直阻塞主线程
            // 计算器阻塞，当计数器为0时通过，阻塞主线程
            boolean await = countDownLatch.await(5000, TimeUnit.SECONDS);
            watch.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.shutdown(); // 关闭线程池
        System.out.println("微信群发消息，告知各家长，所有学生均已离校");
        System.out.println(watch.prettyPrint());
    }

    /**
     * 学生分批放学离校，优化。全部离校后，在家长群通知！
     */
    public static void studentSplitLeaveSchool() {
        Integer[] temp = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        List<Integer> students = new ArrayList<>(Arrays.asList(temp));
        List<List<Integer>> partition = Lists.partition(students, 5);
        System.out.println("学生分为："+partition.size()+"组");
        for (int k = 0; k < partition.size(); k++) {
            System.out.println("第："+(k+1)+"组开始离开");
            List<Integer> integerList = partition.get(k);
            int size = integerList.size();
            final CountDownLatch countDownLatch = new CountDownLatch(size);
            for (Integer integer : integerList) {
                fixedThreadPool.execute(() ->
                        {
                            try {
                                System.out.println("学号为" + integer + "的学生开始离开");
                                Thread.sleep(3000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                countDownLatch.countDown();
                            }
                        }

                );
            }
            try {
                boolean await = countDownLatch.await(50, TimeUnit.SECONDS);
                System.out.println(await ? "计算器归0，该组同学全部离校" : "计算器：" + countDownLatch.getCount() + "，阻塞超时！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fixedThreadPool.shutdown();
        System.out.println("发送通知给家长");
    }
}
