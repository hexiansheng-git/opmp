package com.hhwy.utils.redissonLock;

import com.hhwy.common.core.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedissonManager {
    @Value("${spring.redis.host}")
    String redisUrl;
    @Value("${spring.redis.port}")
    String redisPort;
    //    @Value("${spring.redis.password}")
    private static String password;

    private Config config = new Config();
    private Redisson redisson = null;

    public Redisson getRedisson() {
        if (redisson == null) {
            synchronized (RedissonManager.class) {
                if (redisson == null) {
                    Environment environment = SpringUtils.getBean(Environment.class);
                    password = environment.getProperty("spring.redis.password");
                    if (StringUtils.isBlank(password)) {
                        config.useSingleServer()
                                .setAddress(String.format("redis://%s:%s", redisUrl, redisPort))
                                .setDatabase(0);
                    } else {
                        config.useSingleServer()
                                .setAddress(String.format("redis://%s:%s", redisUrl, redisPort)).setPassword(password)
                                .setPassword(password)
                                .setDatabase(0);
                    }
                    //看门狗检查锁的续期问题，默认是30秒，可自定设置
                    //config.setLockWatchdogTimeout(15 * 1000l);
                    redisson = (Redisson) Redisson.create(config);
                }
            }
        }
        return redisson;
    }
}
