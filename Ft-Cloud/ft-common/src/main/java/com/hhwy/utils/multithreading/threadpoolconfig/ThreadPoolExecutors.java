package com.hhwy.utils.multithreading.threadpoolconfig;

import com.hhwy.utils.multithreading.threadpoolconfig.props.DefaultThreadPoolProp;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 * 目前只有一个默认的，若需要其他的可以添加其他的Prop配置类以及Bean。
 *
 * @author Administrator
 */
@Configuration
@EnableAsync
@Slf4j
@Data
public class ThreadPoolExecutors {


    /**
     * 默认配置
     */
    @Resource
    private DefaultThreadPoolProp defaultThreadPoolProp;

    /**
     * 系统默认线程池配置 七大参数
     * <p>
     * int corePoolSize - 核心线程数 (即队列没有满时，线程的最大并发数量)
     * int maximumPoolSize - 最大线程树 (即队列满时，能够达到的线程最大并发数量)
     * long keepAliveTime - 非核心的线程可以空闲的最大时间 (默认情况下核心线程空闲到此时间不会被回收，但是当 'threadPoolExecutor.allowCoreThreadTimeOut(true);' 时，核心线程也会被回收。)
     * TimeUnit unit -  非核心的线程可以空闲的最大时间的单位，在这里我们默认用秒
     * BlockingQueue<Runnable> workQueue -  线程池使用的阻塞队列，在这里我们默认用LinkedBlockingQueue
     * ThreadFactory threadFactory - 线程池使用的线程工厂，在这里我们默认用Executors.defaultThreadFactory()
     * RejectedExecutionHandler handler - 拒绝策略，分为以下四种
     * ① new ThreadPoolExecutor.AbortPolicy();         达到最大线程数后，再来任务，不处理，直接抛出异常。
     * ② new ThreadPoolExecutor.CallerRunsPolicy();    达到最大线程数后，不再新增线程处理任务，任务回归原来线程。
     * ③ new ThreadPoolExecutor.DiscardPolicy();       达到最大线程数后，丢掉任务，不会抛出异常。
     * ④ new ThreadPoolExecutor.DiscardOldestPolicy()  达到最大线程数后，尝试去和最早的竞争，也不会抛出异常。
     *
     * @return Executor
     */
    @Bean(name = "defaultThreadPoolExecutor")
    public Executor defaultThreadPoolExecutor() {
        log.warn("start asyncServiceExecutor");
        //在这里修改
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(defaultThreadPoolProp.getCorePoolSize());
        //配置最大线程数
        executor.setMaxPoolSize(defaultThreadPoolProp.getMaxPoolSize());
        // 超时时间 单位为秒
        executor.setKeepAliveSeconds(defaultThreadPoolProp.getKeepAliveSeconds());
        //配置队列大小 默认用LinkedBlockingQueue
        executor.setQueueCapacity(defaultThreadPoolProp.getQueueCapacity());
        // 线程创建工厂
        executor.setThreadFactory(Executors.defaultThreadFactory());
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(defaultThreadPoolProp.getPrefix());
        // 拒绝策略 任务回归原来线程
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }


}
