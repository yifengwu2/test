package com.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test08 {
    static String response;
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                log.debug("response是否为空[{}]", response);
                while (response == null) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (response != null) {
                    log.debug("response:{}", response);
                }


            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                response = "abc";
                lock.notify();
            }

        }, "t2");
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
}
