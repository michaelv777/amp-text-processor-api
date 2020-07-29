/**
 * 
 */
package com.amp.text.translator.api.interfaces;

import java.util.HashMap;

/**
 * @author MVEKSLER
 *
 */
public interface TextTranslatorInterface 
{
	public String translateTextByGetHttp(HashMap<String, String> params);
	
	public String translateTextByPostHttp(HashMap<String, String> params);
	
	public String translateTextByGet(String text);
	
	public String translateTextByPost(String text);
	
	public String getTranslatedData(String translatedData);
}
