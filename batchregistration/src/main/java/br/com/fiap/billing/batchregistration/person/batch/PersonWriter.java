package br.com.fiap.billing.batchregistration.person.batch;

import br.com.fiap.billing.batchregistration.person.Person;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class PersonWriter implements ItemWriter<Person> {

    @Override
    public void write(List<? extends Person> messages) throws Exception {
        for (Person msg : messages) {
            System.out.println("Writing the data " + msg);
        }
    }

}
