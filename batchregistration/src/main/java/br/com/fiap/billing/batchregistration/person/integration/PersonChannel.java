package br.com.fiap.billing.batchregistration.person.integration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PersonChannel {

    String OUTPUT = "person-exchange";

    @Output(OUTPUT)
    MessageChannel getMessageChannel();

}

