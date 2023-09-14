package com.geek.tracy.concurrent.synchronizer;

import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * Exchanger 交换器：两个线程之间数据交互
 *
 * @Author Tracy
 * @Date 2023/9/14
 */
public class ExchangerExample {

    /**
     * 同城线下交易，约定地点一手交钱一手交货
     */
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        // 交换超时方法，设置超时时间，超时了则会抛出异常
        // exchanger.exchange(V x, long timeout, TimeUnit unit)
        new Thread(() -> {
            try {
                int time = new Random().nextInt(1500);
                Thread.sleep(time);
                System.out.println(Thread.currentThread().getName() + "，耗时：" + time + "，到达交易点天台");
                String exchange = exchanger.exchange("cash 50 Millon $");
                System.out.println(Thread.currentThread().getName() + "，拿到了货物：" + exchange + "，准备大干一笔");
            } catch (InterruptedException e) {

            }
        }, "买方").start();

        new Thread(() -> {
            try {
                int time = new Random().nextInt(1500);
                Thread.sleep(time);
                System.out.println(Thread.currentThread().getName() + "，耗时：" + time + "，到达交易点天台");
                String exchange = exchanger.exchange("AK 47 一箱");
                System.out.println(Thread.currentThread().getName() + "，拿到了现金：" + exchange + "，准备去嗨");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "卖方").start();
    }
}
