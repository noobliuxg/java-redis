package cn.com.java.redis;


public class Nodify {

    public final static Object lockObject = new Object();

    public static void main(String[] args) throws InterruptedException {
        NodifyThread thread = new NodifyThread();
        new Thread(thread).start();
        new Thread(thread).start();
        new Thread(thread).start();
        Thread.sleep(1000);
        thread.notify();
    }

    static class NodifyThread implements Runnable{

        @Override
        public void run() {
            try {
                synchronized (this){
                    System.out.println("当前线程："+Thread.currentThread().getName()+"执行，进入睡眠");
                    this.wait();
                    System.out.println("当前线程："+Thread.currentThread().getName()+"睡眠结束");
                }
                this.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
