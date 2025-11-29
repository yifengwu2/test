package com.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Like {
    private int count = 0;

    // 原子化地完成：读旧值 → 判断 → 修改 → 返回结果
    public synchronized LikeResult TryLike() {

        int old = count;
        if (count >= 5000) {
            return new LikeResult(false, old, old);
        }
        count += 100;
        return new LikeResult(true, old, count);

    }

}

class LikeResult {
    final int newCount;
    final int oldCount;
    final boolean success;

    public LikeResult(boolean success, int oldCount, int newCount) {
        this.oldCount = oldCount;
        this.newCount = newCount;
        this.success = success;
    }
}

@Slf4j
public class LikeTest {
    private static final Random random = new Random();

    public static void sleepRandom() {
        try {
            Thread.sleep(random.nextInt(500));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Like like = new Like();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(() -> {
                //模拟睡眠
                LikeTest.sleepRandom();
                LikeResult result = like.TryLike();
                log.debug("线程{}尝试点赞->原数量:{} 新数量:{} 成功:{}",
                        Thread.currentThread().getName(),
                        result.oldCount,
                        result.newCount,
                        result.success);

            });
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }


    }
}
