package br.com.fiap.billing.batchregistration.person.batch;

import br.com.fiap.billing.batchregistration.person.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PersonProcessorTest {

    private Person person;

    private PersonProcessor processor = new PersonProcessor();

    @Before
    public void setUp() {
        this.person = Person.builder()
                .name("Rodrigo")
                .doc("12345")
                .enrollment("6789")
                .build();
    }

    @Test
    public void shouldProcessWithSuccess() throws Exception {
        Person processed = processor.process(person);

        assertNotNull(processed);
        assertEquals(processed.getName(), person.getName());
    }

    @Test
    public void shouldProcessWhenNameIsNullWithSuccess() throws Exception {
        Person processed = processor.process(person.toBuilder()
                .name("")
                .build());

        assertNull(processed);
    }

}