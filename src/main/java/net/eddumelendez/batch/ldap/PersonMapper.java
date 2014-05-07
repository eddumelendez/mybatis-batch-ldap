package net.eddumelendez.batch.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

public class PersonMapper implements AttributesMapper<Person> {

	@Override
	public Person mapFromAttributes(Attributes attributes)
			throws NamingException {
		Person person = new Person();
		person.setName(attributes.get("sn") != null ? (String) attributes
				.get("sn").get() : "");
		person.setLastname(attributes.get("cn") != null ? (String) attributes
				.get("cn").get() : "");
		return person;
	}

}
