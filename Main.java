package com.juc;

public class Main {
    public static void main(String[] args) {
//        ThreadSafe obj = new ThreadSafe();

//        //两个线程同时调用method1
//        new Thread(() -> {
//            obj.method1(10);
//        }).start();
//
//        new Thread(() -> {
//            obj.method1(10);
//        }).start();

        ThreadSafe subObj = new ThreadSafeSubClass();
        subObj.method1(1000);


    }
}
