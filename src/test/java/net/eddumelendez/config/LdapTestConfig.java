package net.eddumelendez.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.test.EmbeddedLdapServerFactoryBean;
import org.springframework.ldap.test.LdifPopulator;

@Configuration
public class LdapTestConfig {

	@Bean
	@DependsOn("embeddedLdapServer")
	public LdifPopulator ldifPopulator(ContextSource contextSource) {
		LdifPopulator ldifPopulator = new LdifPopulator();
		ldifPopulator.setContextSource(contextSource);
		ldifPopulator.setResource(new ClassPathResource("setup_data.ldif"));
		ldifPopulator.setBase("dc=jayway,dc=se");
		ldifPopulator.setClean(true);
		ldifPopulator.setDefaultBase("dc=jayway,dc=se");
		return ldifPopulator;
	}

	@Bean(name = "embeddedLdapServer")
	public EmbeddedLdapServerFactoryBean embeddedLdapServerFactoryBean() {
		EmbeddedLdapServerFactoryBean embeddedLdapServerFactoryBean = new EmbeddedLdapServerFactoryBean();
		embeddedLdapServerFactoryBean.setPartitionName("example");
		embeddedLdapServerFactoryBean.setPartitionSuffix("dc=jayway,dc=se");
		embeddedLdapServerFactoryBean.setPort(18880);
		return embeddedLdapServerFactoryBean;
	}

}
