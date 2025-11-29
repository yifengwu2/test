package com.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Demo")
public class Demo {
    static int count = 0;
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                synchronized (lock) {
                    count++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                synchronized (lock) {
                    count--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
            log.info(String.valueOf(count));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }


    }
}
