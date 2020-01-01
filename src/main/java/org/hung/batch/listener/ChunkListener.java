package org.hung.batch.listener;

import java.util.List;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.core.scope.context.ChunkContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChunkListener<T,S> {
	
	@BeforeChunk
	public void beforeChunk(ChunkContext chunkContext) {
		log.info("@BeforeChunk");
	}
	
	@AfterChunk
	public void afterChunk(ChunkContext chunkContext) {
		log.info("@AfterChunk");
	}
	
	@AfterChunkError
	public void afterChunkError(ChunkContext chunkContext) {
		log.info("@AfterChunkError");
	}	
	
	@AfterRead
	public void afterRead(T item) {
		log.debug("@AfterRead");
	}

	@OnReadError
	public void onReadError(Exception e) {
		log.error("@OnReadError");
	}
	
	@OnSkipInRead
	public void onSkipInRead(Throwable t) {
		log.error("@OnSkipInRead",t);
	}
	
	@OnProcessError
	public void onProcessError(T item, Exception e) {
		log.error("@OnProcessError");
	}
	
	@AfterProcess
	public void afterProcess(T item, S result) {
		log.info("@AfterProcess");
	}	
	
	@OnSkipInProcess
	public void onSkipInProcess(T item,Throwable t) {
		log.error("@onSkipInProcess",t);
	}
	
	@AfterWrite
	public void afterWrite(List<S> items) {
		log.info("@AfterWrite");
	}
	
	@OnSkipInWrite
	public void onSkipInWrite(S result,Throwable t) {
		log.error("@@OnSkipInWrite",t);
	}	
}
