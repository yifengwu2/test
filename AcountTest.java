package com.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

//模拟两个人转账
class Acount {
    private int money;

    public Acount(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean change(Acount target, int amount) {
        synchronized (this) {
            synchronized (target) {
                if (money >= amount) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    this.setMoney(this.money - amount);
                    target.setMoney(target.money + amount);
                    return true;

                }
                return false;
            }
        }
    }
}

@Slf4j
public class AcountTest {
    public static void main(String[] args) throws InterruptedException {
        Acount sender = new Acount(1000);
        Acount target = new Acount(1000);
        Thread t1 = new Thread(() -> {
            //模拟睡眠时间
            Sleeper.RandomSleep();

            sender.change(target, 900);

        }, "t1");
        t1.start();
        Thread t2 = new Thread(() -> {
            Sleeper.RandomSleep();

            target.change(sender, 800);

        }, "t2");
        t2.start();

        t1.join();
        t2.join();
        log.debug("sender的余额{},target的余额{}", sender.getMoney(), target.getMoney());

    }


}

class Sleeper {
    private static final Random random = new Random();

    public static void RandomSleep() {
        try {
            Thread.sleep(random.nextInt(500));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
}
