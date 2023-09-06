package com.geek.tracy.concurrent.synchronizer;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * Semaphore 信号量，允许多个线程同时访问
 * @Author Tracy
 * @Date 2023/9/5
 */
public class SemaphoreExample {

    public static void main(String[] args) {
        // 模拟停车场
        simulatedParkingLog();
    }

    /**
     * 模拟停车场
     *
     * acquire()
     * 获取一个令牌，在获取到令牌、或者被其他线程调用中断之前线程一直处于阻塞状态。
     *
     * acquire(int permits)
     * 获取一个令牌，在获取到令牌、或者被其他线程调用中断、或超时之前线程一直处于阻塞状态。
     *
     * acquireUninterruptibly()
     * 获取一个令牌，在获取到令牌之前线程一直处于阻塞状态（忽略中断）。
     *
     * tryAcquire()
     * 尝试获得令牌，返回获取令牌成功或失败，不阻塞线程。
     *
     * tryAcquire(long timeout, TimeUnit unit)
     * 尝试获得令牌，在超时时间内循环尝试获取，直到尝试获取成功或超时返回，不阻塞线程。
     *
     * release()
     * 释放一个令牌，唤醒一个获取令牌不成功的阻塞线程。
     *
     * hasQueuedThreads()
     * 等待队列里是否还存在等待线程。
     *
     * getQueueLength()
     * 获取等待队列里阻塞的线程数。
     *
     * drainPermits()
     * 清空令牌把可用令牌数置为0，返回清空令牌的数量。
     *
     * availablePermits()
     * 返回可用的令牌数量。
     *
     */
    public static void simulatedParkingLog() {
        final int carNum = 130; // 公司总车辆数
        Semaphore semaphore = new Semaphore(30); // 公司比较小，门口只能停10辆车

        for (int i = 0; i < carNum; i++) {
            Thread thread = new Thread(() -> {
                try {
                    // 当前可用令牌数
                    if (semaphore.availablePermits() == 0) {
                        System.out.println(Thread.currentThread().getName() +  "暂无车位，请耐心等待....");
                    }
                    semaphore.acquire(); // 获取令牌尝试进入停车场，获取到车位后，车位-1
                    System.out.println(Thread.currentThread().getName() + " 成功进入停车场！可使用车位：" + semaphore.availablePermits());
                    int parkingTime = new Random().nextInt(2000);
                    Thread.sleep(parkingTime); // 车辆随机停留一段时间
                    semaphore.release(); // 释放令牌，车位数+1
                    System.out.println(Thread.currentThread().getName() + " 停车" + parkingTime + " ms，驶出车场，可使用车位：" + semaphore.availablePermits());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "车辆" + (i + 1) + "号");
            thread.start();
        }
    }
}
