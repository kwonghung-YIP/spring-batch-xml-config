package org.hung.batch.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;

public abstract class AbstractItemConfig<T> {

	public FieldSetMapper<T> fieldsetMapper() {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().
				appendPattern("uuuu-MM-dd").
				toFormatter();
		
		DefaultConversionService conversionService = new DefaultConversionService();
		conversionService.addConverter(String.class,LocalDate.class,(Converter<String, LocalDate>)(s)->{
			return LocalDate.parse(s, formatter);
		});
		
		BeanWrapperFieldSetMapper<T> beanwrapper = new BeanWrapperFieldSetMapper<T>();
		beanwrapper.setTargetType((Class<T>)GenericTypeResolver.resolveTypeArgument(getClass(),AbstractItemConfig.class));
		beanwrapper.setConversionService(conversionService);
		return beanwrapper;
	}
	
	public FlatFileItemReader<T> flatFileItemReader(Resource source) {
		
		return new FlatFileItemReaderBuilder<T>()
				.name("stockquote-file-reader")
				.fieldSetMapper(fieldsetMapper())
				.resource(source)
				.delimited()
				.delimiter(",")
				.includedFields(0,1,2,3,4,5,6,7)
				.names("date","open","high","low","close","volume","dividend","split")
				.linesToSkip(1)
				.build();
	}
	
	public BeanValidatingItemProcessor<T> beanValidatingItemProcessor() {
		BeanValidatingItemProcessor<T> validator = new BeanValidatingItemProcessor<T>();
		return validator;
	}
	
	public ItemWriter<T> jdbcBatchItemWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<T>()
				.dataSource(dataSource)
				.beanMapped()
				.sql("insert into stock_quote values(:date,:open,:high,:low,:close, :volume,:dividend,:split)")
				.build();
	}
}
