package cn.com.java.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {

    private RedisUtils(){

    }

    /**
     * redis host
     */
    private static final String HOST = "127.0.0.1";
    /**
     * redis port
     */
    private static final Integer PORT = 6379;
    /**
     * redis password
     */
//    private static final String PASSWORD = "123456";
    /**
     *connnect max num
     */
    private static final int MAX_ACTIVE = 1024;
    /**
     * controller pool max status idle
     */
    private static final int MAX_IDLE = 200;
    /**
     * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
     */
    private static final int MAX_WAIT = 10000;
    /**
     * 连接超时的时间
     */
    private static final int TIMEOUT = 10000;
    /**
     * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
     */
    private static final boolean TEST_ON_BORROW = true;
    /**
     * redis pool
     */
    private static JedisPool pool = null;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(TEST_ON_BORROW);
        pool = new JedisPool(config,HOST,PORT,TIMEOUT);
    }

    public static synchronized Jedis getJedis(){
        if (pool!=null){
            Jedis resource = pool.getResource();
            return resource;
        }
        return null;
    }

    public static void returnResource(final Jedis jedis){
        jedis.close();
    }

    public static void close(){
        pool.close();
    }

}
