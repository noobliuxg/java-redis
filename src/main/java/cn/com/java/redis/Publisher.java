package cn.com.java.redis;

import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Publisher implements Runnable{

    private Jedis jedis = null;

    private String channal = null;

    public Publisher(Jedis jedis){
        this.jedis = jedis;
    }

    public Publisher(Jedis jedis,String channal){
        this.jedis = jedis;
        this.channal = channal;
    }

    public void setChannel(String channal){
        this.channal = channal;
    }

    private boolean flag = false;

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true){
                String readLine = reader.readLine();
                jedis.publish(((channal==null)?"defaultChannel":channal),readLine);
                if ("quite".equals(readLine)){
                    flag = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭系统资源 flag="+flag);
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jedis.close();
        }
    }

    public boolean isClose(){
        return flag;
    }
}
