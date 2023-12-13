package com.hhwy.feign.factory;

import com.hhwy.feign.service.ILogServiceApi;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class LogServiceFallBackFactory implements FallbackFactory<ILogServiceApi> {
    @Override
    public ILogServiceApi create(Throwable cause) {
        return null;
    }
}
