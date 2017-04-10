package br.com.estudosspringbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.estudosspringbatch.component.PersonFileProcessor;
import br.com.estudosspringbatch.component.PersonFileReader;
import br.com.estudosspringbatch.component.PersonFileWriter;
import br.com.estudosspringbatch.domain.Person;
import br.com.estudosspringbatch.listener.JobCompletionNotificationListener;


@Configuration
public class JobDescriptor {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private PersonFileReader personFileReader;
    
    @Autowired
    private PersonFileProcessor personFileProcessor;    
    
    @Autowired
    private PersonFileWriter personFileWriter; 
    
    @Autowired
    private JobCompletionNotificationListener listener;

    
    @Bean
    public Job processFileJob() {
        return jobBuilderFactory.get("processFileJob")
        		.incrementer(new RunIdIncrementer())
        		.listener(listener)
        		.preventRestart()
        		
        		.flow(importPersonToDataBase())
        		.end()
        		.build();
    }
    
    @Bean
    public Step importPersonToDataBase(){
        return stepBuilderFactory.get("importPersonToDataBase")
        .<Person,Person> chunk(10)
        .reader(personFileReader)		
    	.processor(personFileProcessor)
    	.writer(personFileWriter)
    	.build();
    }
}
