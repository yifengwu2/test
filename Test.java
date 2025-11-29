package com.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    static int r = 0;

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Thread t = new Thread(() -> {
            System.out.println("t线程开始");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            r = 10;
        });
        t.start();
        try {
            t.join();//不等待线程执行结束，输出的10;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("r的值" + r);
    }

}
