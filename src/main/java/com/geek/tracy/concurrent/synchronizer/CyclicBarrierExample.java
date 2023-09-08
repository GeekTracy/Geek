package com.geek.tracy.concurrent.synchronizer;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 循环屏障
 *      1）每一次调用await()方法都将使阻塞的线程数+1，只有阻塞的线程数达到设定值时，屏障才会打开，允许阻塞的所有线程继续执行；
 *      2）CyclicBarrier的计数器可以重置，而CountDownLatch不行，这意味着CyclicBarrier实例可以被重复使用，而CountDownLatch只能使用一次；
 *      3）CyclicBarrier可以自定义barrierAction操作，这是个可选操作，可以再创建CyclicBarrier对象时指定；
 *
 * @Author Tracy
 * @Date 2023/9/7
 */
public class CyclicBarrierExample {

    public static void main(String[] args) {
        // 第一版：聚餐
        waitToHaveLunch();
    }

    /**
     * 公司聚餐，10名员工到齐后开饭；开饭前，所有线程均在await()处阻塞，直到阻塞的线程数量=parties时，所有线程被激活执行
     */
    public static void waitToHaveLunch() {
        final int num = 10;  // 10名员工聚餐
        // 初始化循环屏障，parties:10
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            System.out.println("人已到齐..........");
        });
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(() -> {
                try {
                    // 员工到达聚餐地点花费的时间
                    System.out.println(Thread.currentThread().getName() + ", 出门");

                    int time = new Random().nextInt(3000);
                    Thread.sleep(time);

                    System.out.println(Thread.currentThread().getName() + ", 到达，花费了：" + time + " ms");

                    long arriveTime = System.currentTimeMillis();
                    // 该员工到达后开始等待其他人到齐，即当阻塞线程数量=parties时，所有线程将被激活执行
                    cyclicBarrier.await();
                    long allArrivedTime = System.currentTimeMillis();
                    System.out.println("所有人到齐，准备开饭！" + Thread.currentThread().getName() + "，等待了：" + (allArrivedTime - arriveTime) + " ms");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }, "员工" + (i + 1) + "号");
            thread.start();
        }
    }
}
