package com.hhwy.utils.multithreading.threadpoolconfig.props;

import lombok.Data;
import org.springframework.context.annotation.Configuration;


/**
 * @author Administrator
 */
@Configuration
//@ConfigurationProperties(prefix = "async.executor.thread.default")
@Data
public class DefaultThreadPoolProp {

    /**
     * 核心线程数
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maxPoolSize;
    /**
     * 超时时间
     */
    private int keepAliveSeconds;
    /**
     * 队列大小
     */
    private int queueCapacity;
    /**
     * 线程前缀名称
     */
    private String prefix;


    public DefaultThreadPoolProp() {
        // 默认核心数量 + 1
        this.corePoolSize = Runtime.getRuntime().availableProcessors() + 1;
        // 默认最大数量等于核心数量
        this.maxPoolSize = this.corePoolSize;
        // 超时时间60s
        this.keepAliveSeconds = 60;
        // 队列大小
        this.queueCapacity = 9999;
        // 线程池前缀
        this.prefix = "FT-DEFAULT-THREAD";

    }


}
