package com.example.demo1.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.bean.VariablesBean;
import com.example.demo1.domain.Log;
import com.example.demo1.services.LogService;

@RestController
@RequestMapping(value = "/request")
public class RequestController {

	private final Logger LOG = LoggerFactory.getLogger(RequestController.class);

	@Autowired
	LogService logService;

	@Autowired
	VariablesBean variablesBean;

	@RequestMapping(value = "/create", produces = { "application/JSON" })
	@ResponseBody
	public ResponseEntity<Log> create(@RequestParam(value = "param1") Integer param1,
			@RequestParam(value = "param2") Integer param2) {
		LOG.info("Entered create!");
		variablesBean.setVal(50);
		Log log = logService.saveLogData("create", "param1:" + param1 + ", param2:" + param2);
		
		logService.executeThread(param1, param2);
		
		LOG.info("Exit create!");
		return new ResponseEntity<Log>(log, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/update", produces = { "application/JSON" })
	@ResponseBody
	public ResponseEntity<Log> update(@RequestParam(value = "param1") Integer param1) {
		LOG.info("Entered update!");
		variablesBean.setVal(param1);
		Log log = logService.saveLogData("update", "param1:" + param1);
		
		logService.executeThread();
		
		LOG.info("Exit update!");
		return new ResponseEntity<Log>(log, HttpStatus.OK);
	}
}
