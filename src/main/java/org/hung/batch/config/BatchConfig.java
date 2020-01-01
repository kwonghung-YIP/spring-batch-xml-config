package org.hung.batch.config;



import java.math.BigDecimal;

import javax.sql.DataSource;

import org.hung.batch.pojo.StockQuote;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ImportResource("simple-job-config.xml")
@EnableBatchProcessing
public class BatchConfig {

	@Bean
	@StepScope
	public Tasklet echoBean(@Value("#{stepExecution}") StepExecution stepExecution) {
		return (stepContribution, chunkContext) -> {
			log.info("Running step {}",stepExecution.getStepName());
			stepExecution.getExecutionContext().put("header", BigDecimal.valueOf(100l));
			return null;
		};
	}

	@Bean
	@StepScope
	public Tasklet echoBean2(@Value("#{stepExecution}") StepExecution stepExecution) {
		return (stepContribution, chunkContext) -> {
			log.info("Running step {}",stepExecution.getStepName());
			log.info("value in header:{}",stepExecution.getJobExecution().getExecutionContext().get("header"));
			return null;
		};
	}
	
	@Bean
	public JobParametersValidator jobParametersValidator() {
		DefaultJobParametersValidator validator = new DefaultJobParametersValidator();
		validator.setRequiredKeys(new String[] {"srcFile"});
		return validator;
	}
	
	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		return new TaskExecutorBuilder()
				.corePoolSize(10)
				.maxPoolSize(20)
				.build();
	}
	
	public ExecutionContextPromotionListener executionContextPromotionListener() {
		ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
		listener.setKeys(null);
		return listener;
	}
	
	@Configuration
	public class StockQuoteItemConfig extends AbstractItemConfig<StockQuote> {

		@Bean
		@StepScope
		public FlatFileItemReader<StockQuote> stockQuoteReader(@Value("#{jobParameters['srcFile']}") String srcFile) {
			Resource source = new ClassPathResource(srcFile);
			return this.flatFileItemReader(source);
		}

		@Bean 
		public ItemProcessor<StockQuote,StockQuote> dummyProcessor() {
			return (item) -> {
				return null;
			};
		}
		
		@Bean
		public BeanValidatingItemProcessor<StockQuote> stockQuoteBeanValidator() {
			return super.beanValidatingItemProcessor();
		}
		
		@Bean
		@StepScope
		public ItemWriter<StockQuote> stockQuoteWriter(DataSource dataSource) {
			return super.jdbcBatchItemWriter(dataSource);
		}
		
	}
	
}
