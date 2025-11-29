package com.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadDemo02 {
    public static void main(String[] args) {
        FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() {
                System.out.println("修改了。。。");
                return Thread.currentThread().getName() + "-> Hello World";
            }
        });

        Thread t = new Thread(task);
        t.start();

        try {
            System.out.println(task.get());// 获取call方法返回的结果（正常/异常结果）
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
