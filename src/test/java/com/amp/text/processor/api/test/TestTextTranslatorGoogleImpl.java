package com.amp.text.processor.api.test;

import static org.junit.Assert.fail;

import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.amp.text.translator.api.impl.TextTranslatorGoogleImpl;
import com.amp.text.translator.api.interfaces.TextTranslatorInterface;

public class TestTextTranslatorGoogleImpl {

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
	public void testTranslateFromTextByGet() 
	{
		String cMethodName = "";
	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String text = 
	        		"Test: Question on the Android smartphone.Refresh the phone. "
	        		+ "Necessary for communication ie. What's Up, Facebook, Skype, viewing photos and clips. Need more screen.";
	        
	        String textEncoded = URLEncoder.encode(text, "UTF-8");
	        
	        TextTranslatorGoogleImpl cTextProcessor = new TextTranslatorGoogleImpl();
	    	
	        String jsonResponse = cTextProcessor.translateTextByGet(textEncoded);
	        
	        System.out.println(cMethodName + "::" + jsonResponse);
	        
	        if ( !StringUtils.isEmpty(jsonResponse))
	        {
	        	String cTranslatedText = cTextProcessor.getTranslatedData(jsonResponse);
	        	
	        	System.out.println(cMethodName + "::cKeywrodsText=" + cTranslatedText);
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
	public void testTranslateFromTextByPost() 
	{
		String cMethodName = "";
	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String text  = "Билеты в Израиль по 400CAD туда-обратно, но из Торонто";
	        	   
	        String textEncoded = URLEncoder.encode(text, "UTF-8");
	        
	        TextTranslatorGoogleImpl cTextProcessor = new TextTranslatorGoogleImpl();
	    	
	        String jsonResponse = cTextProcessor.translateTextByPost(textEncoded);
	        
	        System.out.println(cMethodName + "::" + jsonResponse);
	        
	        if ( !StringUtils.isEmpty(jsonResponse))
	        {
	        	String cTranslatedText = cTextProcessor.getTranslatedData(jsonResponse);
	        	
	        	System.out.println(cMethodName + "::cKeywrodsText=" + cTranslatedText);
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
	public void testTranslateFromTextByPost2() 
	{
		String cMethodName = "";
	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String text  = "Билеты в Израиль по 400CAD туда-обратно, но из Торонто";
	        	   
	        String textEncoded = URLEncoder.encode(text, "UTF-8");
	        
	        HashMap<String, String> params = new HashMap<String, String>();
	        params.put("client", "gtx");
	        params.put("sl", "auto");
	        params.put("tl", "en");
	        params.put("dt", "t");
	        params.put("q", textEncoded);
        
	        TextTranslatorInterface cTextProcessor = new TextTranslatorGoogleImpl();
        	
	        String jsonResponse = cTextProcessor.translateTextByPostHttp(params);
	        
	        System.out.println(cMethodName + "::" + jsonResponse);
	        
	        if ( !StringUtils.isEmpty(jsonResponse))
	        {
	        	String cTranslatedText = cTextProcessor.getTranslatedData(jsonResponse);
	        	
	        	System.out.println(cMethodName + "::cKeywrodsText=" + cTranslatedText);
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
	public void testTranslateFromTextByGet2() 
	{
		String cMethodName = "";
	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String text = "�? чего к�?тати Samsung Galaxy A5? Цена более менее при�?тна�?.";
	        String textEncoded = URLEncoder.encode(text, "UTF-8");
	        
	        HashMap<String, String> params = new HashMap<String, String>();
	        params.put("client", "gtx");
	        params.put("sl", "auto");
	        params.put("tl", "en");
	        params.put("dt", "t");
	        params.put("q", textEncoded);
        
	        TextTranslatorInterface cTextProcessor = new TextTranslatorGoogleImpl();
        	
	        String jsonResponse = cTextProcessor.translateTextByGetHttp(params);
	        
	        System.out.println(cMethodName + "::" + jsonResponse);
	        
	        if ( !StringUtils.isEmpty(jsonResponse))
	        {
	        	String cTranslatedText = cTextProcessor.getTranslatedData(jsonResponse);
	        	
	        	System.out.println(cMethodName + "::cKeywrodsText=" + cTranslatedText);
	        }
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			fail(cMethodName + "::" + e.getMessage()); 
		}
	}
}
