package cn.com.java.redis;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    public static void main(String[] args){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        System.out.println("当前时间："+System.currentTimeMillis());
        for (int i=0;i<=4;i++){
            new Thread(new Reader(cyclicBarrier)).start();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Reader implements Runnable{

        private CyclicBarrier cyclicBarrier;
        public Reader(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            try {
                long nowTime = System.currentTimeMillis();
                System.out.println("当前线程："+Thread.currentThread().getName()+"在执行写入操作,当前线程写入完的操作时间："+nowTime);
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("等待所有的线程都执行完毕,当前时间："+System.currentTimeMillis());
        }
    }
}
