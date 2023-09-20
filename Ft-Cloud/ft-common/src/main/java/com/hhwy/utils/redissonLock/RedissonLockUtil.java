package com.hhwy.utils.redissonLock;

import com.hhwy.common.core.utils.SpringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public class RedissonLockUtil {

    private static Redisson redisson;
    static {
        RedissonManager redissonManager = SpringUtils.getBean(RedissonManager.class);
        redisson = redissonManager.getRedisson();
    }
    private static final String LOCK_ = "redisson:lock:";

    //使用demo
    public static void main(String[] args) {
        try{
            if(RedissonLockUtil.lock("test")){
                
                // 代码运行了33秒
                
                //处理业务

            }
        }finally {
            RedissonLockUtil.unlock("test");
        }
    }

    //加锁
    public static boolean lock(String lockname){
        String key = LOCK_ + lockname;
        //获取锁对象
        RLock rLock = redisson.getLock(key);
        //加锁，并设置锁超时时间 30秒
        rLock.lock(30, TimeUnit.SECONDS);
        return true;
    }
    //解锁
    public static void unlock(String lockname){
        String key = LOCK_ + lockname;
        //获取锁对象
        RLock rLock = redisson.getLock(key);
        //解锁
        rLock.unlock();
    }

}
