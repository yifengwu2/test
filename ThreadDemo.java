package com.juc;

public class ThreadDemo {
    public static void main(String[] args) {
//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 50; i++) {
//                System.out.println("t1:" + i);
//            }
//        }, "t1");
//
//        t1.start();

        Thread t = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    System.out.println("t:" + i);
                }
            }
        };
        t.start();
        //main线程
        for (int i = 0; i < 50; i++) {
            System.out.println("main:" + i);
        }
    }
}
