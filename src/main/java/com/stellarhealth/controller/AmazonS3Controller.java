package com.stellarhealth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stellarhealth.service.AmazonS3Service;


@RestController
public class AmazonS3Controller {
	
	@Autowired
	AmazonS3Service service;

	@RequestMapping("/")
	public String home()
	{
		return "<h1>Welcome</h1>";
	}
	@RequestMapping("/anonymizelogs")
	public void anonymizelogs()
	{
		String logs = null;
		try{
			 logs = service.anomyizeLogs();
		}
		catch(Exception e){
			throw new RuntimeException("Error in parsing log content");
		}
		
		try{
			service.upload(logs);

		}
		catch(Exception e){
			throw new RuntimeException("Error in uploading log content");
		}
	}
	
	@RequestMapping("/Admin")
	public String adminHome()
	{
		return "<h1>Welcome Admin</h1>";
	}
}

