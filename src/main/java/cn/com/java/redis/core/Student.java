package cn.com.java.redis.core;

public class Student {

    private RedisCacheUtils cacheManager;

    public void setCacheManager(RedisCacheUtils cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void testString(){
        String AA = "AAAA";
        cacheManager.set("tempValue",AA);
        System.out.println(cacheManager.get("tempValue"));
    }

}
