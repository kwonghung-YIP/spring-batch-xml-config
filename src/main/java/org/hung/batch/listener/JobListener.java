package org.hung.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobListener {
	
	@BeforeJob
	public void beforeJob(JobExecution jobExecution) {
		log.info("@BeforeJob");
	}
	
	@AfterJob
	public void afterJob(JobExecution jobExecution) {
		log.info("@AfterJob");
	}
	

}
