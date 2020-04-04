package br.com.fiap.billing.batchregistration.person.batch;

import br.com.fiap.billing.batchregistration.person.Person;
import br.com.fiap.billing.batchregistration.person.integration.PersonProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class PersonWriter implements ItemWriter<Person> {

    private PersonProducer producer;
    
    @Override
    public void write(List<? extends Person> persons) throws Exception {
        for (Person person : persons) {
            log.info("Writing " + person);
            this.producer.produce(person);
        }
    }

}
