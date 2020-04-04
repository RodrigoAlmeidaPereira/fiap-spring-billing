package br.com.fiap.billing.api.person.integration

import br.com.fiap.billing.api.person.Person
import br.com.fiap.billing.api.person.PersonService
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.transaction.annotation.Transactional
import java.util.*

@EnableBinding(PersonChannel::class)
@Transactional(rollbackFor = [Exception::class])
class HarvestCreatedConsumer(private val service: PersonService) {

    @StreamListener(value = INPUT_PERSON_CHANNEL)
    private fun onReceiveMessage(@Payload event: PersonEvent) {
        var entity = service.findByDoc(event.doc)
        if (Objects.nonNull(entity)) {
            entity = entity!!
            entity.name = event.name
            entity.enrollment = event.enrollment
            service.update(entity.id, entity)
        } else {
            entity = Person(name = event.name, doc = event.doc, enrollment = event.enrollment)
            service.create(person = entity)
        }
    }
}