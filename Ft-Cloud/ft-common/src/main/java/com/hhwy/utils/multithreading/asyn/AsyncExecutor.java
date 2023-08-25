package com.hhwy.utils.multithreading.asyn;

import com.hhwy.utils.multithreading.asyn.functions.DefaultFunction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步执行器，可以使用自定义的线程池。
 * 默认只有一个无参无返回值的方法，若需要此方法不能满足，可自定义函数。
 *
 * @author Administrator
 */
@Component
public class AsyncExecutor {
    /**
     * 异步方法 无参数
     */
    @Async("defaultThreadPoolExecutor")
    public void defaultAsyncExecute(DefaultFunction function) {
        function.run();
    }

}
