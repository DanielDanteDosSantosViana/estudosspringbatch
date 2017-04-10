package br.com.estudosspringbatch.component;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.estudosspringbatch.domain.Person;
import br.com.estudosspringbatch.service.PersonService;

@Component
@StepScope
public class OpenPersonFileTasklet implements Tasklet {


    @Value("#{jobParameters['jobId']}")
    private String jobId;

    @Value("#{jobParameters['tenantName']}")
    private String tenantName;

    @Value("#{jobParameters['inputFile']}")
    private String inputFile;
    
    private PersonService personService;
    private JobData jobData;

    @Autowired
    public OpenPersonFileTasklet(JobData jobData, PersonService personService){
    	this.jobData = jobData;
		this.personService = personService;
    }

    @PostConstruct
    private void init(){
        jobData.put(jobId, "tenantName", tenantName);
        jobData.put(jobId, "inputFile", inputFile);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {
            Person person = personService.openFile(tenantName, inputFile);
            jobData.put(jobId, "person", person);
        } catch (Exception e) {
            stepContribution.setExitStatus(new ExitStatus(e.getClass().getName()));
        } finally {
            jobData.put(jobId, "exitStatus", stepContribution.getExitStatus());
        }
        return RepeatStatus.FINISHED;
    }
}

