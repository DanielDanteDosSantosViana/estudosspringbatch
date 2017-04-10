package br.com.estudosspringbatch.component;

import org.springframework.batch.item.ItemProcessor;

import br.com.estudosspringbatch.domain.Person;

public class PersonProcessor implements ItemProcessor<Person, Person> {

	@Override
	public Person process(final Person person) throws Exception {
		 final String firstName = person.getFirstName().toUpperCase();
	        final String lastName = person.getLastName().toUpperCase();
	        final Person transformedPerson = new Person(firstName, lastName);
	        return transformedPerson;
	}

}
