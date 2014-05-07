package net.eddumelendez.batch.ldap;

import net.eddumelendez.config.AbstractTest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

public class JobExecutionTest extends AbstractTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	@DirtiesContext
	public void testReader() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

}
