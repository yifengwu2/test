package com.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.logging.Logger;

@Slf4j(topic = "Test02")
public class Test02 {
    //    private static final Logger logger = Logger.getLogger(Test02.class.getName());
    public static void main(String[] args) throws InterruptedException {
//        Thread t = new Thread(() -> {
//            try {
//                //sleep、wait、join 方法都会让线程进入阻塞状态，打断线程**会清空打断状态**（false）
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                System.out.println("异常信息" + e.getMessage());
//            }
//        });
//        t.start();
//        Thread.sleep(500);
//        t.interrupt();
//        log.debug("打断状态{}", t.isInterrupted());// 打断状态: {}false

        Thread t = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            System.out.println(Thread.currentThread().isInterrupted());
        });

        t.start();
        Thread.sleep(2000);
        System.out.println("t1:"+t.isInterrupted());
        t.interrupt();
        System.out.println("t1:"+t.isInterrupted());


    }

}
