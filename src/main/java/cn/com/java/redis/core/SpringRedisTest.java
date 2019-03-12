package cn.com.java.redis.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRedisTest {

    public static void main(String[] args){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-redis.xml");
        RedisCacheUtils redisCacheManager = (RedisCacheUtils)applicationContext.getBean("cacheManager");
        Student student = new Student();
        student.setCacheManager(redisCacheManager);
        student.testString();
        System.out.println(redisCacheManager.get("tempValue"));
        boolean value = redisCacheManager.expire("tempValue", 3);
        System.out.println("设置key的有效时间为："+value);
        long tempValue = redisCacheManager.getExpire("tempValue");
        System.out.println("有效时间："+tempValue);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("睡眠4s后key=tempValue的值："+redisCacheManager.get("tempValue"));
    }
}
