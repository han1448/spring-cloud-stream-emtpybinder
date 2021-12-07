package com.oppalove.spring.binder

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EmptyMessageBinderConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun emptyMessageBinderProvisioner(): EmptyMessageBinderProvisioner? {
        return EmptyMessageBinderProvisioner()
    }

    @Bean
    @ConditionalOnMissingBean
    fun emptyMessageBinder(emptyMessageBinderProvisioner: EmptyMessageBinderProvisioner): EmptyMessageBinder? {
        return EmptyMessageBinder(null, emptyMessageBinderProvisioner)
    }
}
