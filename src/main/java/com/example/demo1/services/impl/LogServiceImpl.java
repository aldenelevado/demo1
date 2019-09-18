package com.example.demo1.services.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo1.bean.VariablesBean;
import com.example.demo1.domain.Log;
import com.example.demo1.repository.LogRepository;
import com.example.demo1.services.LogService;
import com.example.demo1.util.Consumer;
import com.example.demo1.util.Producer;

@Service
@Transactional
public class LogServiceImpl implements LogService {
	
	private final Logger LOG = LoggerFactory.getLogger(LogServiceImpl.class);

	@Autowired
	LogRepository logRepository;
	
	@Autowired
	Consumer consumer;
	
	@Autowired
	Producer producer;
	
	@Autowired
	VariablesBean variablesBean;
	
	public Log saveLogData(String name, String details) {
		LOG.info("Enter saveLogData!");
		Log log = new Log();
		log.setName(name);
		log.setLog(details);
		log.setDate(new Date());
		logRepository.save(log);
		LOG.info("Exit saveLogData!");
		return log;
	}
	
	public void executeThread(int param1, int param2) {
		LOG.info("Entered executeThread!");
		consumer.setTotalThreads(param2);
		producer.setTotalThreads(param1);
		executeThread();
		LOG.info("Exit executeThread!");
	}
	
	public void executeThread() {
		consumer.start();
		producer.start();
		producer.getExecutor().shutdown();
		consumer.getExecutor().shutdown();
		try {
			producer.getExecutor().awaitTermination(20, TimeUnit.SECONDS);
			consumer.getExecutor().awaitTermination(20, TimeUnit.SECONDS);
			LOG.info("variablesBean : " + variablesBean.getAtomicInteger().get());
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOG.debug("executor.awaitTermination", e);
		}
		
		if (variablesBean.getAtomicInteger().get() == 0 || variablesBean.getAtomicInteger().get() == 100) {
			saveLogData("thread run consumer and producer", "variablesBean:" + variablesBean.getAtomicInteger().get() );
			LOG.info("executeThread->saveLogData");
		}
	}
}
