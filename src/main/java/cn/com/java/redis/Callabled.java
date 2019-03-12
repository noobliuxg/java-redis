package cn.com.java.redis;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Callabled {

    public static void main(String[] args){
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "AAA";
            }
        };
        FutureTask task = new FutureTask(callable);
        new Thread(task).start();

        System.out.println(System.currentTimeMillis());
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis());
    }
}
