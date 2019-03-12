package cn.com.java.redis.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;

public class RedisCacheErrorHandler extends SimpleCacheErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheErrorHandler.class);

    private static final Logger fatallogger = LoggerFactory.getLogger("fatal");

    private static final int NUM = 3;

//	private final Cache delegate;

/*	public RedisCacheErrorHandler(Cache redisCache) {
		this.delegate = redisCache;
	}*/

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
//		logger.info("get失败");
        logger.error("cache get error, cacheName:{}, key:{}, msg:", cache.getName(), key, exception);
//		throw exception;
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
//		logger.info("put失败");
        logger.error("cache put error, cacheName:{}, key:{}, msg:", cache.getName(), key, exception);
//		throw exception;
    }

    //注解删除redis缓存失败时，进行手动删除，成功直接退出，失败重试NUM次，同时打印fatal日志
    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        boolean checkEvit = false;
        for(int i=1; i <= NUM; i++) {
            try{
                cache.evict(key);
                checkEvit = true;
            }catch (RuntimeException e) {
                fatallogger.error("cache evict error:{}, cacheName:{}, key:{}, msg:", i, cache.getName(), key, exception);
            }
            if(checkEvit) {
                break;
            }
        }
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        logger.error("cache clear error, cacheName:{}, msg:", cache.getName(), exception);
    }
}
