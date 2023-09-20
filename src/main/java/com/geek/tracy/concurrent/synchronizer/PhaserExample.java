package com.geek.tracy.concurrent.synchronizer;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 *
 * Phaser：阶段协同器
 * @Author Tracy
 * @Date 2023/9/11
 */
public class PhaserExample {
    // parties
    public static final int PARTIES = 3;
    // 阶段：phase
    public static final int PHASES = 3;

    /**
     *  在CyclicBarrier、CountDownLatch中，我们使用计数器来控制程序的顺序执行，同样的在Phaser中也是通过计数器来控制。在Phaser中计数器叫
     *  做parties，我们可以通过Phaser的构造函数或者register()方法来注册。
     *  通过调用register()方法，我们可以动态的控制phaser的个数。如果我们需要取消注册，则可以调用arriveAndDeregister()方法。
     *
     *
     */
    public static void main(String[] args) {

        // 定义:阶段协同器
        Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                // 参与者数量，去除主线程
                int persons = registeredParties;
                switch (phase) {
                    case 0:
                        System.out.println("大家集合完毕，准备去景点1，人数：" + persons);
                        break;
                    case 1:
                        System.out.println("大家集合完毕，准备去景点2，人数：" + persons);
                        break;
                    case 2:
                        System.out.println("大家集合完毕，准备去景点3，人数：" + persons);
                        break;
                    case 3:
                        System.out.println("大家集合完毕，准备去景点4，人数：" + persons);
                        break;
                    case 4:
                        System.out.println("大家集合完毕，准备去景点5，人数：" + persons);
                        break;
                    case 5:
                        System.out.println("大家集合完毕，准备上大巴车返程，人数：" + persons);
                        break;
                }
                // 判断是否只剩下一个主线程，如果是返回true，代表终止
                return registeredParties == 0;
            }
        };

        // 主线程注册
        phaser.register();

        // 旅游团5个人
        for (int i = 0; i < 5; i++) {
            // 每个人注册一次
            phaser.register();
            new Thread(() -> {
                try {
                    Vacation vacation = new Vacation();
                    // 阶段一：集合，准备前往景点1
                    vacation.gather();
                    phaser.arriveAndAwaitAdvance(); // 线程集合等待
                    // 阶段二：准备前往景点2
                    vacation.visitViewPoint1();
                    phaser.arriveAndAwaitAdvance(); // 线程集合等待
                    // 阶段三：准备前往景点3
                    vacation.visitViewPoint2();
                    phaser.arriveAndAwaitAdvance(); // 线程集合等待
                    // 阶段四：准备前往景点4
                    vacation.visitViewPoint3();
                    phaser.arriveAndAwaitAdvance(); // 线程集合等待
                    // 阶段五：准备前往景点5
                    vacation.visitViewPoint4();
                    phaser.arriveAndAwaitAdvance(); // 线程集合等待
                    // 阶段六：准备返程
                    vacation.visitViewPoint5();
                    phaser.arriveAndAwaitAdvance(); // 线程集合等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }, "Thread " + (i + 1)).start();
        }
        // 后续阶段，主线程不参加
        phaser.arriveAndDeregister();
    }

    /**
     * 报团旅游活动，跟团旅游景点
     */
    static class Vacation {
        public String name = "伙伴【" + Thread.currentThread().getName() + "]";

        // 出发集合
        public void gather() throws InterruptedException {
            int time = new Random().nextInt(1500);
            System.out.println(name + "：从家里出发...running");
            Thread.sleep(time);
            System.out.println(name + "：从家里出发，经过" + time + "，到达集合点");
        }
        // 参观景点1
        public void visitViewPoint1() throws InterruptedException {
            int time = new Random().nextInt(1500);
            System.out.println(name + "：开始参观景点1...visiting");
            Thread.sleep(time);
            System.out.println(name + "：在景点1，参观了：" + time);
        }

        // 参观景点2
        public void visitViewPoint2() throws InterruptedException {
            int time = new Random().nextInt(1500);
            System.out.println(name + "：开始参观景点2...visiting");
            Thread.sleep(time);
            System.out.println(name + "：在景点2，参观了：" + time);
        }
        // 参观景点3
        public void visitViewPoint3() throws InterruptedException {
            int time = new Random().nextInt(1500);
            System.out.println(name + "：开始参观景点3...visiting");
            Thread.sleep(time);
            System.out.println(name + "：在景点3，参观了：" + time);
        }

        // 参观景点4
        public void visitViewPoint4() throws InterruptedException {
            int time = new Random().nextInt(1500);
            System.out.println(name + "：开始参观景点4...visiting");
            Thread.sleep(time);
            System.out.println(name + "：在景点4，参观了：" + time);
        }
        // 参观景点5
        public void visitViewPoint5() throws InterruptedException {
            int time = new Random().nextInt(1500);
            System.out.println(name + "：开始参观景点5...visiting");
            Thread.sleep(time);
            System.out.println(name + "：在景点5，参观了：" + time);
        }
    }


}
