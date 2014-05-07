package net.eddumelendez.config;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;

import net.eddumelendez.batch.ldap.LdapItemReader;
import net.eddumelendez.batch.ldap.People;
import net.eddumelendez.batch.ldap.PeopleItemProcessor;
import net.eddumelendez.batch.ldap.Person;
import net.eddumelendez.batch.ldap.PersonMapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Bean
	public ContextSource contextSource() {
		LdapContextSource context = new LdapContextSource();
		Map<String, Object> baseEnvironmentProperties = new HashMap<String, Object>();
		baseEnvironmentProperties.put(Context.SECURITY_PROTOCOL, "none");
		baseEnvironmentProperties
				.put(Context.SECURITY_AUTHENTICATION, "simple");
		context.setAnonymousReadOnly(false);
		context.setPooled(true);
		context.setUrl("ldap://127.0.0.1:18880");
		context.setUserDn("uid=admin,ou=system");
		context.setPassword("secret");
		context.setBase("dc=jayway,dc=se");
		context.setBaseEnvironmentProperties(baseEnvironmentProperties);
		context.afterPropertiesSet();
		return context;
	}

	@Bean
	public LdapTemplate ldapTemplate(ContextSource contextSource) {
		LdapTemplate ldapTemplate = new LdapTemplate();
		ldapTemplate.setContextSource(contextSource);
		return ldapTemplate;
	}

	@Bean
	public ItemReader<Person> reader(LdapTemplate ldapTemplate)
			throws Exception {
		LdapItemReader<Person> reader = new LdapItemReader<Person>(
				ldapTemplate, new PersonMapper(),
				"c=Sweden",
				"(&(objectClass=person))");
		reader.afterPropertiesSet();
		return reader;
	}

	@Bean
	public ItemWriter<People> writer(SqlSessionFactory sqlSessionFactory) {
		MyBatisBatchItemWriter<People> writer = new MyBatisBatchItemWriter<People>();
		writer.setSqlSessionFactory(sqlSessionFactory);
		writer.setStatementId("insertPeople");
		return writer;
	}

	@Bean
	public Job importUserJob(JobBuilderFactory jobs, Step s1) {
		return jobs.get("importUserJob").incrementer(new RunIdIncrementer())
				.flow(s1).end().build();
	}

	@Bean
    public ItemProcessor<Person, People> processor() {
        return new PeopleItemProcessor();
    }

	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory,
			ItemReader<Person> reader, ItemWriter<People> writer,
			ItemProcessor<Person, People> processor) {
		return stepBuilderFactory.get("step1").<Person, People> chunk(10)
				.reader(reader).processor(processor).writer(writer).build();
	}

}
