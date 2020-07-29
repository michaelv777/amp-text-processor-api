/**
 * 
 */
package com.amp.text.processor.api.interfaces;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author MVEKSLER
 *
 */
public interface TextProcessorInterface 
{
	public String extractDataFromTextByGet( HashMap<String, String> params);
	
	public String extractDataFromTextByPost( HashMap<String, String> params); 
	
	public LinkedHashMap<Double, String> getKeywordsFromJSONData(
			String jsonData, int numKeywords, boolean checkEmotions); 
	
	public String getKeywordsFromJSONData(String entityData);
	
	public String getEntitiesFromJSONData(String jsonData);
}
