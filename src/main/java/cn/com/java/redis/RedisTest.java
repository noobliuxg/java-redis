package cn.com.java.redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisTest {

    /**
     *string
     */
    public static void testString(){
        Jedis jedis = RedisUtils.getJedis();

        jedis.set("name","liuxg");
        System.out.println(jedis.get("name"));

        jedis.set("name",".com");
        System.out.println(jedis.get("name"));

        String name = jedis.getSet("name", "liuxg.com");
        System.out.println("old:"+name+" new:"+jedis.get("name"));

        jedis.mset("key1","value1","key2","value2");
        System.out.println(jedis.mget("key2","key1"));
        System.out.println(jedis.get("key1"));

        jedis.setex("key3",1,"value3");
        System.out.println(jedis.get("key3"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("key3"+jedis.get("key3"));

        jedis.set("num1","1");
        System.out.println(jedis.get("num1"));
        jedis.incr("num1");
        System.out.println(jedis.get("num1"));
        jedis.decr("num1");
        System.out.println(jedis.get("num1"));

        jedis.del("name");
        jedis.close();
        RedisUtils.close();
    }

    public static void testHash(){
        Jedis jedis = RedisUtils.getJedis();
        Map<String,String> map1 = new HashMap<>();
        map1.put("key1","value1");
        map1.put("key2","value2");
        map1.put("key3","value3");
//        jedis.hset("map1",map1);
        jedis.hset("map1","key1","value1");
        jedis.hset("map1","key2","value2");
        jedis.hset("map1","key3","value3");
        jedis.hset("map1","key4","value4");
        Map<String, String> map11 = jedis.hgetAll("map1");
        map11.entrySet().forEach(entry->{
            System.out.println(entry.getKey()+" : "+entry.getValue());
        });
        jedis.hdel("map1","key1");

        jedis.hmset("map2",map1);
        Map<String, String> map12 = jedis.hgetAll("map2");
        map12.entrySet().forEach(entry->{
            System.out.println(entry.getKey()+" : "+entry.getValue());
        });

        System.out.println(jedis.hlen("map1"));//4
        System.out.println(jedis.hlen("map2"));//3
        Set<String> hkeys = jedis.hkeys("map1");
        hkeys.forEach(key->{
            System.out.println(key+" : "+jedis.hget("map1",key));
        });
        jedis.close();
    }


    public static void testList(){
        Jedis jedis = RedisUtils.getJedis();
        jedis.lpush("list1",new String[]{"list1-value1","list1-value2","list1-value3","list1-value4"});

        List<String> list = jedis.lrange("list1", 0, jedis.llen("list1")-1);
        list.forEach(string->{
            System.out.println(string);
        });
        System.out.println("出序列");
        long i=jedis.llen("list1");
        for (;;){
            System.out.println(jedis.lpop("list1"));
            i--;
            if (i==0){
                break;
            }
        }
        System.out.println(jedis.hlen("list1"));
    }

    public static void testSet(){

        Jedis jedis = RedisUtils.getJedis();
        jedis.sadd("set1",new String[]{"set1-value1","set2-value2","set3-value3","set4-value4","set5-value5"});

        System.out.println(jedis.scard("set1"));
        Set<String> set = jedis.smembers("set1");
        set.forEach(string->{
            System.out.println(string);
        });

        String set1 = jedis.spop("set1");
        System.out.println(set1);
        System.out.println("分界线");

        jedis.smembers("set1").forEach(string->{
            System.out.println(string);
        });
        jedis.close();
    }

    public static void testSortSet(){
        Jedis jedis = RedisUtils.getJedis();
        Map<String,Double> sortSet = new HashMap<>();
        sortSet.put("value1",1d);
        sortSet.put("value2",2d);
        sortSet.put("value3",3d);
        sortSet.put("value4",4d);
        sortSet.put("value5",5d);
        jedis.zadd("sortSet", sortSet);
        Set<String> zrange = jedis.zrange("sortSet", 0, jedis.zcard("sortSet"));
        zrange.forEach(strings->{
            System.out.println(strings);
        });

        System.out.println("分界线");
        Set<String> sortSet1 = jedis.zrangeByScore("sortSet", 1d, 3d);
        sortSet1.forEach(strings->{
            System.out.println(strings);
        });
        System.out.println("分界线");
        zrange = jedis.zrange("sortSet", 0, jedis.zcard("sortSet"));
        zrange.forEach(strings->{
            System.out.println(strings);
        });
        jedis.close();
    }

    public static void main(String[] args){
//        RedisTest.testString();
//        RedisTest.testHash();
//        RedisTest.testList();
//        RedisTest.testSet();
//        RedisTest.testSortSet();
        Publisher publisher = new Publisher(RedisUtils.getJedis(),"mychannel");
        Sublisher sublisher = new Sublisher(RedisUtils.getJedis(),"mychannel",new Subscriber());
        new Thread(sublisher).start();
        new Thread(publisher).start();
        while (publisher.isClose()){
            sublisher.close();
            RedisUtils.close();
        }
    }

}
