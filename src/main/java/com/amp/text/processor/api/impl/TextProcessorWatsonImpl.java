/**
 * 
 */
package com.amp.text.processor.api.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.text.processor.api.base.TextProcessorBase;
import com.amp.text.processor.api.interfaces.TextProcessorInterface;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.CategoriesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.KeywordsOptions;

/**
 * @author MVEKSLER
 *
 */
public class TextProcessorWatsonImpl extends TextProcessorBase  implements TextProcessorInterface
{
	private static final Logger LOG = 
			LoggerFactory.getLogger(TextProcessorWatsonImpl.class);
	
	protected String WATSON_URL = "https://gateway.watsonplatform.net/natural-language-understanding/api/v1";
	//--account 1
	protected String WATSON_TOKEN = "e78399df-329c-4696-bf19-769230782c23";
	protected String WATSON_PASSWORD = "0lq0Q8ZKClHW";
	
	public String getPassword() {
		return WATSON_PASSWORD;
	}

	public void setPassword(String password) {
		this.WATSON_PASSWORD = password;
	}

	public String getUrl() {
		return WATSON_URL;
	}


	public void setUrl(String url) {
		this.WATSON_URL = url;
	}

	public String getToken() {
		return WATSON_TOKEN;
	}


	public void setToken(String token) {
		this.WATSON_TOKEN = token;
	}

