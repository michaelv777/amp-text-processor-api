/**
 * 
 */
package com.amp.text.processor.api.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.amp.text.processor.api.base.TextProcessorBase;
import com.amp.text.processor.api.interfaces.TextProcessorInterface;

/**
 * @author MVEKSLER
 *
 */
public class TextProcessorDandelionImpl extends TextProcessorBase  implements TextProcessorInterface
{
	private static final Logger LOG = 
			LoggerFactory.getLogger(TextProcessorDandelionImpl.class);
	
	protected String url = "https://api.dandelion.eu/datatxt/nex/v1";
	
	protected String token = "41a33dd86024475d99c428e418398145"; 
	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	public TextProcessorDandelionImpl()
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
	
	public TextProcessorDandelionImpl(HashMap<String, String> cSystemConfiguration,
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
		}
		catch( Exception e)
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
		}
	}
	
	public TextProcessorDandelionImpl(HashMap<String, String> cSystemConfiguration)
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

	@Override
	public String extractDataFromTextByGet( HashMap<String, String> params ) 
	{
		String cMethodName = "";
	
		String cResponse = "";
		
		boolean cRes = true;
	
		URL restUrl = null;
		
		try 
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
			
			if ( cRes )
			{
	        	if ( !params.containsKey("text"))
	        	{
	        		LOG.info(cMethodName + "::text parameter should be provided");
	        		
	        		return cResponse;
	        	}
			}
			
			String text = "";
			String token = "";
			String min_confidence = "";
			String social_hashtag = "";
			String social_mention = "";
			String include = "";
			
			if ( params.containsKey("text") )
			{
				text = params.get("text");
			}
			if ( params.containsKey("token") )
			{
				token = params.get("token");
			}
			else
			{
				token = this.getToken();
			}
			if ( params.containsKey("min_confidence") )
			{
				min_confidence = params.get("min_confidence");
			}
			if ( params.containsKey("social.hashtag") )
			{
				social_hashtag = params.get("social.hashtag");
			}
			if ( params.containsKey("social.mention") )
			{
				social_mention = params.get("social.mention");
			}
			if ( params.containsKey("include") )
			{
				include = params.get("include");
			}
			
		    if ( cRes )
		    {
		        restUrl = new URL(this.getUrl());
		    }
		
		    ClientHttpRequestFactory requestFactory = 
					new HttpComponentsClientHttpRequestFactory();
	        
	        RestTemplate restTemplate = new RestTemplate(requestFactory);
	        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	        
		    HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		    
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("text", text);
			requestParams.put("token", token);
			requestParams.put("min_confidence", min_confidence);
			requestParams.put("social.hashtag", social_hashtag);
			requestParams.put("social.mention", social_mention);
			requestParams.put("include", include);
	        		   
		    final String endpointURL = restUrl.toString();
		    
		    LOG.info(cMethodName + "::formattedURL: " + endpointURL);
	        
		    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpointURL);
		    for (Map.Entry<String, String> entry : requestParams.entrySet()) {
		        builder.queryParam(entry.getKey(), entry.getValue());
		    }
		    
		    HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
		   
		    ResponseEntity<String> response = restTemplate.exchange(
		    		builder.toUriString(), HttpMethod.GET, requestEntity, String.class);
		    
		    if (response.getStatusCode() == HttpStatus.OK) 
			{
				cResponse =  String.valueOf(response.getStatusCode().value()) + ":" + response.getBody();
	
				LOG.info(cMethodName + "::" + cResponse);
			} 
			else 
			{
				cResponse = String.valueOf(response.getStatusCode().value()) + ":" + response.getBody();
	
				LOG.error(cMethodName + "::" + cResponse);
			}
		    
			return cResponse;
	
		} 
		catch (Exception e) 
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
	
			return StringUtils.EMPTY;
		}
	}
	

	public String extractEntityFromTextByGetHttp(HashMap<String, String> params)
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		URL restUrl = null;
		
		HttpsURLConnection conn = null;
		
		BufferedReader br = null;
				
		StringBuffer cOutput = new StringBuffer("");
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	      
	       if ( !params.containsKey("text"))
	       {
	        	LOG.info(cMethodName + "::text parameter should be provided");
	        		
	        	cRes = false;
	       }
	        
	       
	        if ( cRes )
	        {
	        	 restUrl = new URL(this.getUrl());
	        }
	        
	        if ( cRes )
	        {
		        conn = (HttpsURLConnection) restUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
		        
				if ( params.containsKey("text") )
				{
					conn.setRequestProperty("text", params.get("text"));
				}
				if ( params.containsKey("token") )
				{
					conn.setRequestProperty("token", params.get("token"));
				}
				else
				{
					conn.setRequestProperty("token", this.getToken());
				}
				if ( params.containsKey("min_confidence") )
				{
					conn.setRequestProperty("min_confidence", params.get("min_confidence"));
				}
				if ( params.containsKey("social.hashtag") )
				{
					conn.setRequestProperty("social.hashtag", params.get("social.hashtag"));
				}
				if ( params.containsKey("social.mention") )
				{
					conn.setRequestProperty("social.mention", params.get("social.mention"));
				}
				if ( params.containsKey("include") )
				{
					conn.setRequestProperty("include", params.get("include"));
				}
				
				int responseCode = conn.getResponseCode();
				if (responseCode != HttpsURLConnection.HTTP_OK) 
				{
					LOG.info(cMethodName + "::" + conn.getResponseCode());
					LOG.info(cMethodName + "::" + conn.getResponseMessage());
					
					cRes = false;
				}
	        }
			
			if ( cRes )
			{
				br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	
				LOG.info("Output from Server .... \n");
				String output = "";
				
				while ((output = br.readLine()) != null) 
				{
					LOG.info(output);
					
					cOutput.append(output);
				}
			}
			
	        return cOutput.toString();
		}
		catch (MalformedURLException e) 
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return StringUtils.EMPTY;

		}
		catch( Exception e)
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return StringUtils.EMPTY;
		}
		finally
		{
			if ( br != null )
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if ( conn != null )
			{
				conn.disconnect();
			}
		}
	}

	@Override
	public String extractDataFromTextByPost(HashMap<String, String> params) 
	{
		String cMethodName = "";
	
		String cResponse = "";
		
		boolean cRes = true;
	
		URL restUrl = null;
		
		try 
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
			
			if ( !params.containsKey("text"))
			{
        		LOG.info(cMethodName + "::text parameter should be provided");
        		
        		return cResponse;
			}
			
			String text = "";
			String token = "";
			String min_confidence = "";
			String social_hashtag = "";
			String social_mention = "";
			String include = "";
			
			if ( params.containsKey("text") )
			{
				text = params.get("text");
			}
			if ( params.containsKey("token") )
			{
				token = params.get("token");
			}
			else
			{
				token = this.getToken();
			}
			if ( params.containsKey("min_confidence") )
			{
				min_confidence = params.get("min_confidence");
			}
			if ( params.containsKey("social.hashtag") )
			{
				social_hashtag = params.get("social.hashtag");
			}
			if ( params.containsKey("social.mention") )
			{
				social_mention = params.get("social.mention");
			}
			if ( params.containsKey("include") )
			{
				include = params.get("include");
			}
			
		    if ( cRes )
		    {
		        restUrl = new URL(this.getUrl());
		    }
			
		    ClientHttpRequestFactory requestFactory = 
					new HttpComponentsClientHttpRequestFactory();
	        
	        RestTemplate restTemplate = new RestTemplate(requestFactory);
	        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	        
		    HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		    
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("text", text);
			requestParams.put("token", token);
			requestParams.put("min_confidence", min_confidence);
			requestParams.put("social.hashtag", social_hashtag);
			requestParams.put("social.mention", social_mention);
			requestParams.put("include", include);
	        		   
		    final String endpointURL = restUrl.toString();
		    
		    LOG.info(cMethodName + "::formattedURL: " + endpointURL);
	        
		    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpointURL);
		    for (Map.Entry<String, String> entry : requestParams.entrySet()) {
		        builder.queryParam(entry.getKey(), entry.getValue());
		    }
		    
		    HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
		   
		    ResponseEntity<String> response = restTemplate.exchange(
		    		builder.toUriString(), HttpMethod.POST, requestEntity, String.class);
		    
			if (response.getStatusCode() == HttpStatus.OK) 
			{
				cResponse =  String.valueOf(response.getStatusCode().value()) + ":" + response.getBody();
	
				LOG.info(cMethodName + "::" + cResponse);
			} 
			else 
			{
				cResponse = String.valueOf(response.getStatusCode().value()) + ":" + response.getBody();
	
				LOG.error(cMethodName + "::" + cResponse);
			}
			
			return cResponse;
	
		} 
		catch (Exception e) 
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return StringUtils.EMPTY;
		}
	}
	
	
	public String extractEntityFromTextByPostHttp(HashMap<String, String> params)
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		URL restUrl = null;
		
		HttpsURLConnection conn = null;
		
		BufferedReader br = null;
				
		StringBuffer cOutput = new StringBuffer("");
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
			
	        final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0";
	        
	        String reqParams = "";
	        
	        if ( cRes )
	        {
	        	if ( params.containsKey("text"))
	        	{
	        		reqParams += "text=" + params.get("text");
	        	}
	        	else
	        	{
	        		LOG.info(cMethodName + "::text parameter should be provided");
	        		
	        		cRes = false;
	        	}
	        	
	        	if ( params.containsKey("token"))
	        	{
	        		reqParams += "&";
	        		reqParams += "token=" + params.get("token");
	        	}
	        	else
	        	{
	        		reqParams += "&";
	        		reqParams += "token=" + this.getToken();
	        	}
	        	
	        	if ( params.containsKey("min_confidence"))
	        	{
	        		reqParams += "&";
	        		reqParams += "min_confidence=" + params.get("min_confidence");
	        	}
	        	
	        	if ( params.containsKey("social.hashtag"))
	        	{
	        		reqParams += "&";
	        		reqParams += "social.hashtag=" + params.get("social.hashtag");
	        	}
	        	
	        	if ( params.containsKey("social.mention"))
	        	{
	        		reqParams += "&";
	        		reqParams += "social.mention=" + params.get("social.mention");
	        	}
	        	
	        	if ( params.containsKey("include")) //i.e. types, categories
	        	{
	        		reqParams += "&";
	        		reqParams += "include=" + params.get("include");
	        	}
	        }
	        
	        if ( cRes )
	        {
	        	 restUrl = new URL(this.getUrl());
	        }
	        
	        if ( cRes )
	        {
	        	conn = (HttpsURLConnection) restUrl.openConnection();
	        	conn.setRequestMethod("POST");
	        	conn.setRequestProperty("User-Agent", USER_AGENT);
	        	conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	        	conn.setDoOutput(true);
	    		
	        	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	    		
	    		wr.writeBytes(reqParams);
	    		wr.flush();
	    		wr.close();
	        }
	        
	        if ( cRes )
	        {
	        	int responseCode = conn.getResponseCode();
	        	
	    		LOG.info("\nSending 'POST' request to URL : " + url);
	    		LOG.info("Post parameters : " + reqParams);
	    		LOG.info("Response Code : " + responseCode);
	    		
	        	if (responseCode != HttpsURLConnection.HTTP_OK) 
				{
					LOG.info(cMethodName + "::" + conn.getResponseCode());
					LOG.info(cMethodName + "::" + conn.getResponseMessage());
					
					cRes = false;
				}
	        }
			if ( cRes )
			{
				br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	
				LOG.info("Output from Server .... \n");
				String output = "";
				
				while ((output = br.readLine()) != null) 
				{
					LOG.info(output);
					
					cOutput.append(output);
				}
			}
			
	        return cOutput.toString();
		}
		catch (MalformedURLException e) 
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return StringUtils.EMPTY;

		}
		catch( Exception e)
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return StringUtils.EMPTY;
		}
		finally
		{
			if ( br != null )
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if ( conn != null )
			{
				conn.disconnect();
			}
		}
	}
	
	@Override
	public String getKeywordsFromJSONData(String entityData) 
	{
		boolean cRes = true;
		
		String cMethodName = "";
				
		StringBuffer cOutput = new StringBuffer("");
		
		int maxNumberOfKeywords = 2;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	     
	        if ( StringUtils.isBlank(entityData) ) 
	        {
	        	LOG.info(cMethodName + "::entityData is empty!");
	        	
	        	cRes = false;
	        }
	        
	        int cKeywordIndex = 1;
	        
	        if ( cRes )
	        {
	        	JSONParser parser = new JSONParser();
	        	
	        	JSONObject jsonObject = (JSONObject) parser.parse(entityData);
	        	
	        	JSONArray annotations = (JSONArray) jsonObject.get("annotations");
	            
	        	for (Object annotationObj : annotations) 
	        	{
	                JSONObject annotation = (JSONObject) annotationObj;
	                
	                String spot = (String) annotation.get("spot");
	                
	                if ( cKeywordIndex <= maxNumberOfKeywords )
	                {
		                cOutput.append(spot);
		                cOutput.append(" ");
		                
		                ++cKeywordIndex;
	                }
	                LOG.info(spot);
	        	}    
	        }
	        
	        return cOutput.toString();
		}
        catch( Exception e)
		{
        	LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return StringUtils.EMPTY;
		}
	}


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
	     
	        if ( StringUtils.isBlank(jsonData) ) 
	        {
	        	LOG.info(cMethodName + "::entityData is empty!");
	        	
	        	cRes = false;
	        }
	        
	        int cKeywordIndex = 0;
	        
	        if ( cRes )
	        {
	        	JSONParser parser = new JSONParser();
	        	
	        	JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
	        	
	        	JSONArray annotations = (JSONArray) jsonObject.get("annotations");
	            
	        	for (Object annotationObj : annotations) 
	        	{
	                JSONObject annotation = (JSONObject) annotationObj;
	                
	                String spot = (String) annotation.get("spot");
	                
	                if ( cKeywordIndex < numKeywords )
	                {
		                cOutput.append(spot);
		                cOutput.append(" ");
		                
		                ++cKeywordIndex;
	                }
	                LOG.info(spot);
	        	}   
	        	
	        	cKeywords.put(new Double(1), cOutput.toString());
	        }
	        
	        return cKeywords;
		}
        catch( Exception e)
		{
        	LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return new LinkedHashMap<Double, String>();
		}
	}


	@Override
	public String getEntitiesFromJSONData(String jsonData) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
