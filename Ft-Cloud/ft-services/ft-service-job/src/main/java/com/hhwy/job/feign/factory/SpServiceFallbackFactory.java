package com.hhwy.job.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SpServiceFallbackFactory implements FallbackFactory<SpServiceFallbackFactory> {
    @Override
    public SpServiceFallbackFactory create(Throwable cause) {
        return new SpServiceFallbackFactory(){

        };
    }
}
