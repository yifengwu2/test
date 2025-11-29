package com.juc;

import java.util.ArrayList;
import java.util.Arrays;

public class ThreadSafe {
    //主方法，调用method2和method3
    public void method1(int loopNumber) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < loopNumber; i++) {
            method2(list);//添加元素
            method3(list);//删除第一个元素
        }
    }

    //添加元素
    public void method2(ArrayList<String> list) {
        list.add("1");
    }

    //删除第一个元素
    public void method3(ArrayList<String> list) {
        list.remove(0);
    }
}

class ThreadSafeSubClass extends ThreadSafe {
    @Override
    public void method3(ArrayList<String> list) {
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}
