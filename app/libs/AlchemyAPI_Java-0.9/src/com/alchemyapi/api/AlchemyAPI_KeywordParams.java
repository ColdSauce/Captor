package com.alchemyapi.api;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;



	public class AlchemyAPI_KeywordParams extends AlchemyAPI_Params{
		public static final String CLEANED_OR_RAW = "cleaned_or_raw";
		public static final String CLEANED = "cleaned";
		public static final String RAW = "raw";
		public static final String CQUERY = "cquery";
		public static final String XPATH = "xpath";
		
		public static final String EXTRACT_MODE_STRICT = "strict";

		private Integer maxRetrieve;
		private String sourceText;
		private Boolean showSourceText;
		private Boolean sentiment;
		private String cQuery;
		private String xPath;
		private String baseUrl;
		private String keywordExtractMode;
		
		public String getKeywordExtractMode() {
			return keywordExtractMode;
		}
		
		public void setKeywordExtractMode(String keywordExtractMode) {
			if( !keywordExtractMode.equals(AlchemyAPI_KeywordParams.EXTRACT_MODE_STRICT))
			{
				throw new RuntimeException("Invalid setting " + keywordExtractMode + " for parameter keywordExtractMode");
			}
			this.keywordExtractMode = keywordExtractMode;
		}
		
		public String getSourceText() {
			return sourceText;
		}
		
		public void setSourceText(String sourceText) {
			if( !sourceText.equals(AlchemyAPI_KeywordParams.CLEANED) && !sourceText.equals(AlchemyAPI_KeywordParams.CLEANED_OR_RAW) 
					&& !sourceText.equals(AlchemyAPI_KeywordParams.RAW) && !sourceText.equals(AlchemyAPI_KeywordParams.CQUERY)
					&& !sourceText.equals(AlchemyAPI_KeywordParams.XPATH))
			{
				throw new RuntimeException("Invalid setting " + sourceText + " for parameter sourceText");
			}
			this.sourceText = sourceText;
		}
		
		public boolean isShowSourceText() {
			return showSourceText;
		}
		
		public void setShowSourceText(boolean showSourceText) {
			this.showSourceText = showSourceText;
		}
		
		public boolean isSentiment() {
			return sentiment;
		}
		
		public void setSentiment(boolean sentiment) {
			this.sentiment = sentiment;
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
		
		public int getMaxRetrieve() {
			return maxRetrieve;
		}
		
		public void setMaxRetrieve(int maxRetrieve) {
			this.maxRetrieve = maxRetrieve;
		}
		
		public String getBaseUrl() {
			return baseUrl;
		}
		
		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}
		
		public String getParameterString(){
			String retString = super.getParameterString();
			try{
				if(sourceText!=null) retString+="&sourceText="+sourceText;
				if(showSourceText!=null) retString+="&showSourceText="+(showSourceText?"1":"0");
				if(sentiment!=null) retString+="&sentiment="+(sentiment?"1":"0");
				if(cQuery!=null) retString+="&cquery="+URLEncoder.encode(cQuery,"UTF-8");
				if(xPath!=null) retString+="&xpath="+URLEncoder.encode(xPath,"UTF-8");
				if(maxRetrieve!=null) retString+="&maxRetrieve="+maxRetrieve.toString();
				if(baseUrl!=null) retString+="&baseUrl="+URLEncoder.encode(baseUrl,"UTF-8");
				if(keywordExtractMode!=null) retString+="&keywordExtractMode="+URLEncoder.encode(keywordExtractMode,"UTF-8");
			}
			catch(UnsupportedEncodingException e ){
				retString = "";
			}
			return retString;
		}
		
	
		
	}
	


