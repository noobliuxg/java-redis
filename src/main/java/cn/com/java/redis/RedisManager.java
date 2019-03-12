package cn.com.java.redis;

import redis.clients.jedis.Jedis;

public class RedisManager {

    class RedisString{
        private Jedis jedis = null;
        public RedisString(Jedis jedis){
            jedis = jedis;
        }

        public void setString(String key,String param){
            jedis.set(key,param);
        }

        public String get(String key){
            return jedis.get(key);
        }
    }
}
