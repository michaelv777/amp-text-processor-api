package com.amp.text.processor.api.test;

import static org.junit.Assert.fail;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.amp.text.processor.api.impl.TextProcessorDandelionImpl;
import com.amp.text.processor.api.interfaces.TextProcessorInterface;

public class TestTextProcessorDandelionImpl {

	@Before
	public void setUp() throws Exception 
	{
		System.setProperty("javax.net.trustStore", "NONE");
		System.setProperty("javax.net.debug", "SSL");
		
		String certificatesTrustStorePath = "C:/Installs/Java/zulu8.36.0.1-ca-jdk8.0.202-win_x64/zulu8.36.0.1-ca-jdk8.0.202-win_x64/jre/lib/security/cacerts";
		System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testEractEntityFromTextByPost() 
	{
		String cMethodName = "";
	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        //String text = "UGREEN USB 2.0 OTG Cable On The Go Adapter Micro USB to USB for Samsung S6 Edge S4 S3 Android or Windows Phones Tablets with OTG Function 4.7 (2 Pack, Black)";
	        //String text = "And why bother Samsung Galaxy A5 cellphone. The price is less pleasant.";
	        String text = "Please advise the bread maker! I heard that many houses make bread, went to Amazon, a million different options. Advise what you use, like or not. And it is possible and for 70 to buy and for 350 and all equally good reviews .........";	
	        
	        HashMap<String, String> params = new HashMap<String, String>();
	        params.put("text", text);
	        params.put("min_confidence", "0.5");
	        params.put("social.hashtag", "true");
	        params.put("social.mention", "true");
	        params.put("include", "types, categories");
        
	        TextProcessorInterface cTextProcessor = new TextProcessorDandelionImpl();
        	
	        String jsonResponse = cTextProcessor.extractDataFromTextByPost(params);
	        
	        System.out.println(cMethodName + "::" + jsonResponse);
	        
	        if ( !StringUtils.isEmpty(jsonResponse))
	        {
	        	String cKeywrodsText = cTextProcessor.getKeywordsFromJSONData(jsonResponse);
	        	
	        	System.out.println(cMethodName + "::cKeywrodsText=" + cKeywrodsText);
	        }
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			fail(cMethodName + "::" + e.getMessage()); 
		}
	}
	
	@Ignore
	@Test
	public void testEractEntityFromTextByGet() 
	{
		String cMethodName = "";
	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String text = "UGREEN USB 2.0 OTG Cable On The Go Adapter Micro USB to USB for Samsung S6 Edge S4 S3 Android or Windows Phones Tablets with OTG Function 4.7 (2 Pack, Black)";
	        
	        HashMap<String, String> params = new HashMap<String, String>();
	        params.put("text", text);
	        params.put("min_confidence", "0.7");
	        params.put("social.hashtag", "true");
	        params.put("social.mention", "true");
	        params.put("include", "types, categories");
        
	        TextProcessorInterface cTextProcessor = new TextProcessorDandelionImpl();
        	
	        String jsonResponse = cTextProcessor.extractDataFromTextByGet(params);
	        
	        System.out.println(cMethodName + "::" + jsonResponse);
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			fail(cMethodName + "::" + e.getMessage()); 
		}
	}

	@Ignore
	@Test
	public void testGetKeywordsFromEntityData() 
	{
		String cMethodName = "";
	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String entityData = 
	        		"";
	        
	        TextProcessorInterface cTextProcessor = new TextProcessorDandelionImpl();
        	
	        String jsonResponse = cTextProcessor.getKeywordsFromJSONData(entityData);
	        
	        System.out.println(cMethodName + "::" + jsonResponse);
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			fail(cMethodName + "::" + e.getMessage()); 
		}
	}
	
}
