package com.example.demo1.bean;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class VariablesBean {

	private final AtomicInteger atomicInteger = new AtomicInteger();
	private final AtomicBoolean run = new AtomicBoolean(true);
	
	public Integer incrementGet() {
		return atomicInteger.incrementAndGet();
	}
	
	public Integer decrementGet() {
		return atomicInteger.decrementAndGet();
	}
	
	public void setVal(Integer val) {
		atomicInteger.lazySet(val);
	}
	
	public AtomicInteger getAtomicInteger() {
		return atomicInteger;
	}
	
	public AtomicBoolean getRun() {
		return run;
	}
}
