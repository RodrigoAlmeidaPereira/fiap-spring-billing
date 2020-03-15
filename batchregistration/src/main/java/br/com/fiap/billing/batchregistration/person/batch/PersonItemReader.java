package br.com.fiap.billing.batchregistration.person.batch;

import br.com.fiap.billing.batchregistration.person.Person;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


@Component
@StepScope
class PersonItemReader extends FlatFileItemReader<Person> {

    PersonItemReader() {
        super();

        setRecordSeparatorPolicy(new BlankLineRecordSeparatorPolicy());
        super.setResource(new ClassPathResource("data/lista_alunos.txt"));

        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);

        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(fixedLengthTokenizer());
        super.setLineMapper(lineMapper);
    }

    private FixedLengthTokenizer fixedLengthTokenizer() {
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setNames("name", "doc", "enrollment");
        tokenizer.setColumns(
                new Range(Person.NAME_START_INDEX, Person.NAME_FINAL_INDEX),
                new Range(Person.DOC_START_INDEX, Person.DOC_FINAL_INDEX),
                new Range(Person.ENROLLMENT_START_INDEX, Person.ENROLLMENT_FINAL_INDEX));
        return tokenizer;
    }

    static class BlankLineRecordSeparatorPolicy extends DefaultRecordSeparatorPolicy {

        @Override
        public String postProcess(final String record) {
            if (record == null || record.trim().length() == 0 || record.startsWith("0")) {
                return super.postProcess(" ".repeat(Person.ENROLLMENT_FINAL_INDEX));
            }
            return super.postProcess(record);
        }

    }
}
