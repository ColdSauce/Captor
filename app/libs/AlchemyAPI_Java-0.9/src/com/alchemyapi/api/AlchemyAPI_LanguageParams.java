package com.alchemyapi.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AlchemyAPI_LanguageParams extends AlchemyAPI_Params {
	public static final String CLEANED_OR_RAW = "cleaned_or_raw";
	public static final String CLEANED = "cleaned";
	public static final String RAW = "raw";
	public static final String CQUERY = "cquery";
	public static final String XPATH = "xpath";
	
	private String sourceText;
	private String cQuery;
	private String xPath;
	
	public String getSourceText() {
		return sourceText;
	}

	public void setSourceText(String sourceText) {
		if( !sourceText.equals(AlchemyAPI_LanguageParams.CLEANED_OR_RAW) 
				&& !sourceText.equals(AlchemyAPI_LanguageParams.CQUERY)
				&& !sourceText.equals(AlchemyAPI_LanguageParams.XPATH))
		{
			throw new RuntimeException("Invalid setting " + sourceText + " for parameter sourceText");
		}
		this.sourceText = sourceText;
	}

	public String getCQuery() {
		return cQuery;
	}

	public void setCQuery(String cQuery) {
		this.cQuery = cQuery;
	}

	public String getXPath() {
		return xPath;
	}

	public void setXPath(String xPath) {
		this.xPath = xPath;
	}

	public String getParameterString(){
		String retString = super.getParameterString();
		try{
			if(sourceText!=null) retString+="&sourceText="+sourceText;
			if(cQuery!=null) retString+="&cquery="+URLEncoder.encode(cQuery,"UTF-8");
			if(xPath!=null) retString+="&xpath="+URLEncoder.encode(xPath,"UTF-8");
		}
		catch(UnsupportedEncodingException e ){
			retString = "";
		}
		return retString;
	}

}
