package com.alchemyapi.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AlchemyAPI_TargetedSentimentParams extends AlchemyAPI_Params {
	
	private Boolean showSourceText;
	private String target;
		
	public boolean isShowSourceText() {
		return showSourceText;
	}
	
	public void setShowSourceText(boolean showSourceText) {
		this.showSourceText = showSourceText;
	}
	
	public String getTarget(){
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getParameterString(){
		String retString = super.getParameterString();
		try{
			if(showSourceText!=null) retString+="&showSourceText="+(showSourceText?"1":"0");
			if(target!=null) retString+="&target="+URLEncoder.encode(target, "UTF-8");
					
		}
		catch(UnsupportedEncodingException e ){
			retString = "";
		}
		
		return retString;
	}
}
