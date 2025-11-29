package com.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Test04")
public class Test04 {
    static int counter = 0;
    private Room room = new Room();

    public Room getRoom() {
        return room;
    }

    public static void main(String[] args) throws Exception {
        Test04 test04 = new Test04();
        Room room = test04.getRoom();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                room.increatment();
            }

        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                room.decrement();
            }
        }, "t2");
        t2.start();
        t1.start();

        t1.join();
        t2.join();
        log.debug("counter:{}", room.getCounter());
    }
}

class Room {
    private int counter = 0;

    public synchronized void increatment() {
        counter++;
    }

    public synchronized void decrement() {
        counter--;
    }

    public int getCounter() {
        return counter;
    }
}
