package com.hhwy.utils;

import java.util.concurrent.*;

/**
 * @ClassName : ThreadPoolUtil
 * @Description : 线程池工具类
 * @Author : zxb
 * @Date :  14:48
 * @Version : V1.0
 **/
public class ThreadPoolUtil {

    public static ThreadPoolExecutor threadPool;

    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool != null) {
            return threadPool;
        } else {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(5, 16, 60, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(32), new ThreadPoolExecutor.CallerRunsPolicy());
                }
                return threadPool;
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            getThreadPool().execute(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(1111);
            });
        }
        System.out.println("主线程。。。" + System.currentTimeMillis());
    }


    /**
     * 无返回值直接执行
     * @param runnable
     */
    public  static void execute(Runnable runnable){
        getThreadPool().execute(runnable);
    }

    /**
     * 返回值直接执行
     * @param callable
     */
    public  static <T> Future<T> submit(Callable<T> callable){
        return   getThreadPool().submit(callable);
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        getThreadPool().shutdown();
    }

}
