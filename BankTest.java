package com.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

class Bank {
    private int money;

    public Bank(int money) {
        this.money = money;
    }

    public synchronized boolean take(int amount) {
        if (money > amount) {
            money -= amount;
            return true;
        }
        return false;
    }

    public int getMoney() {
        return money;
    }
}

public class BankTest {
    private static final Random random = new Random();

    public static int getRandomMoney() {
        return random.nextInt(1000);
    }

    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank(2000);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(Thread.currentThread().getName() + bank.take(BankTest.getRandomMoney()));
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(bank.getMoney());

    }


}
