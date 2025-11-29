package com.juc;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j(topic = "TestBiased")
public class TestBisased {
    public static void main(String[] args) throws InterruptedException {
        // 延迟一下，确保偏向锁已启用（或者加 -XX:BiasedLockingStartupDelay=0）
        Thread.sleep(5000);

        Dog d = new Dog();
        log.debug(ClassLayout.parseInstance(d).toPrintable());
        // 🔹 状态：【可偏向】（匿名偏向）
        // ✅ Mark Word 特征：末三位是 101
        // 💡 说明：对象刚创建，JVM 准备好支持偏向锁，但还没有绑定任何线程
        // 📌 此时 hashcode 未生成，如果调用 System.identityHashCode() 会禁用偏向
        Thread t1 = new Thread(() -> {
            log.debug("t1开始");
            log.debug(ClassLayout.parseInstance(d).toPrintable());
            // 🔹 状态：仍然是【可偏向】
            // 💡 因为还没进入 synchronized，所以没触发加锁

            synchronized (d) {
                log.debug("获取锁之后");
                log.debug(ClassLayout.parseInstance(d).toPrintable());
                // 🔹 状态：【偏向锁】（已偏向 t1）
                // ✅ Mark Word 包含 t1 的线程 ID + epoch + 101
                // 💡 第一次由 t1 加锁，成功获得偏向权
                // 🧠 偏向锁不会修改对象头的引用指针，而是记录持有者线程ID
            }
            log.debug("t1,释放锁之后");
            log.debug(ClassLayout.parseInstance(d).toPrintable());
            // 🔹 状态：仍然是【偏向锁】（仍偏向 t1）
            // 💡 偏向锁释放后并不清空线程ID，下次 t1 再来还能快速获取
            // 📌 这就是“偏向”的含义：偏爱某个线程
            synchronized (TestBisased.class) {
                TestBisased.class.notify();
            }
        }, "t1");
        t1.start();

        new Thread(() -> {
            log.debug("t2开始");
            log.debug(ClassLayout.parseInstance(d).toPrintable());
            // 🔹 状态：【偏向锁】（仍偏向 t1）
            // ⚠️ 注意：t2 看到的是同一个对象 d，它的 Mark Word 仍然指向 t1
            // 💡 此时 t2 尚未尝试抢锁，只是读取布局

            synchronized (TestBisased.class) {
                try {
                    TestBisased.class.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            synchronized (TestBisased.class) {
                log.debug("t2获取锁");
                log.debug(ClassLayout.parseInstance(d).toPrintable());
                // 🔹 状态：【轻量级锁】（或可能升级为重量级）
                // ❗关键点来了：
                // 当 t2 发现锁被 t1 偏向时，会触发：
                // 1. 检查 t1 是否还在运行临界区？
                //    - 如果不在（如已释放），则尝试 CAS 抢锁 → 成功则变为轻量级锁
                //    - 如果在，则膨胀为重量级锁
                // 2. 在本例中，t1 已释放，所以通常会升级为【轻量级锁】
                // ✅ Mark Word 特征：指向栈帧中的 Lock Record 指针 + 末两位 00
                // 🧱 轻量级锁使用 CAS + 自旋，不涉及操作系统调度
            }
            log.debug("t2释放锁");
            log.debug(ClassLayout.parseInstance(d).toPrintable());
            // 🔹 状态：【无锁】或【轻量级解锁后恢复】
            // ✅ Mark Word 恢复为 01 结尾（无锁可偏向）或 001（普通无锁）
            // 💡 因为不再有线程持有锁，且没有固定偏向目标

        }).start();
    }
}

class Dog {

}
