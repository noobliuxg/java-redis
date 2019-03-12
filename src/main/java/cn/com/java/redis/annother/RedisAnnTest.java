package cn.com.java.redis.annother;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedisAnnTest {

    public static void main(String[] args){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-redis.xml");
        RedisServer server = (RedisServer)applicationContext.getBean("redisServer");
        System.out.println(server.testAnnMethod("252525"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("缓存后：");
        System.out.println(server.testAnnMethod("252525"));
    }
}
