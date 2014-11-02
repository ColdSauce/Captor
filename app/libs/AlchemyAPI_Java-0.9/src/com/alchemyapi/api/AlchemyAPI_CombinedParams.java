package com.alchemyapi.api;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;



	public class AlchemyAPI_CombinedParams extends AlchemyAPI_Params{
		public static final String CLEANED_OR_RAW = "cleaned_or_raw";
		public static final String CLEANED = "cleaned";
		public static final String RAW = "raw";
		public static final String CQUERY = "cquery";
		public static final String XPATH = "xpath";
		public static final String EXTRACT_MODE_TRUST = "trust-metadata";
		public static final String EXTRACT_MODE_INFER = "always-infer";
		public static final String EXTRACT_PAGE_IMAGE = "page-image";
		public static final String EXTRACT_ENTITY = "entity";
		public static final String EXTRACT_KEYWORD = "keyword";
		public static final String EXTRACT_TITLE = "title";
		public static final String EXTRACT_AUTHOR = "author";
		public static final String EXTRACT_TAXONOMY = "taxonomy";
		public static final String EXTRACT_CONCEPT = "concept";
		public static final String EXTRACT_RELATION = "relation";
		public static final String EXTRACT_DOC_SENTIMENT = "doc-sentiment";

		private Boolean disambiguate;
		private Boolean linkedData;
		private Boolean coreference;
		private Boolean quotations;
		private String sourceText;
		private Boolean showSourceText;
		private String cQuery;
		private String xPath;
		private Integer maxRetrieve;
		private String baseUrl;
		private Boolean sentiment = false;
		private String extractMode;
		private String extract;

		public boolean isDisambiguate() {
			return disambiguate;
		}
		public void setDisambiguate(boolean disambiguate) {
			this.disambiguate = disambiguate;
		}
		public boolean isLinkedData() {
			return linkedData;
		}
		public void setLinkedData(boolean linkedData) {
			this.linkedData = linkedData;
		}
		public boolean isCoreference() {
			return coreference;
		}
		public void setCoreference(boolean coreference) {
			this.coreference = coreference;
		}
		public boolean isQuotations() {
			return quotations;
		}
		public void setQuotations(boolean quotations) {
			this.quotations = quotations;
		}
		public String getSourceText() {
			return sourceText;
		}
		public void setSourceText(String sourceText) {
			if( !sourceText.equals(AlchemyAPI_CombinedParams.CLEANED) && !sourceText.equals(AlchemyAPI_CombinedParams.CLEANED_OR_RAW) 
					&& !sourceText.equals(AlchemyAPI_CombinedParams.RAW) && !sourceText.equals(AlchemyAPI_CombinedParams.CQUERY)
					&& !sourceText.equals(AlchemyAPI_CombinedParams.XPATH))
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
		public String getBaseUrl() {
			return baseUrl;
		}
		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}
		public boolean isSentiment() {
			return sentiment;
		}
		public void setSentiment(boolean sentiment) {
			this.sentiment = sentiment;
		}
		public void setExtractMode(String extractMode) {
		if( !extractMode.equals(EXTRACT_MODE_TRUST) && !extractMode.equals(EXTRACT_MODE_INFER) ) 
		{
			throw new RuntimeException("Invalid setting " + extractMode + " for parameter extractMode");
		}
		this.extractMode = extractMode;
		}
		public void setExtractAll() {
		    this.extract = EXTRACT_PAGE_IMAGE + EXTRACT_ENTITY + EXTRACT_KEYWORD
			+ EXTRACT_TITLE + EXTRACT_AUTHOR + EXTRACT_TAXONOMY
			+ EXTRACT_CONCEPT + EXTRACT_RELATION + EXTRACT_DOC_SENTIMENT;
		}
		public void setExtract(String extractArg) {
		    if( !extractArg.equals(EXTRACT_PAGE_IMAGE)
			&& !extractArg.equals(EXTRACT_ENTITY)
			&& !extractArg.equals(EXTRACT_KEYWORD)
			&& !extractArg.equals(EXTRACT_TITLE)
			&& !extractArg.equals(EXTRACT_AUTHOR)
			&& !extractArg.equals(EXTRACT_TAXONOMY)
			&& !extractArg.equals(EXTRACT_CONCEPT)
			&& !extractArg.equals(EXTRACT_RELATION)
			&& !extractArg.equals(EXTRACT_DOC_SENTIMENT) )
		    {
			throw new RuntimeException("Invalid setting " + extractArg + " for parameter extract");
		    }
		    if ((null == this.extract) || (0 == this.extract.length()))
			this.extract = extractArg;
		    else
			this.extract += "," + extractArg;
		}
		public String getParameterString(){
			String retString = super.getParameterString();
			try{
			    if (extractMode!=null) retString+="&extractMode="+extractMode;
			    if (extract!=null) retString+="&extract="+extract;
				if(sentiment!=null) retString+="&sentiment="+(sentiment?"1":"0");
				if(showSourceText!=null) retString+="&showSourceText="+(showSourceText?"1":"0");
				if(maxRetrieve!=null) retString+="&maxRetrieve="+maxRetrieve.toString();
				if(baseUrl!=null) retString+="&baseUrl="+URLEncoder.encode(baseUrl,"UTF-8");
				if(disambiguate!=null) retString+="&disambiguate="+(disambiguate ? "1":"0");
				if(linkedData!=null) retString+="&linkedData="+(linkedData?"1":"0");
				if(coreference!=null) retString+="&coreference="+(coreference?"1":"0");
				if(quotations!=null) retString+="&quotations="+(quotations?"1":"0");
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
	


