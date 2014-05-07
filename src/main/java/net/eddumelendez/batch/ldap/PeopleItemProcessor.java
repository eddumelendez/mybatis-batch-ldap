package net.eddumelendez.batch.ldap;

import org.springframework.batch.item.ItemProcessor;

public class PeopleItemProcessor implements ItemProcessor<Person, People> {

	@Override
	public People process(Person person) throws Exception {
		String lastname = person.getLastname();
		String firstname = person.getName();

		People people = new People(firstname, lastname);
		return people;
	}

}
