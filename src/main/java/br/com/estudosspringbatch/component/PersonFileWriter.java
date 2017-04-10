package br.com.estudosspringbatch.component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.estudosspringbatch.domain.Person;

@Component
@StepScope
public class PersonFileWriter extends JdbcBatchItemWriter<Person> {
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init(){
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        super.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
        super.setDataSource(dataSource);
	}
}
