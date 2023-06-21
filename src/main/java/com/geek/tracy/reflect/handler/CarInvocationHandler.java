package com.geek.tracy.reflect.handler;


import com.geek.tracy.reflect.bean.Car;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 继承InvocationHandler接口，重写invoke方法进行增强
 * @Author Tracy
 * @Date 2023/6/12
 */
public class CarInvocationHandler implements InvocationHandler {

    /** 成员变量 */
    private Car car;

    /**
     * 构造函数
     */
    public CarInvocationHandler(Car car) {
        this.car = car;
    }


    /**
     * 代理类会执行InvocationHandler 的 invoke方法，从而达到增强效果
     *
     * @param proxy：就是代理对象，newProxyInstance()方法的返回对象
     * @param method：调用的方法
     * @param args: 方法中的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("...功能增强，检查车机状态....车机状态正常.");
        Object invoke = method.invoke(car, args);
        System.out.println("...功能增强结束。");
        return invoke;
    }
}
