package br.com.fiap.billing.batchregistration.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    public static final int NAME_START_INDEX = 1;
    public static final int NAME_FINAL_INDEX = 41;

    public static final int DOC_START_INDEX = 42;
    public static final int DOC_FINAL_INDEX = 49;

    public static final int ENROLLMENT_START_INDEX = 50;
    public static final int ENROLLMENT_FINAL_INDEX = 55;

    private String name;
    private String doc;
    private String enrollment;

}
