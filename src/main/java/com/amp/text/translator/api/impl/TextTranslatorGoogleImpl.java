/**
 * 
 */
package com.amp.text.translator.api.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
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
import com.amp.text.translator.api.interfaces.TextTranslatorInterface;

/**
 * @author MVEKSLER
 *
 */
public class TextTranslatorGoogleImpl extends TextProcessorBase implements TextTranslatorInterface
{
	private static final Logger LOG = 
			LoggerFactory.getLogger(TextProcessorBase.class);
	
	protected String url = "https://translate.googleapis.com/translate_a/single";

	private URI getBaseURI() 
	{
		try 
		{
			//URI uri = new URI("http://localhost:21080/amp-storage-api/ConfigurationService");
			URI uri = new URI(url);
			return uri;
			
		} catch (URISyntaxException e) {
			
			LOG.error(e.getMessage(), e);
			
			return null;
		}
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public TextTranslatorGoogleImpl(HashMap<String, String> cSystemConfiguration,
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
	
	public TextTranslatorGoogleImpl(HashMap<String, String> cSystemConfiguration)
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
	
	public TextTranslatorGoogleImpl()
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
	
	
	
	public String translateTextByGet( String textToTranslate ) 
	{
		String cMethodName = "";
	
		String cResponse = "";
		
		@SuppressWarnings("unused")
		boolean cRes = true;
	
		try 
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
			
			final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0";
			
			ClientHttpRequestFactory requestFactory = 
					new HttpComponentsClientHttpRequestFactory();
	        
	        RestTemplate restTemplate = new RestTemplate(requestFactory);
	        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	        
		    HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
			headers.add("user-agent", USER_AGENT);
			
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("client", "gtx");
			requestParams.put("sl", "auto");
			requestParams.put("tl", "en");
			requestParams.put("dt", "t");
			requestParams.put("q", textToTranslate);
			
	        		   
		    final String endpointURL = this.getBaseURI().toString();
		    
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
				cResponse =  response.getBody();
	
				LOG.info(cMethodName + "::" + cResponse);
			} 
			else 
			{
				cResponse = response.getBody();
	
				LOG.error(cMethodName + "::" + cResponse);
			}
			
			return cResponse;
	
		} 
		catch (Exception e) 
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
	
			return "";
		}
	}

	@Override
	public String translateTextByPost(String textToTranslate) 
	{
		String cMethodName = "";
	
		String cResponse = "";
		
		@SuppressWarnings("unused")
		boolean cRes = true;
	
		try 
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
			
			final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0";
			
			ClientHttpRequestFactory requestFactory = 
					new HttpComponentsClientHttpRequestFactory();
	        
	        RestTemplate restTemplate = new RestTemplate(requestFactory);
	        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	        
		    HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
			headers.add("user-agent", USER_AGENT);
			
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("client", "gtx");
			requestParams.put("sl", "auto");
			requestParams.put("tl", "en");
			requestParams.put("dt", "t");
			requestParams.put("q", textToTranslate);
			
	        		   
		    final String endpointURL = this.getBaseURI().toString();
		    
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
	
			return "";
		}
	}

	@Override
	public String translateTextByGetHttp(HashMap<String, String> params)
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		URL restUrl = null;
		
		HttpURLConnection conn = null;
		
		BufferedReader br = null;
				
		StringBuffer cOutput = new StringBuffer("");
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	      
	        final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0";
	        
	        if ( cRes )
	        {
	        	if ( !params.containsKey("text"))
	        	{
	        		System.out.println(cMethodName + "::text parameter should be provided");
	        		
	        		cRes = false;
	        	}
	        }
	        
	        if ( cRes )
	        {
	        	 restUrl = new URL(this.getUrl());
	        }
	        
	        if ( cRes )
	        {
		        conn = (HttpURLConnection) restUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("User-Agent", USER_AGENT);
	        	conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	        	conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				
				if ( params.containsKey("client") )
				{
					conn.setRequestProperty("client", params.get("client"));
				}
				if ( params.containsKey("sl") )
				{
					conn.setRequestProperty("sl", params.get("sl"));
				}
				if ( params.containsKey("tl") )
				{
					conn.setRequestProperty("tl", params.get("tl"));
				}
				if ( params.containsKey("dt") )
				{
					conn.setRequestProperty("dt", params.get("dt"));
				}
				if ( params.containsKey("q") )
				{
					conn.setRequestProperty("q", params.get("q"));
				}
				
				int responseCode = conn.getResponseCode();
				if (responseCode != HttpURLConnection.HTTP_OK) 
				{
					System.out.println(cMethodName + "::" + conn.getResponseCode());
					System.out.println(cMethodName + "::" + conn.getResponseMessage());
					
					cRes = false;
				}
	        }
			
			if ( cRes )
			{
				br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	
				System.out.println("Output from Server .... \n");
				String output = "";
				
				while ((output = br.readLine()) != null) 
				{
					System.out.println(output);
					
					cOutput.append(output);
				}
			}
			
	        return cOutput.toString();
		}
		catch (MalformedURLException e) 
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return "";

		}
		catch( Exception e)
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return "";
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
	public String translateTextByPostHttp(HashMap<String, String> params)
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		URL restUrl = null;
		
		HttpURLConnection conn = null;
		
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
	        	if ( params.containsKey("client"))
	        	{
	        		reqParams += "client=" + params.get("client");
	        	}
	        	else
	        	{
	        		System.out.println(cMethodName + "::text parameter should be provided");
	        		
	        		cRes = false;
	        	}
	        	
	        	if ( params.containsKey("sl"))
	        	{
	        		reqParams += "&";
	        		reqParams += "sl=" + params.get("sl");
	        	}
	        	
	        	if ( params.containsKey("tl"))
	        	{
	        		reqParams += "&";
	        		reqParams += "tl=" + params.get("tl");
	        	}
	        	
	        	if ( params.containsKey("dt"))
	        	{
	        		reqParams += "&";
	        		reqParams += "dt=" + params.get("dt");
	        	}
	        	
	        	if ( params.containsKey("q")) //i.e. types, categories
	        	{
	        		reqParams += "&";
	        		reqParams += "q=" + params.get("q");
	        	}
	        }
	        
	        if ( cRes )
	        {
	        	 restUrl = new URL(this.getUrl());
	        }
	        
	        if ( cRes )
	        {
	        	conn = (HttpURLConnection) restUrl.openConnection();
	        	conn.setRequestMethod("POST");
	        	conn.setRequestProperty("User-Agent", USER_AGENT);
	        	conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	        	conn.setRequestProperty("Accept", "application/json");
	        	conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        	//conn.setRequestProperty("Content-Length", String.valueOf(data.length));
	        	conn.setDoOutput(true);
	    		
	        	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	    		
	    		wr.writeBytes(reqParams);
	    		wr.flush();
	    		wr.close();
	        }
	        
	        if ( cRes )
	        {
	        	int responseCode = conn.getResponseCode();
	        	
	    		System.out.println("\nSending 'POST' request to URL : " + url);
	    		System.out.println("Post parameters : " + reqParams);
	    		System.out.println("Response Code : " + responseCode);
	    		
	        	if (responseCode != HttpURLConnection.HTTP_OK) 
				{
					System.out.println(cMethodName + "::" + conn.getResponseCode());
					System.out.println(cMethodName + "::" + conn.getResponseMessage());
					
					cRes = false;
				}
	        }
			if ( cRes )
			{
				br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	
				System.out.println("Output from Server .... \n");
				String output = "";
				
				while ((output = br.readLine()) != null) 
				{
					System.out.println(output);
					
					cOutput.append(output);
				}
			}
			
	        return cOutput.toString();
		}
		catch (MalformedURLException e) 
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return "";

		}
		catch( Exception e)
		{
			LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return "";
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
	public String getTranslatedData(String translatedData)
	{
		try
		{
			JSONParser parser = new JSONParser();
	    	
	    	JSONArray jsonArray = (JSONArray) parser.parse(translatedData);
		   
		    JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
		    JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);
	
		    return jsonArray3.get(0).toString();
		}
		catch( Exception e)
		{
			LOG.error(e.getMessage(), e);
			
			return "";
		}
	}
	
	/*
	@Override
	public String getTranslatedData(String translatedData) 
	{
		boolean cRes = true;
		boolean isTranslationTaken = false;
		
		String cMethodName = "";
				
		StringBuffer cOutput = new StringBuffer("");
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	     
	        if ( StringUtils.isBlank(translatedData) ) 
	        {
	        	LOG.info(cMethodName + "::entityData is empty!");
	        	
	        	cRes = false;
	        }
	        
	        if ( cRes )
	        {
	        	JSONParser parser = new JSONParser();
	        	
	        	JSONArray root = (JSONArray) parser.parse(translatedData);
	        	
	        	if ( root != null )
	        	{
		        	for (Object rootObj : root) 
		        	{
		        		if ( !isTranslationTaken && rootObj != null )
		        		{
		        			isTranslationTaken = true;
		        			
		        			if ( rootObj instanceof JSONArray )
		        			{
				        		JSONArray flevelArray = (JSONArray)  rootObj;
				        		
				        		if ( flevelArray != null )
				        		{
					        		for( Object flevelObj : flevelArray )
					        		{
					        			if ( flevelObj != null )
					        			{
					        				if ( flevelObj instanceof JSONArray )
						        			{
							        			JSONArray slevelArray = (JSONArray) flevelObj;
							        			
							        			if ( slevelArray != null )
							        			{
								        			for( Object slevelObj : slevelArray )
									        		{
								        				if ( slevelObj != null )
								        				{
									        				if ( slevelObj instanceof String )
									        				{
									        					String translatedText = (String) slevelObj;
										        				
										        				cOutput.append(translatedText);
										    	                cOutput.append(" ");
										    	                System.out.println(translatedText);
									        				}
									        				
									        				break;
								        				}
									        		}
							        			}
						        			}
					        			}
					        		}
				        		}
			        		}
		        		}
		        	}
	        	}
	        }
	        
	        String cOutputStr = this.cleanResult(cOutput);
	        
	        return cOutputStr;
		}
        catch( Exception e)
		{
        	LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return "";
		}
	}
	*/
	
	/**
	 * @param cOutput
	 * @return
	 */
	protected String cleanResult(StringBuffer cOutput) 
	{
		String cMethodName = "";
		
		@SuppressWarnings("unused")
		boolean cRes = true;
	
		try 
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement ste = stacktrace[1];
			cMethodName = ste.getMethodName();
					
			if ( StringUtils.isBlank(cOutput))
			{
				return "";
			}
			
			String cOutputStr = cOutput.toString();
			
			cOutputStr = cOutputStr.replaceAll(Pattern.quote("+"), " ");
			cOutputStr = cOutputStr.replaceAll(Pattern.quote(":"), " ");
		
		return cOutputStr;
		
		}
        catch( Exception e)
		{
        	LOG.error(cMethodName + "::" + e.getMessage(), e);
			
			return "";
		}
	}
}
