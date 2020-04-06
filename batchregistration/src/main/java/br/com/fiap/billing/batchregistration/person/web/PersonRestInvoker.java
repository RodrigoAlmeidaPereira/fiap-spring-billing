package br.com.fiap.billing.batchregistration.person.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Api("API Job Pessoas")
public class PersonRestInvoker {

    final private JobLauncher jobLauncher;
    final private Job personJob;

    @PostMapping("jobs/persons/invoke")
    @ApiOperation(value = "Inicia o Job de cadastro de pessoas.")
    public String handle() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(personJob, jobParameters);

        return "Person Batch job has been invoked";
    }
}
