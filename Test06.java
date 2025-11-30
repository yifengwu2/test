package com.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test06 {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                log.debug("获取锁");
                try {
//                    Thread.sleep(20000);
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }, "t1");
        t1.start();
        try {
            Thread.sleep(1000);
            synchronized (lock) {
                log.debug("获取锁");
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
}
