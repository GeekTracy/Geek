package com.geek.tracy.reflect.bean;

/**
 * BYD 车，需要被增强的类
 * @Author Tracy
 * @Date 2023/6/12
 */
public class BydCar implements Car{

    @Override
    public void start() {
        // 点火
        System.out.println("start the engine...");
    }

    @Override
    public void stop() {
        // 点火
        System.out.println("stop the engine...");
    }
}
