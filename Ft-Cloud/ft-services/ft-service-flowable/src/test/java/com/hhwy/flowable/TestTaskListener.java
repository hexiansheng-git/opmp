package com.hhwy.flowable;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

public class TestTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.err.println("监听器触发了：" + delegateTask.getName());

    }
}
