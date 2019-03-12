package cn.com.java.redis.core;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

public class RedisKeyGenerator implements KeyGenerator {
    private static final Logger logger = LoggerFactory.getLogger(RedisKeyGenerator.class);


    private static String NO_PARAM_KEY = "no_params";
    @Override
    public Object generate(Object target, Method method, Object... params) {

        String sp = ":";
        StringBuilder strBuilder = new StringBuilder(30);
        // 类名
/*      strBuilder.append(target.getClass().getSimpleName());
        strBuilder.append(sp);*/
        // 方法名
        strBuilder.append(method.getName());
        strBuilder.append(sp);
        if (params.length > 0) {
            int i =1;
            // 参数值
            for (Object object : params) {
                if (isSimpleValueType(object.getClass())) {
                    strBuilder.append(object);
                    if(i++ < params.length) {
                        strBuilder.append(sp);
                    }
                } else {
                    strBuilder.append(JSONObject.toJSON(object).toString());
                    if(i++ < params.length) {
                        strBuilder.append(sp);
                    }
                }
            }
        } else {
            strBuilder.append(NO_PARAM_KEY);
        }
        logger.info("=============newKey:{}",strBuilder.toString());
        return strBuilder.toString();

    }

    public static boolean isSimpleValueType(Class<?> clazz) {
        return (ClassUtils.isPrimitiveOrWrapper(clazz) || clazz.isEnum() ||
                CharSequence.class.isAssignableFrom(clazz) ||
                Number.class.isAssignableFrom(clazz) ||
                Date.class.isAssignableFrom(clazz) ||
                URI.class == clazz || URL.class == clazz ||
                Locale.class == clazz || Class.class == clazz);
    }

}
