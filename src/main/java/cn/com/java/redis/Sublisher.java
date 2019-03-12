package cn.com.java.redis;

import redis.clients.jedis.Jedis;

public class Sublisher implements Runnable{
    private Jedis jedis = null;

    private String channal = null;

    private Subscriber subscribe = null;

    public Sublisher(Jedis jedis){
        this.jedis = jedis;
    }

    public Sublisher(Jedis jedis,String channal,Subscriber subscribe){
        this.jedis = jedis;
        this.channal = channal;
        this.subscribe = subscribe;
    }

    public void setChannel(String channal){
        this.channal = channal;
    }

    private boolean flag = true;
    @Override
    public void run() {
        while (flag){
            jedis.subscribe(subscribe, channal);
        }
    }

    public void close(){
        flag = false;
    }
}
