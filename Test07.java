package com.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test07 {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没[{}]", hasCigarette);
                if (!hasCigarette) {
                    log.debug("没烟休息会");
//                    Sleeper(2);
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                log.debug("有烟没[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("小南开始干活");
                }
            }
        }, "小南");
        t1.start();

        for (int i = 0; i <= 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    log.debug("可以开始干活");
                }

            }, "其他人").start();
        }
        Sleeper(1);
        new Thread(() -> {
            synchronized (room) {
                hasCigarette = true;
                log.debug("烟送到了");
                room.notify();
            }
        }, "送烟").start();
    }

    private static void Sleeper(int i) {
        try {
            Thread.sleep(i * 1000L);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
