package br.com.fiap.billing.api.person.integration

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

const val INPUT_PERSON_CHANNEL = "person-queue"

interface PersonChannel {

    @Input(INPUT_PERSON_CHANNEL)
    fun getSubscribableChannel(): SubscribableChannel?

}