package com.oppalove.spring.binder

import org.springframework.cloud.stream.binder.ConsumerProperties
import org.springframework.cloud.stream.binder.ProducerProperties
import org.springframework.cloud.stream.provisioning.ConsumerDestination
import org.springframework.cloud.stream.provisioning.ProducerDestination
import org.springframework.cloud.stream.provisioning.ProvisioningProvider

class EmptyMessageBinderProvisioner : ProvisioningProvider<ConsumerProperties, ProducerProperties> {

    override fun provisionProducerDestination(name: String, properties: ProducerProperties): ProducerDestination {
        return EmtpyMessageDestination(name)
    }

    override fun provisionConsumerDestination(
        name: String,
        group: String,
        properties: ConsumerProperties
    ): ConsumerDestination {
        return EmtpyMessageDestination(name)
    }

    data class EmtpyMessageDestination(private val name: String) : ProducerDestination, ConsumerDestination {
        override fun getName() = name
        override fun getNameForPartition(partition: Int): String {
            throw UnsupportedOperationException("Partitioning is not implemented for file messaging.");
        }
    }
}
