package br.com.fiap.billing.batchregistration.person.batch;

import br.com.fiap.billing.batchregistration.person.Person;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

public class PersonProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) throws Exception {
        if (!StringUtils.hasText(person.getName())) {
            return null;
        }
        return person;
    }

}
