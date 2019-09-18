package com.example.demo1.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo1.bean.VariablesBean;

@Component
public class Producer {
	
	private final Logger LOG = LoggerFactory.getLogger(Producer.class);

	@Autowired
	VariablesBean variablesBean;
	
	private ExecutorService executor; 
	
	private int noThreads;

	public void setTotalThreads(int noThreads) {
		LOG.info("Entered setTotalThreads!");
		this.noThreads = noThreads;
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(noThreads);
		LOG.info("Exit setTotalThreads!");
	}

	public void start() {
		LOG.info("Total max pool size : " + noThreads);
		for (int i = 0; i < noThreads; i++) {
			executor.submit(new Thread("Thread#" + i) {
				@Override
				public void run() {
					int val = 0;
					while (variablesBean.getRun().get()) {
						val = variablesBean.incrementGet();
						LOG.info(this.getName() + " val = " + val);
						if (val == 0 || val == 100) {
							variablesBean.setVal(val);
							variablesBean.getRun().set(false);
						}
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							LOG.debug("Thread.sleep(500)", e);
						}
					}
				}
			});
		}
	}
	
	public ExecutorService getExecutor() {
		return executor;
	}

	public static void main(String[] args) {
		Producer prod = new Producer();
		prod.setTotalThreads(30);
		prod.start();
	}
}
