package org.hung.batch.listener;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StepListener {
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		log.info("@BeforeStep");
	}
	
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		log.info("@AfterStep");
	}
	
}
