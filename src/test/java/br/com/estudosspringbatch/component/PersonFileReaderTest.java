package br.com.estudosspringbatch.component;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@ContextConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,StepScopeTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonFileReaderTest {

	@Autowired
	private PersonFileReader personFileReader;

	public StepExecution geStepExecution(){
		 StepExecution execution = MetaDataInstanceFactory.createStepExecution();
		 execution.getExecutionContext().put("sample-data.csv", "jose,hon,jaja");
		 return execution;
	}

	@Test
	public void test() throws UnexpectedInputException, ParseException, Exception{
		assertNotNull(personFileReader.read());
	}
}
