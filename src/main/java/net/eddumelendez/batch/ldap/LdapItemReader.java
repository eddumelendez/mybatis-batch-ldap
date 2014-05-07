package net.eddumelendez.batch.ldap;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.util.Assert;

public class LdapItemReader<T> implements ItemReader<T>, InitializingBean {

	List<T> items;

	private LdapTemplate ldapTemplate;

	private AttributesMapper<T> attributesMapper;
	
	private String base;
	
	private String filter;

	public LdapItemReader(LdapTemplate ldapTemplate,
			AttributesMapper<T> attributes, String base, String filter) {
		this.ldapTemplate = ldapTemplate;
		this.attributesMapper = attributes;
		this.base = base;
		this.filter = filter;
		
		this.items = getAllPersonNames();
	}

	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		if (!items.isEmpty()) {
			return items.remove(0);
		}
		return null;
	}

	private List<T> getAllPersonNames() {
		return ldapTemplate.search(base, filter, attributesMapper);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.ldapTemplate, "The 'ldapTemplate' is required.");
		Assert.notNull(this.attributesMapper,
				"The 'attributesMapper' is required.");
		Assert.notNull(this.base, "The 'base' is required.");
		Assert.notNull(this.filter, "The 'filter' is required.");
	}
}
