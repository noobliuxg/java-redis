package cn.com.java.redis.annother;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component("redisServer")
public class RedisServer {


    @Cacheable(key = "testAnnMethod")
    public String testAnnMethod(String annother_key){
        String prefix = "redis_"+annother_key;
        System.out.println(prefix);
        return prefix;
    }
}
