package com.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Test03")
public class Test03 {
    public static Thread getThread1() {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            log.debug("烧开水");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        },"t1");
        t1.start();
        return t1;
    }

    public static Thread getThread2() {
        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶，洗茶杯，拿茶叶");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        },"t2");
        t2.start();
        return t2;
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = Test03.getThread1();
        Thread t2 = Test03.getThread2();
        t1.join();
        t2.join();
        log.debug("开始泡茶");


    }
}
