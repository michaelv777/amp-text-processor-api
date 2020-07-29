package com.amp.text.processor.api.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TextProcessorBase
{
	private static final Logger LOG = 
			LoggerFactory.getLogger(TextProcessorBase.class);
	
	protected HashMap<String, String> 
		cSystemConfiguration = new  HashMap<String, String>();
	
	protected HashMap<String, String> 
		cWorkerConfiguration = new  HashMap<String, String>();
	
	public HashMap<String, String> getcSystemConfiguration() {
		return cSystemConfiguration;
	}

	public void setcSystemConfiguration(HashMap<String, String> cSystemConfiguration) {
		this.cSystemConfiguration = cSystemConfiguration;
	}
	
	public HashMap<String, String> getcWorkerConfiguration() {
		return cWorkerConfiguration;
	}

	public void setcWorkerConfiguration(HashMap<String, String> cWorkerConfiguration) {
		this.cWorkerConfiguration = cWorkerConfiguration;
	}

	protected void printHttpsCert(HttpsURLConnection conn)
	{

      try 
      {
    	  if( conn == null )
    	  {
    		  return ;
    	  }
    	  
    	  LOG.info("Response Code : " + conn.getResponseCode());
    	  LOG.info("Cipher Suite : " + conn.getCipherSuite());
    	  LOG.info("\n");
	
		  Certificate[] certs = conn.getServerCertificates();
			
		  for( Certificate cert : certs )
		  {
			  LOG.info("Cert Type : " + cert.getType());
			  LOG.info("Cert Hash Code : " + cert.hashCode());
			  LOG.info("Cert Public Key Algorithm : "
		                                    + cert.getPublicKey().getAlgorithm());
			  LOG.info("Cert Public Key Format : "
		                                    + cert.getPublicKey().getFormat());
			  LOG.info("\n");
		  }

      }
	  catch (SSLPeerUnverifiedException e) 
	  {
		  LOG.error(e.getMessage(), e);
	  } 
	  catch (IOException e)
	  {
		  LOG.error(e.getMessage(), e);
	  }
	    
	}
	
	protected void printContent(HttpsURLConnection con)
	{
		if(con == null)
		{
			return ;
		}
		
		try 
		{

		   System.out.println("****** Content of the URL ********");
		   BufferedReader br =
			new BufferedReader(
				new InputStreamReader(con.getInputStream()));

		   String input;

		   while ((input = br.readLine()) != null)
		   {
		      System.out.println(input);
		   }
		   
		   br.close();

		} 
		catch (IOException e) 
		{
			LOG.error(e.getMessage(), e);
		}
	}
	
	protected boolean setSystemProperties() 
	{
		boolean cRes = true;
		
		try
		{
	        for( Map.Entry<String, String> cConfiguration : this.cSystemConfiguration.entrySet() )
	        {
	        	String cConfigKey   = cConfiguration.getKey();
	        	String cConfigValue = cConfiguration.getValue();
	        	
	        	if ( System.getProperty(cConfigKey) != null )
        		{
        			System.clearProperty(cConfigKey);
        		}
	        	
	        	System.setProperty(cConfigKey, cConfigValue);
	        }
	        
	        return cRes;
		}
		catch( Exception e)
		{
			LOG.error(e.getMessage(), e);
			
			return false;
		}
	}
	
	//---
	protected boolean setWorkerProperties(Class<? extends TextProcessorBase> clazz)
	{
		@SuppressWarnings("unused")
		String cMethodName = "";
		
		boolean cRes = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
			Field[] cFields = clazz.getDeclaredFields();
			
			for( int jondex = 0; jondex < cFields.length; ++jondex )
			{
				Field cField = (Field)cFields[jondex];
				
				String cSourceConfigKey = cField.getName();
						 
				if ( this.cWorkerConfiguration.containsKey(cSourceConfigKey))
				{
					String cSourceConfigValue = 
							this.cWorkerConfiguration.get(cSourceConfigKey);
					
					  Type type = (Type) cField.getGenericType();
					  	
					  if ( type.equals(String.class ))
					  {
						  cField.set(this, cSourceConfigValue);
					  }
					  else if ( type.equals(boolean.class ))
					  {
						  boolean cBoolSet = Boolean.parseBoolean(cSourceConfigValue);
						  cField.setBoolean(this, cBoolSet);	
					  }
					  else if ( type.equals(int.class ))
					  {
						  int cIntSet = Integer.parseInt(cSourceConfigValue);
						  cField.setInt(this, cIntSet);	
					  }
					  else if ( type.equals(long.class ))
					  {
						  long cIntSet = Long.parseLong(cSourceConfigValue);
						  cField.setLong(this, cIntSet);	
					  }
				}
			}
			
			return cRes;
		}
		catch( IllegalAccessException e )
		{
			LOG.error(e.getMessage(), e);
			
			return false;
		}
		catch( Exception e)
		{
			LOG.error(e.getMessage(), e);
			
			return false;
		}
	}
}

