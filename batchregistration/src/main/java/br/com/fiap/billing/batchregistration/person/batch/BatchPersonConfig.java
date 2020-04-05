package br.com.fiap.billing.batchregistration.person.batch;

import br.com.fiap.billing.batchregistration.person.Person;
import br.com.fiap.billing.batchregistration.person.integration.PersonProducer;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BatchPersonConfig {

    final private JobBuilderFactory jobBuilderFactory;
    final private StepBuilderFactory stepBuilderFactory;
    final private PersonProducer personProducer;

    @Bean
    public Job personJob() {
        return jobBuilderFactory.get("personJob")
                .incrementer(new RunIdIncrementer()).listener(personListener())
                .flow(personStep1())
                .end()
                .build();
    }

    @Bean
    public Step personStep1() {

        return stepBuilderFactory.get("personStep1")
                .<Person, Person> chunk(1)
                .reader(new PersonItemReader())
                .processor(new PersonProcessor())
                .writer(new PersonWriter(personProducer))
                .build();
    }

    @Bean
    public JobExecutionListener personListener() {
        return new PersonJobCompletionListener();
    }

}
