package com.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class ExerciseSell {
    public static void main(String[] args) throws InterruptedException {
        TicketWindow window = new TicketWindow(1000);
        List<Integer> list = new Vector<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(10)); // 随机延迟 0~10ms，制造并发高峰
                } catch (InterruptedException ignored) {}
                int count = window.sell(random.nextInt(5) + 1);
                list.add(count);
            });
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("卖出去的票数" + list.stream().mapToInt(i -> i).sum());
        System.out.println("剩余票数" + window.getTicket());


    }

    private static final Random random = new Random();

}

//售票窗口
class TicketWindow {
    private int ticket;

    public TicketWindow(int ticket) {
        this.ticket = ticket;
    }

    public  int getTicket() {
        return ticket;
    }

    public synchronized int sell(int amount) {
        if (ticket >= amount) {
            ticket -= amount;
            return amount;
        }
        return 0;
    }
}
