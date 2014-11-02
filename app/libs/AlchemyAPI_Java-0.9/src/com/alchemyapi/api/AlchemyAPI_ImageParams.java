package com.alchemyapi.api;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;



	public class AlchemyAPI_ImageParams extends AlchemyAPI_Params{
		public static final String CQUERY = "cquery";
		public static final String XPATH = "xpath";

		private String cQuery;
		private String xPath;
		private Integer maxRetrieve;
		private String baseUrl;

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
				if(cQuery!=null) retString+="&cquery="+URLEncoder.encode(cQuery,"UTF-8");
				if(xPath!=null) retString+="&xpath="+URLEncoder.encode(xPath,"UTF-8");
				if(maxRetrieve!=null) retString+="&maxRetrieve="+maxRetrieve.toString();
				if(baseUrl!=null) retString+="&baseUrl="+URLEncoder.encode(baseUrl,"UTF-8");
			}
			catch(UnsupportedEncodingException e ){
				retString = "";
			}
			return retString;
		}
	}
