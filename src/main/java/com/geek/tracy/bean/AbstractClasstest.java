package com.geek.tracy.bean;

/**
 * @Author Tracy
 * @Date 2023/9/19
 */
public class AbstractClasstest {

    static abstract class A {
        public void fun() {
            System.out.println("具体的方法体");
        }

        public abstract void print();
    }

    static class B extends A {

        @Override
        public void print() {
            System.out.println("B extends A，override print()");
        }
    }

    public static void main(String[] args) {
        A a = new B();
        a.print();
    }
}
