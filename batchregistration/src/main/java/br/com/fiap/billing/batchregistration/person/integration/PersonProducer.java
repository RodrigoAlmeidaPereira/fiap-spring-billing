package br.com.fiap.billing.batchregistration.person.integration;


import br.com.fiap.billing.batchregistration.person.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@AllArgsConstructor
@EnableBinding(PersonChannel.class)
public class PersonProducer {

    private PersonChannel channel;

    @TransactionalEventListener(Person.class)
    public void produce(Person event) {
        log.info("Producing " + event);
        channel.getMessageChannel().send(MessageBuilder.withPayload(event)
                .build());
    }
}
