package com.example.demo1.services;

import com.example.demo1.domain.Log;

public interface LogService {

	Log saveLogData(String name, String details);

	void executeThread(int param1, int param2);
	
	void executeThread();
}
 