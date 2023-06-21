package com.geek.tracy.reflect;

import com.geek.tracy.reflect.bean.BydCar;
import com.geek.tracy.reflect.bean.Car;
import com.geek.tracy.reflect.handler.CarInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * 动态代理测试类
 *  -- Proxy。newProxyInstance 创建代理类
 * @Author Tracy
 * @Date 2023/6/12
 */
public class ProxyTest {

    /**
     * 动态代理，可以在不修改原代码的情况下，进行功能的增强，在InvocationHandler的invoke方法中，添加具体的增强功能，
     * 例如：添加日志、事务增强、性能监控、安全检查、缓存等。
     */
    public static void main(String[] args) {
        Car byd = new BydCar();
        /**
         * 通过Proxy.newProxyInstance()创建代理类
         * loader：类加载器，将字节码文件加载到JVM；
         * interfaces：动态代理类需要实现的接口
         * h: 动态代理方法在执行时，会调用h里面的invoke方法去执行。
         */
        // 解释一下这里做的事情
        // 首先传递第一个参数为，我们需要调用的对象的classLoader，第二个参数为我们需要调用的对象的子类，此时Proxy是会找到对象以及对象的子类，将所有方法都找出来。
        // 然后存储到代理对象中，然后传递handler，handler重写了invoke方法，相当于一个回调函数，之后代理对象调用的所有方法实际上都是再invoke中使用反射调用方法。
        Car bydProxy = (Car) Proxy.newProxyInstance(byd.getClass().getClassLoader(), byd.getClass().getInterfaces(), new CarInvocationHandler(byd));

        bydProxy.start(); // 调用handler中invoke方法进行增强
        bydProxy.stop();
    }
}
