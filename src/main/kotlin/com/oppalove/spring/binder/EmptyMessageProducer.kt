package com.oppalove.spring.binder

import org.springframework.cloud.stream.provisioning.ConsumerDestination
import org.springframework.integration.endpoint.MessageProducerSupport

class EmptyMessageProducer(
    private val destination: ConsumerDestination
) : MessageProducerSupport() {

    override fun doStart() {
        println("EmptyMessageProducer started. (${destination.name})")
    }
}
