package com.stellarhealth.service;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Service
public class AmazonS3Service {
private AmazonS3 s3client;
@Value("${s3.endpointUrl}")
private String endpointUrl;
@Value("${s3.bucketName}")
private String bucketName;
@Value("${s3.accessKeyId}")
private String accessKeyId;
@Value("${s3.secretKey}")
private String secretKey;
@Value("${s3.region}")
private String region;
@Value("${s3.readkey}")
private String readkey;
@Value("${s3.writekey}")
private String writekey;

@PostConstruct
private void initializeAmazon() {
  AWSCredentials credentials 
    = new BasicAWSCredentials(this.accessKeyId, this.secretKey);
  this.s3client = AmazonS3ClientBuilder.standard()
  .withRegion(region)
  .withCredentials(new AWSStaticCredentialsProvider(credentials))
  .build();
}

public void upload(String logs)
{
	s3client.putObject(
			  bucketName, 
			  writekey , logs
			);

}
public String anomyizeLogs() 
{
    try 
    {
        try (InputStream is = s3client.getObject(bucketName, readkey).getObjectContent()) 
        {
  
          return helper(is);
         
        }
    } catch (Exception e) 
    {
        throw new IllegalStateException(e);
    }
}

private String changeDOB(String aLine) {
		
	int yearIndex = aLine.lastIndexOf("/");
	
	String year = aLine.substring(yearIndex+1,yearIndex+5<aLine.length()?yearIndex+5:aLine.length());
	
	StringBuilder sb = new StringBuilder();
	sb.append("DOB=");
	sb.append("'");
	sb.append("X");
	sb.append("/");
	sb.append("X");
	sb.append("/");
	sb.append(year);
	sb.append("'");
	
	return sb.toString();
}

private String helper(InputStream is) throws UnsupportedEncodingException
{
    Scanner scanner = new Scanner(is, StandardCharsets.UTF_8);    

    StringBuilder sb = new StringBuilder();

    while (scanner.hasNext())
    {
    	
    	String logLine = scanner.nextLine();
    	
        String[] logLineArray = logLine.split(" ");

        for(String str:logLineArray) {
     	  
     	   if(str.startsWith("DOB")) {
     		   
     		   String output = changeDOB(str);
     		   
     		   sb.append(output);
     	   }
     	   else {
     		   sb.append(str);

     	   }
     	   
     	   sb.append(" ");
        }
        
        sb.append(System.getProperty("line.separator"));
        
  }
    
    sb.deleteCharAt(sb.length()-1);

   return sb.toString();
    
}

}