	public TextProcessorWatsonImpl()
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	      
		}
		catch( Exception e)
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
		}
	}
	
	public TextProcessorWatsonImpl(HashMap<String, String> cSystemConfiguration)
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        this.setcSystemConfiguration(cSystemConfiguration);
	        
	        this.setSystemProperties();
		}
		catch( Exception e)
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
		}
	}
	
	public TextProcessorWatsonImpl(HashMap<String, String> cSystemConfiguration,
								   HashMap<String, String> cWorkerConfiguration)
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        this.setcSystemConfiguration(cSystemConfiguration);
	        
	        this.setcWorkerConfiguration(cWorkerConfiguration);
	        
	        this.setSystemProperties();
	        
	        this.setWorkerProperties(this.getClass());
		}
		catch( Exception e)
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
		}
	}

	
	public String extractDataFromTextByGet(HashMap<String, String> params)
	{
		@SuppressWarnings("unused")
		boolean cRes = true;
		
		String cMethodName = "";
	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
			
	        String cToken = this.WATSON_TOKEN;
	        String cPassword = this.WATSON_PASSWORD;
	        String cText = "";
	        
	      
	        //---
	        if ( params.containsKey("token"))
	        {
	        	cToken = params.get("token");
	        }
	        if ( params.containsKey("password"))
	        {
	        	cPassword = params.get("password");
	        }
	        if ( params.containsKey("text"))
	        {
	        	cText = params.get("text");
	        }
	      
	        //---
	        String url = "www.ibm.com";
	        
	        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
	        		  NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
	        		  cToken,
	        		  cPassword
	        		);

	        CategoriesOptions categories = new CategoriesOptions();
	        
	       
	        Features features = new Features.Builder()
	          .categories(categories)
	          .build();

	        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
	          .url(url)
	          .text(cText)
	          .features(features)
	          .build();

	        AnalysisResults response = service
	          .analyze(parameters)
	          .execute();
	        
	        System.out.println(response);
	        		
	        return response.toString();
		}
		
		catch( Exception e)
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return "";
		}
		
	}

	
	public String extractDataFromTextByPost(HashMap<String, String> params)
	{
		@SuppressWarnings("unused")
		boolean cRes = true;
		
		String cMethodName = "";
	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
			
	        String cToken = this.WATSON_TOKEN;
	        String cPassword = this.WATSON_PASSWORD;
	        String cText = "";
	        
	        String isEntitiesEmotion   = "true";
	        String isEntitiesSentiment = "true";
	        String cEntitiesLimit = "2";
	        
	        String isKeywordsEmotion   = "true";
	        String isKeywordsSentiment = "true";
	        String cKeywordsLimit = "2";
	        //---
	        if ( params.containsKey("token"))
	        {
	        	cToken = params.get("token");
	        }
	        if ( params.containsKey("password"))
	        {
	        	cPassword = params.get("password");
	        }
	        if ( params.containsKey("text"))
	        {
	        	cText = params.get("text");
	        }
	        
	        if ( params.containsKey("isEntitiesEmotion"))
	        {
	        	isEntitiesEmotion = params.get("isEntitiesEmotion");
	        }
	        if ( params.containsKey("isEntitiesSentiment"))
	        {
	        	isEntitiesSentiment = params.get("isEntitiesSentiment");
	        }
	        if ( params.containsKey("cEntitiesLimit"))
	        {
	        	cEntitiesLimit = params.get("cEntitiesLimit");
	        }
	        
	        if ( params.containsKey("isKeywordsEmotion"))
	        {
	        	isKeywordsEmotion = params.get("isKeywordsEmotion");
	        }
	        if ( params.containsKey("isKeywordsSentiment"))
	        {
	        	isKeywordsSentiment = params.get("isKeywordsSentiment");
	        }
	        if ( params.containsKey("cKeywordsLimit"))
	        {
	        	cKeywordsLimit = params.get("cKeywordsLimit");
	        }
	        //---
	        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
	        		  NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
	        		  cToken,
	        		  cPassword
	        		);

    		EntitiesOptions entitiesOptions = new EntitiesOptions.Builder()
    		  .emotion(Boolean.valueOf(isEntitiesEmotion))
    		  .sentiment(Boolean.valueOf(isEntitiesSentiment))
    		  .limit(Integer.valueOf(cEntitiesLimit))
    		  .build();

    		KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
    		  .emotion(Boolean.valueOf(isKeywordsEmotion))
        	  .sentiment(Boolean.valueOf(isKeywordsSentiment))
        	  .limit(Integer.valueOf(cKeywordsLimit))
    		  .build();

    		Features features = new Features.Builder()
    		  .entities(entitiesOptions)
    		  .keywords(keywordsOptions)
    		  .build();

    		AnalyzeOptions parameters = new AnalyzeOptions.Builder()
    		  .text(cText)
    		  .features(features)
    		  .build();

    		AnalysisResults response = service
    		  .analyze(parameters)
    		  .execute();
	        		
	        System.out.println(response);
	        
	        return response.toString();
		}
		
		catch( Exception e)
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return "";
		}
		
	}
	//---
	
	@Override
	public LinkedHashMap<Double, String> getKeywordsFromJSONData(
			String jsonData, int numKeywords, boolean checkEmotions) 
	{
		boolean cRes = true;
		
		String cMethodName = "";
				
		StringBuffer cOutput = new StringBuffer("");
		
		LinkedHashMap<Double, String> cKeywords = 
				new LinkedHashMap<Double, String>();
				
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        //---
	        if ( StringUtils.isBlank(jsonData) ) 
	        {
	        	System.out.println("M.V. Custom::" + cMethodName + "::entityData is empty!");
	        	
	        	cRes = false;
	        }
	       
	        //---
	        if ( cRes )
	        {
	        	JSONParser parser = new JSONParser();
	        	
	        	JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
	        	
	        	JSONArray keywords = (JSONArray) jsonObject.get("keywords");
	        	
	        	int numKeyword = 0;
	        	
	        	for (Object keyword : keywords) 
	        	{
	                JSONObject keywordObj = (JSONObject) keyword;
	                
	                String keywordValue   = (String) keywordObj.get("text");
	                Double relevanceValue = (Double) keywordObj.get("relevance");
	                
	                JSONObject emotionObject = (JSONObject)keywordObj.get("emotion");
	                Double sadness = (Double) emotionObject.get("sadness");
	                Double joy     = (Double) emotionObject.get("joy");
	                Double fear    = (Double) emotionObject.get("fear");
	                Double disgust = (Double) emotionObject.get("disgust");
	                Double anger   = (Double) emotionObject.get("anger");
	                
	                if ( numKeyword < numKeywords )
	                {
	                	if ( !checkEmotions )
	                	{
	                		cKeywords.put(relevanceValue, keywordValue);
	                	}
	                	else
	                	{
	                		if ( (joy > sadness) && (joy > fear) && 
	                			 (joy > disgust) && (joy > anger))
	                		{
	                			cKeywords.put(relevanceValue, keywordValue);
	                		}
	                	}
	                	++numKeyword;
	                }
	                
	                cOutput.append(keywordValue);
	                cOutput.append(" ");
	                
	                System.out.println(keywordValue);
	        	}    
	        	//---
	        }
	        
	        return cKeywords;
		}
        catch( Exception e)
		{
        	LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return new LinkedHashMap<Double, String>();
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public String getKeywordsFromJSONData(String jsonData) 
	{
		boolean cRes = true;
		
		String cMethodName = "";
				
		StringBuffer cOutput = new StringBuffer("");
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	     
	        if ( StringUtils.isBlank(jsonData) ) 
	        {
	        	System.out.println("M.V. Custom::" + cMethodName + "::entityData is empty!");
	        	
	        	cRes = false;
	        }
	        
	        if ( cRes )
	        {
	        	JSONParser parser = new JSONParser();
	        	
	        	JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
	        	
	        	JSONArray keywords = (JSONArray) jsonObject.get("keywords");
	        	
	        	for (Object keyword : keywords) 
	        	{
	                JSONObject keywordObj = (JSONObject) keyword;
	                
	                String keywordValue   = (String) keywordObj.get("text");
	                Double relevanceValue = (Double) keywordObj.get("relevance");
	                
	                JSONObject emotionObject = (JSONObject)keywordObj.get("emotion");
	                Double sadness = (Double) emotionObject.get("sadness");
	                Double joy     = (Double) emotionObject.get("joy");
	                Double fear    = (Double) emotionObject.get("fear");
	                Double disgust = (Double) emotionObject.get("disgust");
	                Double anger   = (Double) emotionObject.get("anger");
	                
	                cOutput.append(keywordValue);
	                cOutput.append(" ");
	                
	                System.out.println(keywordValue);
	        	}    
	        	//---
	        }
	        
	        return cOutput.toString();
		}
        catch( Exception e)
		{
        	LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return "";
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public String getEntitiesFromJSONData(String jsonData) 
	{
		boolean cRes = true;
		
		String cMethodName = "";
				
		StringBuffer cOutput = new StringBuffer("");
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	     
	        if ( StringUtils.isBlank(jsonData) ) 
	        {
	        	System.out.println("M.V. Custom::" + cMethodName + "::entityData is empty!");
	        	
	        	cRes = false;
	        }
	        
	        if ( cRes )
	        {
	        	JSONParser parser = new JSONParser();
	        	
	        	JSONObject jsonObject = (JSONObject) parser.parse(jsonData);

	        	//---
	        	JSONArray entities = (JSONArray) jsonObject.get("entities");
	        	
	        	for (Object entity : entities) 
	        	{
	                JSONObject entityObj = (JSONObject) entity;
	                
	                String entityValue   = (String) entityObj.get("text");
	                Double relevanceValue = (Double) entityObj.get("relevance");
	                
	                cOutput.append(entityValue);
	                cOutput.append(" ");
	                
	                System.out.println(entityValue);
	        	}    
	        }
	        
	        return cOutput.toString();
		}
        catch( Exception e)
		{
        	LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return "";
		}
	}
}
