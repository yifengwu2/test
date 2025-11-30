package com.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test05 {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    log.debug("t1开始等待");
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                log.debug("其他代码");
            }

        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    log.debug("t2开始等待");
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            log.debug("其他代码");
        }, "t2");
        t2.start();

        Thread.sleep(2000);
        synchronized (lock) {
            //唤醒obj上一个线程
            lock.notifyAll();
        }

    }
}
