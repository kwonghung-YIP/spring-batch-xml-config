package org.hung.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ExampleApplication {

	public static void main(String[] args) {
		ApplicationContext appCtx = SpringApplication.run(ExampleApplication.class, args);
		
		JobLauncher joblauncher = appCtx.getBean(JobLauncher.class);
		
		Job job1 = appCtx.getBean("testing",Job.class);
		
		Job job2 = appCtx.getBean("testing2",Job.class);
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("srcFile", "EOD-JNJ.csv")
				.toJobParameters();
		
		try {
			joblauncher.run(job1, jobParameters);
			joblauncher.run(job2, jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
