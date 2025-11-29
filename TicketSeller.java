package com.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "TicketSeller")
public class TicketSeller {
    public static void main(String[] args) {
        //一百张票
        Window window = new Window(20);
        Thread t1 = new Thread(() -> {
            int count = 0;
            while (window.sell()) {
                count++;
                log.debug("卖出第{}张票", count);
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            int count = 0;
            while (window.sell()) {
                count++;
                log.debug("卖出第{}张票", count);
            }

        }, "t2");
        t2.start();


    }
}

class Window {
    private int ticket;

    public Window(int ticket) {
        this.ticket = ticket;
    }

    //获取票数
    public synchronized int getTicket() {
        return ticket;
    }

    public synchronized boolean sell() {
        if (ticket > 0) {
            ticket--;
            return true;
        }
        return false;
    }
}
