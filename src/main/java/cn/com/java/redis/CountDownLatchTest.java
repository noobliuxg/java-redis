package cn.com.java.redis;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"执行，睡眠2s");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println(Thread.currentThread().getName()+"执行完毕，计数器递减");
            }
        },"Thread-1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"执行，睡眠2s");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println(Thread.currentThread().getName()+"执行完毕，计数器递减");
            }
        },"Thread-2").start();
        long oldTime = System.currentTimeMillis();
        System.out.println("当前await之前的时间"+oldTime);
        latch.await();
        System.out.println("主线程被唤醒，往下执行的时间"+(System.currentTimeMillis()-oldTime));
    }
}
