package net.eddumelendez.batch.ldap;

import net.eddumelendez.config.AbstractTest;

import org.junit.Test;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.annotation.DirtiesContext;

public class LdapTest extends AbstractTest {

	@Autowired
	private ItemReader<Person> reader;

	@Autowired
	private LdapTemplate ldapTemplate;

	@Test
	@DirtiesContext
	public void testReader() throws Exception {
		int count = 0;
		while (count < 10000) {
//			System.out.println(count);
			Person p = reader.read();
			if (p != null) {
				System.out.println(p.getName());
				System.out.println(p.getLastname());
			}
			count++;
		}
	}

}
