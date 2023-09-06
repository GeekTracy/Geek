package com.geek.tracy.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自旋锁：自旋锁不是一种锁（类型），自旋锁是线程没有获取到锁时的一种等待策略
 * @Author Tracy
 * @Date 2023/9/4
 */
public class SpinlockTest {

    private final AtomicBoolean available = new AtomicBoolean(false);

    public void lock() {
        // 循环不断获取锁
        while (!tryLock()) {
            // do something
        }
    }

    // 获取锁，false --> true
    public boolean tryLock() {
        return available.compareAndSet(false, true);
    }

    // 释放锁
    public void unlock() {
        if (!available.compareAndSet(true, false)) {
            throw new RuntimeException("unlock fail!");
        }
    }
}
