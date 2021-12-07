package com.oppalove.spring.binder

import org.springframework.cloud.stream.binder.AbstractMessageChannelBinder
import org.springframework.cloud.stream.binder.ConsumerProperties
import org.springframework.cloud.stream.binder.ProducerProperties
import org.springframework.cloud.stream.provisioning.ConsumerDestination
import org.springframework.cloud.stream.provisioning.ProducerDestination
import org.springframework.integration.core.MessageProducer
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler


open class EmptyMessageBinder(
    headersToEmbed: Array<String>?,
    provisioningProvider: EmptyMessageBinderProvisioner
) : AbstractMessageChannelBinder<ConsumerProperties, ProducerProperties, EmptyMessageBinderProvisioner>(
    headersToEmbed,
    provisioningProvider
) {


    @Throws(Exception::class)
    override fun createProducerMessageHandler(
        destination: ProducerDestination,
        producerProperties: ProducerProperties?,
        errorChannel: MessageChannel?
    ): MessageHandler? {
        return MessageHandler { message ->
            // Simply ignore messages
            println("${message.payload}")
        }
    }

    @Throws(Exception::class)
    override fun createConsumerEndpoint(
        destination: ConsumerDestination,
        group: String?,
        properties: ConsumerProperties?
    ): MessageProducer? {
        return EmptyMessageProducer(destination)
    }
}
