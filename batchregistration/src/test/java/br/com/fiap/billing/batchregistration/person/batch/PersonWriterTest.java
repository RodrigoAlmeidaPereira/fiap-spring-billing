package br.com.fiap.billing.batchregistration.person.batch;

import br.com.fiap.billing.batchregistration.person.Person;
import br.com.fiap.billing.batchregistration.person.integration.PersonProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PersonWriterTest {

    private Person person;

    @Mock
    protected PersonProducer personProducer;

    private PersonWriter writer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        writer = new PersonWriter(this.personProducer);

        this.person = Person.builder()
                .name("Rodrigo")
                .doc("12345")
                .enrollment("6789")
                .build();

        doNothing().when(this.personProducer).produce(person);
    }

    @Test
    public void shouldWriteWithSuccess() throws Exception {
        writer.write(List.of(person));

    }

}