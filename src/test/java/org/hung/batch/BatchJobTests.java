package org.hung.batch;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBatchTest
public class BatchJobTests {

	@Autowired
	private JobLauncherTestUtils batchUtils;
	
	@Test
	void testingJobTest() throws Exception {
		JobLauncher joblauncher = batchUtils.getJobLauncher();
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("srcFile", Paths.get("C:","Temp").toString())
				.toJobParameters();
		
		JobExecution jobExecution = joblauncher.run(batchUtils.getJob(), jobParameters);
	}
}
