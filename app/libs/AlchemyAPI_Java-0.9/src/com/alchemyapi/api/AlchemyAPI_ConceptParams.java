package com.alchemyapi.api;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;



	public class AlchemyAPI_ConceptParams extends AlchemyAPI_Params{
		public static final String CLEANED_OR_RAW = "cleaned_or_raw";
		public static final String CLEANED = "cleaned";
		public static final String RAW = "raw";
		public static final String CQUERY = "cquery";
		public static final String XPATH = "xpath";
		
		private Integer maxRetrieve;
		private String sourceText;
		private Boolean showSourceText;
		private String cQuery;
		private String xPath;
		private Boolean linkedData;
		
		public String getSourceText() {
			return sourceText;
		}
		
		public void setSourceText(String sourceText) {
			if( !sourceText.equals(AlchemyAPI_ConceptParams.CLEANED) && !sourceText.equals(AlchemyAPI_ConceptParams.CLEANED_OR_RAW) 
					&& !sourceText.equals(AlchemyAPI_ConceptParams.RAW) && !sourceText.equals(AlchemyAPI_ConceptParams.CQUERY)
					&& !sourceText.equals(AlchemyAPI_ConceptParams.XPATH))
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
		
		public boolean isLinkedData() {
			return linkedData;
		}
		
		public void setLinkedData(boolean linkedData) {
			this.linkedData = linkedData;
		}
		
		public String getParameterString(){
			String retString = super.getParameterString();
			try{
				if(sourceText!=null) retString+="&sourceText="+sourceText;
				if(showSourceText!=null) retString+="&showSourceText="+(showSourceText?"1":"0");
				if(cQuery!=null) retString+="&cquery="+URLEncoder.encode(cQuery,"UTF-8");
				if(xPath!=null) retString+="&xpath="+URLEncoder.encode(xPath,"UTF-8");
				if(maxRetrieve!=null) retString+="&maxRetrieve="+maxRetrieve.toString();
				if(linkedData!=null) retString+="&linkedData="+(linkedData?"1":"0");
			}
			catch(UnsupportedEncodingException e ){
				retString = "";
			}
			return retString;
		}
		
	
		
	}
	


